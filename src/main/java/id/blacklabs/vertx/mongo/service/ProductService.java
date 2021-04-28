package id.blacklabs.vertx.mongo.service;

import id.blacklabs.vertx.mongo.document.Product;
import id.blacklabs.vertx.mongo.repository.ProductRepository;
import id.blacklabs.vertx.mongo.repository.impl.ProductRepositoryImpl;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import lombok.Builder;

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

    public void save(Product product, Handler<AsyncResult<String>> resultHandler) {
        repositoryContext.get(ProductRepository.class).save(product, resultHandler);
    }

    public void update(Product product, Handler<AsyncResult<String>> resultHandler) {
        repositoryContext.get(ProductRepository.class).update(product, resultHandler);
    }

    public void find(Product product, Handler<AsyncResult<List<Product>>> resultHandler) {
        repositoryContext.get(ProductRepository.class).find(product, 0, 10, resultHandler);
    }

    public void count(Product product, Handler<AsyncResult<Long>> resultHandler) {
        repositoryContext.get(ProductRepository.class).count(product, resultHandler);
    }

    @Override
    void registerRepository(Vertx vertx) {
        repositoryContext.putIfAbsent(ProductRepository.class, () -> new ProductRepositoryImpl(vertx));
    }

}
