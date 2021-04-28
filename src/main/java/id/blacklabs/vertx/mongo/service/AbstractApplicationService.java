package id.blacklabs.vertx.mongo.service;

import id.blacklabs.vertx.mongo.context.ConfigContext;
import id.blacklabs.vertx.mongo.context.RepositoryContext;
import io.vertx.core.Vertx;

/**
 * @author krissadewo
 * @date 4/24/21 3:28 PM
 */
public abstract class AbstractApplicationService {

    protected RepositoryContext repositoryContext;

    protected ConfigContext configContext;

    public AbstractApplicationService(Vertx vertx) {
        repositoryContext = new RepositoryContext(vertx);
        configContext = new ConfigContext(vertx);

        registerRepository(vertx);
    }

    abstract void registerRepository(Vertx vertx);

}
