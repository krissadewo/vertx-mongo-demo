package id.blacklabs.vertx.mongo.common;

import io.vertx.core.Future;
import io.vertx.core.Promise;

/**
 * @author krissadewo
 * @date 5/3/21 8:00 PM
 */
public abstract class PromiseWrapperHandler<T> implements Promise<T> {

    public abstract void onSuccess(T result);

    public abstract void onFailure(Throwable cause);

    @Override
    public boolean tryComplete(T result) {
        onSuccess(result);

        return true;
    }

    @Override
    public boolean tryFail(Throwable cause) {
        onFailure(cause);

        return true;
    }

    @Override
    public Future<T> future() {
        return null;
    }
}
