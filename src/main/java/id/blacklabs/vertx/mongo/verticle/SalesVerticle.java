package id.blacklabs.vertx.mongo.verticle;

import id.blacklabs.vertx.mongo.api.SalesApi;
import id.blacklabs.vertx.mongo.service.SalesService;
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
            .build();
    }
}
