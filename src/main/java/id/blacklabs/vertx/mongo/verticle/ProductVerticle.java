package id.blacklabs.vertx.mongo.verticle;

import com.google.inject.Guice;
import id.blacklabs.vertx.mongo.module.VertxModule;
import id.blacklabs.vertx.mongo.module.product.ProductModule;
import id.blacklabs.vertx.mongo.module.product.infrastructure.service.ProductService;
import io.vertx.ext.web.Router;

/**
 * @author krissadewo
 * @date 4/24/21 11:07 AM
 */
public class ProductVerticle extends ApplicationVerticle<ProductService> {

    private final Router router;

    public ProductVerticle(Router router) {
        this.router = router;
    }

    @Override
    void buildModule() {
        Guice.createInjector(
                new VertxModule(vertx, router),
                new ProductModule()
        );
    }
}
