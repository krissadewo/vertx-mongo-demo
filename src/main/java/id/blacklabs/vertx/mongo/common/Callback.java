package id.blacklabs.vertx.mongo.common;

import java.util.function.Consumer;

/**
 * @author krissadewo
 * @date 6/5/21 12:21 PM
 */
public interface Callback<T> extends Consumer<T> {

    void success(T t);

    void failure(Throwable throwable);

    abstract class DelegatingCallback<T> implements Callback<T> {

        private Consumer<T> success;

        private Consumer<Throwable> failure;

        public DelegatingCallback() {
            success = new Consumer<T>() {
                @Override
                public void accept(T t) {

                }
            };

            failure = new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) {

                }
            };
        }

        private DelegatingCallback(Consumer<T> success, Consumer<Throwable> failure) {
            this.success = success;
            this.failure = failure;
        }

        @Override
        public void success(T t) {
            success.accept(t);
        }

        @Override
        public void failure(Throwable throwable) {
            failure.accept(throwable);
        }

    }
}
