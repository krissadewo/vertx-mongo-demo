package id.blacklabs.vertx.mongo.module.product.infrastructure.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import id.blacklabs.vertx.mongo.common.Handler;
import id.blacklabs.vertx.mongo.common.PromiseHandler;
import id.blacklabs.vertx.mongo.common.PromiseResponseHandler;
import id.blacklabs.vertx.mongo.document.Product;
import id.blacklabs.vertx.mongo.dto.ProductDto;
import id.blacklabs.vertx.mongo.module.product.domain.port.ProductAdapter;
import id.blacklabs.vertx.mongo.module.product.infrastructure.repository.ProductRepository;
import id.blacklabs.vertx.mongo.module.product.infrastructure.repository.impl.ProductRepositoryImpl;
import id.blacklabs.vertx.mongo.service.AbstractApplicationService;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.future.PromiseImpl;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import javax.inject.Named;
import java.util.Collection;
import java.util.List;

/**
 * @author krissadewo
 * @date 4/24/21 10:45 AM
 */
@Singleton
public class ProductService extends AbstractApplicationService implements ProductAdapter {

    @Inject
    public ProductService(@Named("vertx") Vertx vertx) {
        super(vertx);
    }

    @Override
    public void save(ProductDto dto, Handler<String> handler) {
        repositoryContext.get(ProductRepository.class).save(dto.toDocument(dto), new PromiseHandler<>(handler));
    }

    @Override
    public void update(ProductDto dto, Handler<String> handler) {
        repositoryContext.get(ProductRepository.class).update(dto.toDocument(dto), new PromiseHandler<>(handler));
    }

    @Override
    public void findById(String id, Handler<ProductDto> handler) {
        repositoryContext.get(ProductRepository.class).findById(id, new PromiseResponseHandler<>() {
            @Override
            public void onSuccess(Product result) {
                handler.success(new ProductDto().toDto(result));
            }

            @Override
            public void onFailure(Throwable cause) {
                handler.failure(cause);
            }
        });
    }

    @Override
    public void find(ProductDto dto, int limit, int offset, Handler<Tuple2<Collection<ProductDto>, Long>> handler) {
        Promise<List<Product>> promiseProduct = new PromiseImpl<>();
        Promise<Long> promiseCount = new PromiseImpl<>();

        repositoryContext.get(ProductRepository.class).find(dto.toDocument(dto), limit, offset, promiseProduct);
        repositoryContext.get(ProductRepository.class).count(dto.toDocument(dto), promiseCount);

        CompositeFuture.all(promiseProduct.future(), promiseCount.future())
                .onSuccess(event -> {
                    if (event.succeeded()) {
                        List<Product> products = event.result().resultAt(0);
                        Long rows = event.result().resultAt(1);

                        handler.success(Tuples.of(new ProductDto().toDto(products), rows));
                    }
                });
    }

    @Override
    public void registerRepository(Vertx vertx) {
        repositoryContext.putIfAbsent(ProductRepository.class, () -> new ProductRepositoryImpl(vertx));
    }
}
