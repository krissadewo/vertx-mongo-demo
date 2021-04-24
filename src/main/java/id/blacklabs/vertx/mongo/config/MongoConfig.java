package id.blacklabs.vertx.mongo.config;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

/**
 * @author krissadewo
 * @date 4/24/21 7:56 PM
 */
public class MongoConfig {

    private static class MongoConfigHelper {
        private static final MongoConfig INSTANCE = new MongoConfig();
    }

    public static MongoConfig getInstance() {
        return MongoConfigHelper.INSTANCE;
    }

    public MongoClient mongoClient(Vertx vertx) {
        JsonObject mongoConfig = new JsonObject();
        mongoConfig.put("connection_string", "mongodb://localhost:27017");
        mongoConfig.put("db_name", "vertx_demo");
        mongoConfig.put("username", "vertx");
        mongoConfig.put("password", "vertx");

        return MongoClient.createShared(vertx, mongoConfig);
    }
}
