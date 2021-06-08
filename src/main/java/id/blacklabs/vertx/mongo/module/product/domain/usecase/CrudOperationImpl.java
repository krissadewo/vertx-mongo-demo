package id.blacklabs.vertx.mongo.module.product.domain.usecase;

import com.google.inject.Inject;
import id.blacklabs.vertx.mongo.common.Handler;
import id.blacklabs.vertx.mongo.common.argument.Arg2;
import id.blacklabs.vertx.mongo.dto.ProductDto;
import id.blacklabs.vertx.mongo.module.product.domain.port.ProductAdapter;

import java.util.Collection;

/**
 * @author krissadewo
 * @date 6/5/21 12:23 PM
 */
public class CrudOperationImpl implements CrudOperation {

    private final ProductAdapter adapter;

    @Inject
    public CrudOperationImpl(ProductAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void save(ProductDto dto, Handler<String> handler) {
        adapter.save(dto, handler);
    }

    @Override
    public void update(ProductDto dto, Handler<String> handler) {
        adapter.update(dto, handler);
    }

    @Override
    public void find(ProductDto dto, int limit, int offset, Handler<Arg2<Collection<ProductDto>, Long>> handler) {
        adapter.find(dto, limit, offset, handler);
    }
}
