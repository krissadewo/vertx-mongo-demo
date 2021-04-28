package id.blacklabs.vertx.mongo.common;

import com.mongodb.MongoTimeoutException;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author krissadewo
 * @date 4/28/21 11:21 AM
 */
public abstract class MongoSubscriber<T> implements Subscriber<T> {

    private final List<T> received;

    private final List<Throwable> errors;

    private final CountDownLatch latch;

    private volatile Subscription subscription;

    private volatile boolean completed;

    public MongoSubscriber() {
        this.received = new ArrayList<>();
        this.errors = new ArrayList<>();
        this.latch = new CountDownLatch(1);
    }

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
