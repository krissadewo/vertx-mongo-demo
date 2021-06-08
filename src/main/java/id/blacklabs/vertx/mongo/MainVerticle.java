package id.blacklabs.vertx.mongo;

import id.blacklabs.vertx.mongo.config.MongoConfig;
import id.blacklabs.vertx.mongo.verticle.ProductVerticle;
import id.blacklabs.vertx.mongo.verticle.SalesVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.impl.RouterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MainVerticle extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(MainVerticle.class);

    private final List<String> deployedVerticles = new ArrayList<>();

    @Override
    public void start(Promise<Void> startPromise) {
        Router router = new RouterImpl(vertx);

        MongoConfig.builder()
            .config(config())
            .vertx(vertx)
            .build(); //setup mongo

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8888, http -> {
                if (http.succeeded()) {
                    startPromise.complete();

                    CompositeFuture.all(
                        vertx.deployVerticle(new ProductVerticle(router)),
                        vertx.deployVerticle(new SalesVerticle(router))
                    ).onSuccess(event -> {
                        if (event.succeeded()) {
                            deployedVerticles.addAll(event.list());
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
