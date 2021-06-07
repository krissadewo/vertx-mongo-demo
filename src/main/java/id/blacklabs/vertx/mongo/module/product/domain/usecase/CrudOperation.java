package id.blacklabs.vertx.mongo.module.product.domain.usecase;

import id.blacklabs.vertx.mongo.common.Handler;
import id.blacklabs.vertx.mongo.dto.ProductDto;
import reactor.util.function.Tuple2;

import java.util.Collection;

/**
 * @author krissadewo
 * @date 6/5/21 12:23 PM
 */
public interface CrudOperation {

    void save(ProductDto dto, Handler<String> handler);

    void update(ProductDto dto, Handler<String> handler);

    void find(ProductDto dto, int limit, int offset, Handler<Tuple2<Collection<ProductDto>, Long>> handler);
}
