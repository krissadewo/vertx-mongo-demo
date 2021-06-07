package id.blacklabs.vertx.mongo.verticle;

import com.google.inject.Guice;
import id.blacklabs.vertx.mongo.module.VertxModule;
import id.blacklabs.vertx.mongo.module.product.ProductModule;
import id.blacklabs.vertx.mongo.module.sales.SalesModule;
import id.blacklabs.vertx.mongo.module.sales.infrastructure.service.SalesService;
import io.vertx.ext.web.Router;

/**
 * @author krissadewo
 * @date 4/26/21 2:36 PM
 */
public class SalesVerticle extends ApplicationVerticle<SalesService> {

    private final Router router;

    public SalesVerticle(Router router) {
        this.router = router;
    }

    @Override
    void buildModule() {
        Guice.createInjector(
                new VertxModule(vertx, router),
                new ProductModule(),
                new SalesModule()
        );
    }
}
