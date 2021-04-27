package id.blacklabs.vertx.mongo.service;

import id.blacklabs.vertx.mongo.common.RepositoryContext;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author krissadewo
 * @date 4/24/21 3:28 PM
 */
public abstract class AbstractApplicationService {

    private final static Logger logger = LoggerFactory.getLogger(AbstractApplicationService.class);

    private final MessageConsumer<JsonObject> serviceRegister;

    protected RepositoryContext repositoryContext;

    public AbstractApplicationService(Vertx vertx, MongoClient mongoClient) {
        serviceRegister = registerService(vertx, mongoClient);
        repositoryContext = new RepositoryContext(vertx);
    }

    abstract MessageConsumer<JsonObject> registerService(Vertx vertx, MongoClient mongoClient);

    public void unregisterService() {
        serviceRegister.unregister(event -> {
            if (event.succeeded()) {
                logger.info("unregister service {} success", serviceRegister.address());
            } else {
                logger.error("unregister service {} success", serviceRegister.address());
            }
        });
    }
}
