package id.blacklabs.vertx.mongo;

import id.blacklabs.vertx.mongo.config.MongoConfig;
import id.blacklabs.vertx.mongo.verticle.ProductVerticle;
import id.blacklabs.vertx.mongo.verticle.SalesVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.Json;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MainVerticle extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(MainVerticle.class);

    private final List<String> deployedVerticles = new ArrayList<>();

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        Router router = Router.router(vertx);

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8888, http -> {
                if (http.succeeded()) {
                    startPromise.complete();

                    MongoClient mongoClient = MongoConfig.getInstance().mongoClient(vertx);

                    vertx.deployVerticle(new ProductVerticle(mongoClient, router), event -> {
                        if (event.succeeded()) {
                            deployedVerticles.add(event.result());
                        } else {
                            logger.error("failed to deploy product verticle : {}", event.cause().getMessage());
                        }
                    });

                    vertx.deployVerticle(new SalesVerticle(mongoClient, router), event -> {
                        if (event.succeeded()) {
                            deployedVerticles.add(event.result());
                        } else {
                            logger.error("failed to deploy sales verticle : {}", event.cause().getMessage());
                        }
                    });

                    logger.info("HTTP server started on port 8888");
                } else {
                    startPromise.fail(http.cause());
                }
            });
    }

}
