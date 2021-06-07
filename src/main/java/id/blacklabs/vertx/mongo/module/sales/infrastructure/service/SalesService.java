package id.blacklabs.vertx.mongo.module.sales.infrastructure.service;

import com.google.inject.Inject;
import id.blacklabs.vertx.mongo.common.Handler;
import id.blacklabs.vertx.mongo.common.PromiseHandler;
import id.blacklabs.vertx.mongo.dto.SalesDto;
import id.blacklabs.vertx.mongo.module.sales.domain.port.SalesAdapter;
import id.blacklabs.vertx.mongo.module.sales.infrastructure.repository.SalesRepository;
import id.blacklabs.vertx.mongo.module.sales.infrastructure.repository.impl.SalesRepositoryImpl;
import id.blacklabs.vertx.mongo.service.AbstractApplicationService;
import io.vertx.core.Vertx;

import javax.inject.Named;

/**
 * @author krissadewo
 * @date 4/26/21 2:32 PM
 */
public class SalesService extends AbstractApplicationService implements SalesAdapter {

    @Inject
    public SalesService(@Named("vertx") Vertx vertx) {
        super(vertx);
    }

    @Override
    public void save(SalesDto dto, Handler<String> handler) {
        repositoryContext.get(SalesRepository.class).save(dto.toDocument(dto), new PromiseHandler<>(handler));
    }

    @Override
    protected void registerRepository(Vertx vertx) {
        repositoryContext.putIfAbsent(SalesRepository.class, () -> new SalesRepositoryImpl(vertx));
    }

}
