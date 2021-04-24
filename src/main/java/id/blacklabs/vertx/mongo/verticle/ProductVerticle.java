package id.blacklabs.vertx.mongo.verticle;

import id.blacklabs.vertx.mongo.api.ProductApi;
import id.blacklabs.vertx.mongo.service.ProductService;
import io.vertx.core.Promise;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author krissadewo
 * @date 4/24/21 11:07 AM
 */
public class ProductVerticle extends ApplicationVerticle<ProductService> {

    private final Logger logger = LoggerFactory.getLogger(ProductVerticle.class);

    private final MongoClient mongoClient;

    private final Router router;

    public ProductVerticle(MongoClient mongoClient, Router router) {
        this.mongoClient = mongoClient;
        this.router = router;
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        super.start(startPromise);
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

    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {
        super.stop(stopPromise);
    }
}
