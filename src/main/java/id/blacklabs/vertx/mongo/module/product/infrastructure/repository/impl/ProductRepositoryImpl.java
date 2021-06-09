package id.blacklabs.vertx.mongo.module.product.infrastructure.repository.impl;

import com.google.inject.Inject;
import com.mongodb.MongoClientException;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import id.blacklabs.vertx.mongo.common.StatusCode;
import id.blacklabs.vertx.mongo.config.MongoConfig;
import id.blacklabs.vertx.mongo.context.ConfigContext;
import id.blacklabs.vertx.mongo.document.Product;
import id.blacklabs.vertx.mongo.module.product.infrastructure.repository.ProductRepository;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.util.List;

/**
 * @author krissadewo
 * @date 4/24/21 10:49 AM
 */
public class ProductRepositoryImpl implements ProductRepository {

    private final Logger logger = LoggerFactory.getLogger(ProductRepositoryImpl.class);

    private final MongoConfig mongoConfig;

    @Inject
    public ProductRepositoryImpl(@Named("vertx") Vertx vertx) {
        this.mongoConfig = new ConfigContext(vertx).get(MongoConfig.class);
    }

    @Override
    public void save(Product product, Promise<String> promise) {
        mongoConfig.getProductCollection()
            .insertOne(product)
            .subscribe(new SingleSubscriber<>() {
                @Override
                public void onSuccess(InsertOneResult result) {
                    logger.info("saving product success");

                    promise.handle(Future.succeededFuture(StatusCode.SAVE_SUCCESS));
                }

                @Override
                public void onFailure(Throwable throwable) {
                    logger.error("saving product failed : {}", throwable.getCause().getMessage());

                    promise.handle(Future.failedFuture(throwable));
                }
            });
    }

    @Override
    public void update(Product document, Promise<String> promise) {
        mongoConfig.getProductCollection()
            .findOneAndReplace(Filters.eq("_id", document.getId()), document)
            .subscribe(new ManySubscriber<>() {
                @Override
                public void onSuccess(Product result) {
                    logger.info("update product success");

                    promise.handle(Future.succeededFuture(StatusCode.UPDATE_SUCCESS));
                }

                @Override
                public void onFailure(Throwable throwable) {
                    logger.error("update product failed : {}", throwable.getCause().getMessage());

                    promise.handle(Future.failedFuture(StatusCode.UPDATE_FAILED));
                }
            });
    }

    @Override
    public void delete(String id, Promise<String> promise) {
    }

    @Override
    public void findById(String id, Promise<Product> promise) {
        mongoConfig.getProductCollection()
            .find(Filters.eq("_id", new ObjectId(id)))
            .subscribe(new SingleSubscriber<>() {
                @Override
                public void onSuccess(Product result) {
                    promise.handle(Future.succeededFuture(result));
                }

                @Override
                public void onFailure(Throwable throwable) {
                    logger.error("finding product failed : {}", throwable.getMessage());

                    promise.handle(Future.failedFuture(throwable));
                }
            });
    }

    @Override
    public void find(Product param, int limit, int offset, Promise<List<Product>> promise) {
        mongoConfig.getProductCollection()
            .find()
            .skip(offset)
            .limit(limit)
            .subscribe(new ManySubscriber<>() {
                @Override
                public void onSuccess(Product result) {
                }

                @Override
                public void onFailure(Throwable throwable) {
                    logger.error("finding product failed : {}", throwable.getCause().getMessage());

                    promise.handle(Future.failedFuture(throwable));
                }

                @Override
                public void onComplete() {
                    promise.handle(Future.succeededFuture(getReceived()));
                }
            });
    }

    @Override
    public void count(Product param, Promise<Long> promise) {
        mongoConfig.getProductCollection()
            .countDocuments()
            .subscribe(new SingleSubscriber<>() {
                @Override
                public void onSuccess(Long result) {
                    promise.handle(Future.succeededFuture(result));
                }

                @Override
                public void onFailure(Throwable throwable) {
                    logger.error("finding product failed : {}", throwable.getCause().getMessage());

                    promise.handle(Future.failedFuture(throwable));
                }
            });
    }

}
