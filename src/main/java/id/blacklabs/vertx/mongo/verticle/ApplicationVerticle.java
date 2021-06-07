package id.blacklabs.vertx.mongo.verticle;

import id.blacklabs.vertx.mongo.service.AbstractApplicationService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;

/**
 * @author krissadewo
 * @date 4/24/21 7:34 PM
 */
public abstract class ApplicationVerticle<T extends AbstractApplicationService> extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        super.start(startPromise);

        buildModule();
    }

    abstract void buildModule();

}
