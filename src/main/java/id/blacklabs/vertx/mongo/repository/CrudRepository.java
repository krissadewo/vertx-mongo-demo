package id.blacklabs.vertx.mongo.repository;

import com.mongodb.MongoTimeoutException;
import id.blacklabs.vertx.mongo.common.DefaultException;
import id.blacklabs.vertx.mongo.common.StatusCode;
import io.vertx.core.Promise;
import io.vertx.core.shareddata.Shareable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author krissadewo
 * @date 4/26/21 2:51 PM
 */
public interface CrudRepository<T> extends Shareable {

    void save(T document, Promise<String> promise);

    void update(T document, Promise<String> promise);

    void delete(String id, Promise<String> promise);

    void findById(String id, Promise<T> promise);

    void find(T param, int limit, int offset, Promise<List<T>> promise);

    void count(T param, Promise<Long> promise);

    abstract class MongoSubscriber<T> implements Subscriber<T> {

        private List<T> received;

        private List<Throwable> errors;

        private CountDownLatch latch;

        private volatile Subscription subscription;

        private volatile boolean completed;

        @Override
        public void onSubscribe(final Subscription subscription) {
            this.subscription = subscription;

            subscription.request(Integer.MAX_VALUE);
        }

        @Override
        public void onNext(final T t) {
            received.add(t);

            onSuccess(t);
        }

        public abstract void onSuccess(final T result);

        public abstract void onFailure(Throwable throwable);

        @Override
        public void onError(Throwable throwable) {
            errors.add(throwable);

            onFailure(throwable);
            onComplete();
        }

        @Override
        public void onComplete() {
            completed = true;
            latch.countDown();

            if (getReceived().size() == 0) {
                onFailure(new DefaultException(StatusCode.NOT_FOUND));
            }
        }

        public Subscription getSubscription() {
            return subscription;
        }

        public List<T> getReceived() {
            return received;
        }

        public Throwable getError() {
            if (errors.size() > 0) {
                return errors.get(0);
            }

            return null;
        }

        public boolean isCompleted() {
            return completed;
        }

        public List<T> get(final long timeout, final TimeUnit unit) throws Throwable {
            return await(timeout, unit).getReceived();
        }

        public MongoSubscriber<T> await() throws Throwable {
            return await(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        }

        public MongoSubscriber<T> await(final long timeout, final TimeUnit unit) throws Throwable {
            subscription.request(Integer.MAX_VALUE);

            if (!latch.await(timeout, unit)) {
                throw new MongoTimeoutException("Publisher onComplete timed out");
            }

            if (!errors.isEmpty()) {
                throw errors.get(0);
            }

            return this;
        }
    }

    abstract class ManySubscriber<T> extends MongoSubscriber<T> {

        public ManySubscriber() {
            super.received = new ArrayList<>();
            super.errors = new ArrayList<>();
            super.latch = new CountDownLatch(1);
        }
    }

    abstract class SingleSubscriber<T> extends MongoSubscriber<T> {

        public SingleSubscriber() {
            super.errors = new ArrayList<>();
            super.received = new ArrayList<>();
            super.latch = new CountDownLatch(1);
        }

    }
}
