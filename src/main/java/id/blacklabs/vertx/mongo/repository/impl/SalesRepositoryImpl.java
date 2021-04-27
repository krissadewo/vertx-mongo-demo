package id.blacklabs.vertx.mongo.repository.impl;

import id.blacklabs.vertx.mongo.common.StatusCode;
import id.blacklabs.vertx.mongo.document.Sales;
import id.blacklabs.vertx.mongo.repository.SalesRepository;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.ext.mongo.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author krissadewo
 * @date 4/26/21 2:29 PM
 */
public class SalesRepositoryImpl implements SalesRepository {

    private final Logger logger = LoggerFactory.getLogger(ProductRepositoryImpl.class);

    private final MongoClient mongoClient;

    public SalesRepositoryImpl(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public void save(Sales sales, Handler<AsyncResult<String>> resultHandler) {
        mongoClient.save("sales", sales.toJson())
            .onSuccess(event -> {
                logger.info("saving sales success");

                resultHandler.handle(Future.succeededFuture(StatusCode.SAVE_SUCCESS));
            })
            .onFailure(event -> {
                logger.error("saving sales failed : {}", event.getCause().getMessage());

                resultHandler.handle(Future.failedFuture(StatusCode.SAVE_FAILED));
            });
    }
}
