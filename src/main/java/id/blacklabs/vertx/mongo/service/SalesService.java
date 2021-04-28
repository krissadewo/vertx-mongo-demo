package id.blacklabs.vertx.mongo.service;

import id.blacklabs.vertx.mongo.document.Product;
import id.blacklabs.vertx.mongo.document.Sales;
import id.blacklabs.vertx.mongo.repository.ProductRepository;
import id.blacklabs.vertx.mongo.repository.SalesRepository;
import id.blacklabs.vertx.mongo.repository.impl.SalesRepositoryImpl;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import lombok.Builder;

/**
 * @author krissadewo
 * @date 4/26/21 2:32 PM
 */
public class SalesService extends AbstractApplicationService {

    @Builder
    public SalesService(Vertx vertx) {
        super(vertx);

        repositoryContext.putIfAbsent(SalesRepository.class, () -> new SalesRepositoryImpl(vertx));
    }

    public void save(Sales sales, Handler<AsyncResult<String>> resultHandler) {
        repositoryContext.get(ProductRepository.class)
            .findById(sales.getProduct().getId().toHexString(), event -> {
                Product product = event.result();

                if (product != null) {
                    sales.setProduct(product);

                    repositoryContext.get(SalesRepository.class).save(sales, resultHandler);
                }
            });
    }

    @Override
    MessageConsumer<JsonObject> registerService(Vertx vertx) {
        return null;
    }

}
