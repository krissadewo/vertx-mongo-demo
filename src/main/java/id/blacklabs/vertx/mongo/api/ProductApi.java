package id.blacklabs.vertx.mongo.api;

import id.blacklabs.vertx.mongo.api.response.HttpResponse;
import id.blacklabs.vertx.mongo.dto.ProductDTO;
import id.blacklabs.vertx.mongo.service.ProductService;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

/**
 * @author krissadewo
 * @date 4/24/21 3:09 PM
 */
public class ProductApi {

    private final ProductService service;

    private static final Logger logger = LoggerFactory.getLogger(ProductApi.class);

    @Builder
    public ProductApi(Router router, ProductService service) {
        this.service = service;

        router.clear();
        router.route().handler(BodyHandler.create());
        router.post("/products").handler(this::save);
        router.get("/products").handler(this::find);
        router.put("/products").handler(this::update);
    }

    private void save(RoutingContext context) {
        service.save(new ProductDTO().toDocument(context.getBodyAsString()), event -> {
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

    private void find(RoutingContext context) {
        ProductDTO dto = new ProductDTO();

        service.find(dto.toParam(context.getBodyAsString()), event -> {
            if (event.succeeded()) {
                context.response()
                    .putHeader("content-type", "application/json")
                    .end(Json.encode(HttpResponse.Many
                        .builder()
                        .data(dto.toDTO(event.result()))
                        .build()
                    ));
            } else {
                context.response()
                    .putHeader("content-type", "application/json")
                    .end("failed");
            }
        });
    }

    private void update(RoutingContext context) {
        service.update(new ProductDTO().toDocument(context.getBodyAsString()), event -> {
            if (event.succeeded()) {
                context.response()
                    .putHeader("content-type", "application/json")
                    .end(Json.encode(event.result()));
            } else {
                context.response()
                    .putHeader("content-type", "application/json")
                    .end("failed");
            }
        });
    }
}
