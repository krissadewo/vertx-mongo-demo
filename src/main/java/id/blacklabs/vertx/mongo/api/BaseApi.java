package id.blacklabs.vertx.mongo.api;

import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

/**
 * @author krissadewo
 * @date 4/29/21 3:05 PM
 */
public interface BaseApi {

    default void doSuccessResponse(RoutingContext context, Object object) {
        context.response()
            .putHeader("content-type", "application/json")
            .end(Json.encode(object));
    }

    default void doFailedResponse(RoutingContext context, Object object) {
        context.response()
            .putHeader("content-type", "application/json")
            .end(Json.encode(object));
    }
}
