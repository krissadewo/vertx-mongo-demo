package id.blacklabs.vertx.mongo.common;

import io.vertx.core.Future;
import io.vertx.core.Promise;

import java.util.concurrent.CompletableFuture;

/**
 * @author krissadewo
 * @date 5/3/21 8:00 PM
 */
public class PromiseHandler<T> implements Promise<T> {

    Handler<T> handler;

    public PromiseHandler(Handler<T> handler) {
        this.handler = handler;
    }

    @Override
    public boolean tryComplete(T result) {
        handler.success(result);

        return true;
    }

    @Override
    public boolean tryFail(Throwable cause) {
        handler.failure(cause);

        return true;
    }

    @Override
    public Future<T> future() {
        return Future.fromCompletionStage(new CompletableFuture<>());
    }
}
