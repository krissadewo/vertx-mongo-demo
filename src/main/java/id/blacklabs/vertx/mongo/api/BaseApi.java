package id.blacklabs.vertx.mongo.api;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

/**
 * @author krissadewo
 * @date 4/29/21 3:05 PM
 */
interface BaseApi {

    default void doSuccessResponse(RoutingContext context, Object object) {
        context.response()
            .putHeader("content-type", "application/json")
            .end(Json.encode(object));
    }

    default void doFailedResponse(RoutingContext context) {
        context.response()
            .putHeader("content-type", "application/json")
            .end("failed");
    }

    abstract class PromiseResponseHandler<T> implements Promise<T> {

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

}
