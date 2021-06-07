package id.blacklabs.vertx.mongo.module.sales;

import com.google.inject.AbstractModule;
import id.blacklabs.vertx.mongo.module.sales.domain.port.SalesAdapter;
import id.blacklabs.vertx.mongo.module.sales.domain.usecase.CrudOperation;
import id.blacklabs.vertx.mongo.module.sales.domain.usecase.CrudOperationImpl;
import id.blacklabs.vertx.mongo.module.sales.infrastructure.api.SalesApi;
import id.blacklabs.vertx.mongo.module.sales.infrastructure.service.SalesService;

/**
 * @author krissadewo
 * @date 6/7/21 6:35 PM
 */
public class SalesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SalesApi.class).asEagerSingleton();

        bind(SalesAdapter.class).to(SalesService.class);
        bind(CrudOperation.class).to(CrudOperationImpl.class);
    }
}
