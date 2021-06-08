package id.blacklabs.vertx.mongo.module.product.domain.usecase;

import id.blacklabs.vertx.mongo.common.Handler;
import id.blacklabs.vertx.mongo.common.argument.Arg2;
import id.blacklabs.vertx.mongo.dto.ProductDto;

import java.util.Collection;

/**
 * @author krissadewo
 * @date 6/5/21 12:23 PM
 */
public interface CrudOperation {

    void save(ProductDto dto, Handler<String> handler);

    void update(ProductDto dto, Handler<String> handler);

    void find(ProductDto dto, int limit, int offset, Handler<Arg2<Collection<ProductDto>, Long>> handler);
}
