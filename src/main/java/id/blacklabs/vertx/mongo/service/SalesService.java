package id.blacklabs.vertx.mongo.service;

import id.blacklabs.vertx.mongo.common.Address;
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
import io.vertx.ext.mongo.MongoClient;
import io.vertx.serviceproxy.ServiceBinder;
import lombok.Builder;

/**
 * @author krissadewo
 * @date 4/26/21 2:32 PM
 */
public class SalesService extends AbstractApplicationService {

    @Builder
    public SalesService(Vertx vertx, MongoClient mongoClient) {
        super(vertx, mongoClient);

        repositoryContext.putIfAbsent(SalesRepository.class, () -> SalesRepository.createProxy(vertx, Address.ADDRESS_SALES_REPO));
    }

    public void save(Sales sales, Handler<AsyncResult<String>> resultHandler) {
        repositoryContext.get(ProductRepository.class)
            .findById(sales.getProduct().getId(), event -> {
                Product product = event.result();

                if (product != null) {
                    sales.setProduct(product);

                    repositoryContext.get(SalesRepository.class).save(sales, resultHandler);
                }
            });
    }

    @Override
    MessageConsumer<JsonObject> registerService(Vertx vertx, MongoClient mongoClient) {
        return new ServiceBinder(vertx)
            .setAddress(Address.ADDRESS_SALES_REPO)
            .register(SalesRepository.class, new SalesRepositoryImpl(mongoClient));
    }

}
