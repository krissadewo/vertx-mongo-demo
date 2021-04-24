package id.blacklabs.vertx.mongo.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

/**
 * @author krissadewo
 * @date 4/24/21 7:34 PM
 */
public abstract class ApplicationVerticle<T> extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        super.start(startPromise);

        buildApi(buildService());
    }

    abstract void buildApi(T service);

    abstract T buildService();
}
