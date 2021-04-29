package id.blacklabs.vertx.mongo.service;

import id.blacklabs.vertx.mongo.document.Product;
import id.blacklabs.vertx.mongo.repository.ProductRepository;
import id.blacklabs.vertx.mongo.repository.impl.ProductRepositoryImpl;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.future.PromiseImpl;
import lombok.Builder;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.List;

/**
 * @author krissadewo
 * @date 4/24/21 10:45 AM
 */
public class ProductService extends AbstractApplicationService {

    @Builder
    public ProductService(Vertx vertx) {
        super(vertx);
    }

    public void save(Product product, Promise<String> promise) {
        repositoryContext.get(ProductRepository.class).save(product, promise);
    }

    public void update(Product product, Promise<String> promise) {
        repositoryContext.get(ProductRepository.class).update(product, promise);
    }

    public void find(Product product, int limit, int offset, Promise<Tuple2<List<Product>, Long>> promise) {
        Promise<List<Product>> promiseProduct = new PromiseImpl<>();
        Promise<Long> promiseCount = new PromiseImpl<>();

        repositoryContext.get(ProductRepository.class).find(product, limit, offset, promiseProduct);
        repositoryContext.get(ProductRepository.class).count(product, promiseCount);

        CompositeFuture.all(promiseProduct.future(), promiseCount.future())
            .onSuccess(event -> {
                if (event.succeeded()) {
                    List<Product> products = event.result().resultAt(0);
                    Long rows = event.result().resultAt(1);

                    promise.handle(Future.succeededFuture(Tuples.of(products, rows)));
                }
            });
    }

    @Override
    void registerRepository(Vertx vertx) {
        repositoryContext.putIfAbsent(ProductRepository.class, () -> new ProductRepositoryImpl(vertx));
    }

}
