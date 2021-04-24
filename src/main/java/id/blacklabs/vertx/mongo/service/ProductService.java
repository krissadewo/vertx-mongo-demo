package id.blacklabs.vertx.mongo.service;

import id.blacklabs.vertx.mongo.common.Address;
import id.blacklabs.vertx.mongo.document.Product;
import id.blacklabs.vertx.mongo.repository.ProductRepository;
import id.blacklabs.vertx.mongo.repository.impl.ProductRepositoryImpl;
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
 * @date 4/24/21 10:45 AM
 */
public class ProductService extends AbstractApplicationService {

    private final ProductRepository productRepository;

    @Builder
    public ProductService(Vertx vertx, MongoClient mongoClient) {
        super(vertx, mongoClient);

        productRepository = ProductRepository.createProxy(vertx, Address.ADDRESS_PRODUCT_REPOSITORY);
    }

    public void save(Product product, Handler<AsyncResult<String>> resultHandler) {
        productRepository.save(product, resultHandler);
    }

    @Override
    MessageConsumer<JsonObject> registerService(Vertx vertx, MongoClient mongoClient) {
        return new ServiceBinder(vertx)
            .setAddress(Address.ADDRESS_PRODUCT_REPOSITORY)
            .register(ProductRepository.class, new ProductRepositoryImpl(mongoClient));
    }

}
