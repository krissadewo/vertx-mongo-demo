package id.blacklabs.vertx.mongo.module.sales.domain.port;

import id.blacklabs.vertx.mongo.common.Handler;
import id.blacklabs.vertx.mongo.dto.SalesDto;

/**
 * @author krissadewo
 * @date 6/5/21 3:24 PM
 */
public interface SalesAdapter {

    void save(SalesDto dto, Handler<String> handler);
}
