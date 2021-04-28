package id.blacklabs.vertx.mongo.verticle;

import id.blacklabs.vertx.mongo.api.ProductApi;
import id.blacklabs.vertx.mongo.service.ProductService;
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
    void buildApi(ProductService service) {
        ProductApi.builder()
            .service(service)
            .router(router)
            .build();
    }

    @Override
    ProductService buildService() {
        return ProductService.builder()
            .vertx(vertx)
            .build();
    }
}
