package id.blacklabs.vertx.mongo.api;

import com.mongodb.client.model.Filters;
import id.blacklabs.vertx.mongo.MainVerticle;
import id.blacklabs.vertx.mongo.common.SubscriberHelpers;
import id.blacklabs.vertx.mongo.config.MongoConfig;
import id.blacklabs.vertx.mongo.context.ConfigContext;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.FileReader;
import java.io.IOException;

/**
 * @author krissadewo
 * @date 4/30/21 12:08 PM
 */
@ExtendWith(VertxExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {

    @BeforeAll
    void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
        try {
            JsonObject config = new JsonObject(IOUtils.toString(new FileReader("src/main/resources/env/test/bootstrap.json")));

            DeploymentOptions options = new DeploymentOptions();
            options.setConfig(config);

            vertx.deployVerticle(new MainVerticle(), options, event -> {
                testContext.completeNow();

                setupDatabase(vertx);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setupDatabase(Vertx vertx) {
        MongoConfig mongoConfig = new ConfigContext(vertx).get(MongoConfig.class);

        mongoConfig.getProductCollection().deleteMany(Filters.exists("_id")).subscribe(new SubscriberHelpers.OperationSubscriber<>());
        mongoConfig.getSalesCollection().deleteMany(Filters.exists("_id")).subscribe(new SubscriberHelpers.OperationSubscriber<>());
    }
}
