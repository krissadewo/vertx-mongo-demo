package id.blacklabs.vertx.mongo.service;

import id.blacklabs.vertx.mongo.context.ConfigContext;
import io.vertx.core.Vertx;

/**
 * @author krissadewo
 * @date 4/24/21 3:28 PM
 */
public abstract class AbstractApplicationService {

    protected ConfigContext configContext;

    public AbstractApplicationService(Vertx vertx) {
        configContext = new ConfigContext(vertx);
    }
}
