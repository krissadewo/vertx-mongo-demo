package id.blacklabs.vertx.mongo.api;

import id.blacklabs.vertx.mongo.dto.SalesDTO;
import id.blacklabs.vertx.mongo.service.SalesService;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author krissadewo
 * @date 4/26/21 2:36 PM
 */
public class SalesApi {

    private final SalesService service;

    private static final Logger logger = LoggerFactory.getLogger(SalesApi.class);

    @Builder
    public SalesApi(Router router, SalesService service) {
        this.service = service;

        router.clear();
        router.route().handler(BodyHandler.create());
        router.post("/sales").handler(this::save);
    }

    private void save(RoutingContext context) {
        service.save(new SalesDTO().toDocument(context.getBodyAsString()), event -> {
            logger.info(event.result());

            if (event.succeeded()) {
                context.response()
                    .putHeader("content-type", "application/json")
                    .end("success");
            } else {
                context.response()
                    .putHeader("content-type", "application/json")
                    .end("failed");
            }
        });
    }

}
