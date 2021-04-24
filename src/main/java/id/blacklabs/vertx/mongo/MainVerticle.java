package id.blacklabs.vertx.mongo;

import id.blacklabs.vertx.mongo.config.MongoConfig;
import id.blacklabs.vertx.mongo.verticle.ProductVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
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
                        }
                    });

                    logger.info("HTTP server started on port 8888");
                } else {
                    startPromise.fail(http.cause());
                }
            });
    }

}
