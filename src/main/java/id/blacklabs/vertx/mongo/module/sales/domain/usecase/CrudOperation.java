package id.blacklabs.vertx.mongo.module.sales.domain.usecase;

import id.blacklabs.vertx.mongo.common.Handler;
import id.blacklabs.vertx.mongo.dto.SalesDto;

/**
 * @author krissadewo
 * @date 6/5/21 3:26 PM
 */
public interface CrudOperation {

    void save(SalesDto dto, Handler<String> handler);
}
