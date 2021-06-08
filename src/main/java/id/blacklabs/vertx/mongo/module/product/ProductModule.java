package id.blacklabs.vertx.mongo.module.product;

import com.google.inject.AbstractModule;
import id.blacklabs.vertx.mongo.module.product.domain.port.ProductAdapter;
import id.blacklabs.vertx.mongo.module.product.domain.usecase.CrudOperation;
import id.blacklabs.vertx.mongo.module.product.domain.usecase.CrudOperationImpl;
import id.blacklabs.vertx.mongo.module.product.infrastructure.api.ProductApi;
import id.blacklabs.vertx.mongo.module.product.infrastructure.repository.ProductRepository;
import id.blacklabs.vertx.mongo.module.product.infrastructure.repository.impl.ProductRepositoryImpl;
import id.blacklabs.vertx.mongo.module.product.infrastructure.service.ProductService;

/**
 * @author krissadewo
 * @date 6/7/21 6:35 PM
 */
public class ProductModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ProductApi.class).asEagerSingleton();

        bind(ProductAdapter.class).to(ProductService.class);
        bind(CrudOperation.class).to(CrudOperationImpl.class);

        bind(ProductRepository.class).to(ProductRepositoryImpl.class);
    }

}
