package id.blacklabs.vertx.mongo.module.sales.infrastructure.repository.impl;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.mongodb.client.result.InsertOneResult;
import id.blacklabs.vertx.mongo.common.StatusCode;
import id.blacklabs.vertx.mongo.config.MongoConfig;
import id.blacklabs.vertx.mongo.context.ConfigContext;
import id.blacklabs.vertx.mongo.document.Sales;
import id.blacklabs.vertx.mongo.module.product.infrastructure.repository.impl.ProductRepositoryImpl;
import id.blacklabs.vertx.mongo.module.sales.infrastructure.repository.SalesRepository;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author krissadewo
 * @date 4/26/21 2:29 PM
 */
public class SalesRepositoryImpl implements SalesRepository {

    private final Logger logger = LoggerFactory.getLogger(ProductRepositoryImpl.class);

    private final MongoConfig mongoConfig;

    @Inject
    public SalesRepositoryImpl(@Named("vertx") Vertx vertx) {
        this.mongoConfig = new ConfigContext(vertx).get(MongoConfig.class);
    }

    @Override
    public void save(Sales sales, Promise<String> handler) {
        mongoConfig.getSalesCollection()
            .insertOne(sales)
            .subscribe(new SingleSubscriber<>() {
                @Override
                public void onSuccess(InsertOneResult result) {
                    logger.info("saving sales success");

                    handler.handle(Future.succeededFuture(StatusCode.SAVE_SUCCESS));
                }

                @Override
                public void onFailure(Throwable throwable) {
                    logger.error("saving sales failed : {}", throwable.getCause().getMessage());

                    handler.handle(Future.failedFuture(throwable));
                }
            });
    }

    @Override
    public void update(Sales document, Promise<String> promise) {
    }

    @Override
    public void delete(String id, Promise<String> promise) {
    }

    @Override
    public void findById(String id, Promise<Sales> promise) {
    }

    @Override
    public void find(Sales param, int limit, int offset, Promise<List<Sales>> promise) {
    }

    @Override
    public void count(Sales param, Promise<Long> promise) {

    }
}
