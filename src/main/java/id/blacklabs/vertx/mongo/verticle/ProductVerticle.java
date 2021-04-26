package id.blacklabs.vertx.mongo.verticle;

import id.blacklabs.vertx.mongo.api.ProductApi;
import id.blacklabs.vertx.mongo.service.ProductService;
import io.vertx.core.Promise;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;

/**
 * @author krissadewo
 * @date 4/24/21 11:07 AM
 */
public class ProductVerticle extends ApplicationVerticle<ProductService> {

    private final MongoClient mongoClient;

    private final Router router;

    public ProductVerticle(MongoClient mongoClient, Router router) {
        this.mongoClient = mongoClient;
        this.router = router;
    }

    @Override
    void buildApi(ProductService productService) {
        ProductApi.builder()
            .productService(productService)
            .router(router)
            .build();
    }

    @Override
    ProductService buildService() {
        return ProductService.builder()
            .vertx(vertx)
            .mongoClient(mongoClient)
            .build();
    }
}
