package id.blacklabs.vertx.mongo.module.product.infrastructure.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import id.blacklabs.vertx.mongo.common.Handler;
import id.blacklabs.vertx.mongo.common.PromiseHandler;
import id.blacklabs.vertx.mongo.common.PromiseWrapperHandler;
import id.blacklabs.vertx.mongo.common.argument.Arg2;
import id.blacklabs.vertx.mongo.common.argument.Args;
import id.blacklabs.vertx.mongo.document.Product;
import id.blacklabs.vertx.mongo.dto.ProductDto;
import id.blacklabs.vertx.mongo.module.product.domain.port.ProductAdapter;
import id.blacklabs.vertx.mongo.module.product.infrastructure.repository.ProductRepository;
import id.blacklabs.vertx.mongo.service.AbstractApplicationService;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.future.PromiseImpl;

import javax.inject.Named;
import java.util.Collection;
import java.util.List;

/**
 * @author krissadewo
 * @date 4/24/21 10:45 AM
 */
@Singleton
public class ProductService extends AbstractApplicationService implements ProductAdapter {

    ProductRepository productRepository;

    @Inject
    public ProductService(@Named("vertx") Vertx vertx, ProductRepository productRepository) {
        super(vertx);

        this.productRepository = productRepository;
    }

    @Override
    public void save(ProductDto dto, Handler<String> handler) {
        productRepository.save(dto.toDocument(dto), new PromiseHandler<>(handler));
    }

    @Override
    public void update(ProductDto dto, Handler<String> handler) {
        productRepository.update(dto.toDocument(dto), new PromiseHandler<>(handler));
    }

    @Override
    public void findById(String id, Handler<ProductDto> handler) {
        productRepository.findById(id, new PromiseWrapperHandler<>() {
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
    public void find(ProductDto dto, int limit, int offset, Handler<Arg2<Collection<ProductDto>, Long>> handler) {
        Promise<List<Product>> promiseProduct = new PromiseImpl<>();
        Promise<Long> promiseCount = new PromiseImpl<>();

        productRepository.find(dto.toDocument(dto), limit, offset, promiseProduct);
        productRepository.count(dto.toDocument(dto), promiseCount);

        CompositeFuture.all(promiseProduct.future(), promiseCount.future())
            .onSuccess(event -> {
                if (event.succeeded()) {
                    List<Product> products = event.result().resultAt(0);
                    Long rows = event.result().resultAt(1);

                    handler.success(Args.of(new ProductDto().toDto(products), rows));
                }
            });
    }


}
