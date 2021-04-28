package id.blacklabs.vertx.mongo.repository.impl;

import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import id.blacklabs.vertx.mongo.common.MongoSubscriber;
import id.blacklabs.vertx.mongo.common.StatusCode;
import id.blacklabs.vertx.mongo.config.MongoConfig;
import id.blacklabs.vertx.mongo.context.ConfigContext;
import id.blacklabs.vertx.mongo.document.Product;
import id.blacklabs.vertx.mongo.repository.ProductRepository;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author krissadewo
 * @date 4/24/21 10:49 AM
 */
public class ProductRepositoryImpl implements ProductRepository {

    private final Logger logger = LoggerFactory.getLogger(ProductRepositoryImpl.class);

    private final MongoConfig mongoConfig;

    public ProductRepositoryImpl(Vertx vertx) {
        this.mongoConfig = new ConfigContext(vertx).get(MongoConfig.class);
    }

    @Override
    public void save(Product product, Handler<AsyncResult<String>> resultHandler) {
        mongoConfig.getProductCollection()
            .insertOne(product)
            .subscribe(new MongoSubscriber<>() {
                @Override
                public void onSuccess(InsertOneResult result) {
                    logger.info("saving product success");

                    resultHandler.handle(Future.succeededFuture(StatusCode.SAVE_SUCCESS));
                }

                @Override
                public void onFailure(Throwable throwable) {
                    logger.error("saving product failed : {}", throwable.getCause().getMessage());

                    resultHandler.handle(Future.failedFuture(StatusCode.SAVE_FAILED));
                }
            });
    }

    @Override
    public void update(Product document, Handler<AsyncResult<String>> resultHandler) {
        mongoConfig.getProductCollection()
            .findOneAndReplace(Filters.eq("_id", document.getId()), document)
            .subscribe(new MongoSubscriber<>() {
                @Override
                public void onSuccess(Product result) {
                    logger.info("update product success");

                    resultHandler.handle(Future.succeededFuture(StatusCode.UPDATE_SUCCESS));
                }

                @Override
                public void onFailure(Throwable throwable) {
                    logger.error("update product failed : {}", throwable.getCause().getMessage());

                    resultHandler.handle(Future.failedFuture(StatusCode.UPDATE_FAILED));
                }
            });
    }

    @Override
    public void delete(String id, Handler<AsyncResult<String>> resultHandler) {
    }

    @Override
    public void findById(String id, Handler<AsyncResult<Product>> resultHandler) {
        mongoConfig.getProductCollection()
            .find(Filters.eq("_id", new ObjectId(id)))
            .subscribe(new MongoSubscriber<>() {

                @Override
                public void onSuccess(Product result) {
                    resultHandler.handle(Future.succeededFuture(result));
                }

                @Override
                public void onFailure(Throwable throwable) {
                    logger.error("finding product failed : {}", throwable.getCause().getMessage());

                    resultHandler.handle(Future.failedFuture(throwable));
                }
            });
    }

    @Override
    public void find(Product param, int limit, int offset, Handler<AsyncResult<List<Product>>> resultHandler) {
        mongoConfig.getProductCollection()
            .find()
            .subscribe(new MongoSubscriber<>() {
                @Override
                public void onSuccess(Product result) {
                }

                @Override
                public void onFailure(Throwable throwable) {
                    logger.error("finding product failed : {}", throwable.getCause().getMessage());

                    resultHandler.handle(Future.failedFuture(throwable));
                }

                @Override
                public void onComplete() {
                    super.onComplete();

                    resultHandler.handle(Future.succeededFuture(getReceived()));
                }
            });
    }

    @Override
    public void count(Product param, Handler<AsyncResult<Long>> resultHandler) {
        mongoConfig.getProductCollection()
            .countDocuments()
            .subscribe(new MongoSubscriber<>() {
                @Override
                public void onSuccess(Long result) {
                    resultHandler.handle(Future.succeededFuture(result));
                }

                @Override
                public void onFailure(Throwable throwable) {
                    logger.error("finding product failed : {}", throwable.getCause().getMessage());

                    resultHandler.handle(Future.failedFuture(throwable));
                }
            });
    }

}
