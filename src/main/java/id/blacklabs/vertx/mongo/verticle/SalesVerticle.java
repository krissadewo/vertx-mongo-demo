package id.blacklabs.vertx.mongo.verticle;

import id.blacklabs.vertx.mongo.api.SalesApi;
import id.blacklabs.vertx.mongo.service.SalesService;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;

/**
 * @author krissadewo
 * @date 4/26/21 2:36 PM
 */
public class SalesVerticle extends ApplicationVerticle<SalesService> {

    private final MongoClient mongoClient;

    private final Router router;

    public SalesVerticle(MongoClient mongoClient, Router router) {
        this.mongoClient = mongoClient;
        this.router = router;
    }

    @Override
    void buildApi(SalesService service) {
        SalesApi.builder()
            .service(service)
            .router(router)
            .build();
    }

    @Override
    SalesService buildService() {
        return SalesService.builder()
            .vertx(vertx)
            .mongoClient(mongoClient)
            .build();
    }
}
