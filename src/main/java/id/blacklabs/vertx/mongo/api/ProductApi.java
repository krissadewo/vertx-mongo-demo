package id.blacklabs.vertx.mongo.api;

import id.blacklabs.vertx.mongo.document.Product;
import id.blacklabs.vertx.mongo.service.ProductService;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author krissadewo
 * @date 4/24/21 3:09 PM
 */
public class ProductApi {

    private final ProductService productService;

    private final Logger logger = LoggerFactory.getLogger(ProductApi.class);

    @Builder
    public ProductApi(Router router, ProductService productService) {
        this.productService = productService;

        router.route().handler(BodyHandler.create());
        router.post("/products").handler(this::save);
    }

    private void save(RoutingContext context) {
        productService.save(new Product(context.getBodyAsJson()), event -> {
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
