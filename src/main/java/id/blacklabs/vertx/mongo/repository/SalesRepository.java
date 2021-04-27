package id.blacklabs.vertx.mongo.repository;

import id.blacklabs.vertx.mongo.document.Sales;
import id.blacklabs.vertx.mongo.repository.impl.SalesRepositoryImpl;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.shareddata.Shareable;
import io.vertx.ext.mongo.MongoClient;

/**
 * @author krissadewo
 * @date 4/26/21 2:27 PM
 */
@ProxyGen
@VertxGen
public interface SalesRepository extends Shareable {

    static SalesRepository create(MongoClient mongoClient) {
        return new SalesRepositoryImpl(mongoClient);
    }

    static SalesRepository createProxy(Vertx vertx, String address) {
        return new SalesRepositoryVertxEBProxy(vertx, address);
    }

    void save(Sales sales, Handler<AsyncResult<String>> resultHandler);
}
