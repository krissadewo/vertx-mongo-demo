package id.blacklabs.vertx.mongo.service;

import id.blacklabs.vertx.mongo.document.Product;
import id.blacklabs.vertx.mongo.document.Sales;
import id.blacklabs.vertx.mongo.repository.ProductRepository;
import id.blacklabs.vertx.mongo.repository.SalesRepository;
import id.blacklabs.vertx.mongo.repository.impl.SalesRepositoryImpl;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.future.PromiseImpl;
import lombok.Builder;

/**
 * @author krissadewo
 * @date 4/26/21 2:32 PM
 */
public class SalesService extends AbstractApplicationService {

    @Builder
    public SalesService(Vertx vertx) {
        super(vertx);
    }

    public void save(Sales sales, Promise<String> promise) {
        Promise<Product> promiseProduct = new PromiseImpl<>();

        repositoryContext.get(ProductRepository.class).findById(sales.getProduct().getId().toHexString(), promiseProduct);

        promiseProduct.future().onComplete(event -> {
            if (event != null) {
                sales.setProduct(event.result());

                repositoryContext.get(SalesRepository.class).save(sales, promise);
            }
        });
    }

    @Override
    void registerRepository(Vertx vertx) {
        repositoryContext.putIfAbsent(SalesRepository.class, () -> new SalesRepositoryImpl(vertx));
    }

}
