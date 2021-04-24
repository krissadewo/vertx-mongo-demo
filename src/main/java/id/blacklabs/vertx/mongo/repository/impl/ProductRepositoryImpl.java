package id.blacklabs.vertx.mongo.repository.impl;

import id.blacklabs.vertx.mongo.common.StatusCode;
import id.blacklabs.vertx.mongo.document.Product;
import id.blacklabs.vertx.mongo.repository.ProductRepository;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.ext.mongo.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author krissadewo
 * @date 4/24/21 10:49 AM
 */
public class ProductRepositoryImpl implements ProductRepository {

    private final Logger logger = LoggerFactory.getLogger(ProductRepositoryImpl.class);

    private final MongoClient mongoClient;

    public ProductRepositoryImpl(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public void save(Product product, Handler<AsyncResult<String>> resultHandler) {
        mongoClient.save("product", product.toJson())
            .onSuccess(event -> {
                logger.info("saving product success");

                resultHandler.handle(Future.succeededFuture(StatusCode.SAVE_SUCCESS));
            })
            .onFailure(event -> {
                logger.error("saving product failed : {}", event.getCause().getMessage());

                resultHandler.handle(Future.failedFuture(StatusCode.SAVE_FAILED));
            });
    }

    @Override
    public void update(Product product, Handler<AsyncResult<String>> resultHandler) {
    }

    @Override
    public void close() {
    }

}
