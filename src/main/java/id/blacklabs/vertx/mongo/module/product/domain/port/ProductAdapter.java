package id.blacklabs.vertx.mongo.module.product.domain.port;

import id.blacklabs.vertx.mongo.common.Handler;
import id.blacklabs.vertx.mongo.common.argument.Arg2;
import id.blacklabs.vertx.mongo.dto.ProductDto;
import reactor.util.function.Tuple2;

import java.util.Collection;

/**
 * @author krissadewo
 * @date 6/5/21 12:21 PM
 */
public interface ProductAdapter {

    void save(ProductDto dto, Handler<String> handler);

    void update(ProductDto dto, Handler<String> handler);

    void findById(String id, Handler<ProductDto> handler);

    void find(ProductDto dto, int limit, int offset, Handler<Arg2<Collection<ProductDto>, Long>> handler);
}
