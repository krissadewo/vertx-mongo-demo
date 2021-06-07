package id.blacklabs.vertx.mongo.common;

import java.util.concurrent.CompletableFuture;

/**
 * @author krissadewo
 * @date 6/5/21 3:43 PM
 */
public class Handler<T> extends CompletableFuture<T> {

    public void success(T t) {
        complete(t);
    }

    public void failure(Throwable throwable) {
        completeExceptionally(throwable);
    }

}
