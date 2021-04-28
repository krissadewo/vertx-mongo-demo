package id.blacklabs.vertx.mongo.service;

import id.blacklabs.vertx.mongo.context.ConfigContext;
import id.blacklabs.vertx.mongo.context.RepositoryContext;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
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

    protected ConfigContext configContext;

    public AbstractApplicationService(Vertx vertx) {
        serviceRegister = registerService(vertx);
        repositoryContext = new RepositoryContext(vertx);
        configContext = new ConfigContext(vertx);
    }

    abstract MessageConsumer<JsonObject> registerService(Vertx vertx);

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
