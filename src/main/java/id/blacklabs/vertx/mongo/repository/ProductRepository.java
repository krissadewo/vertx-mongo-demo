package id.blacklabs.vertx.mongo.repository;

import id.blacklabs.vertx.mongo.document.Product;
import id.blacklabs.vertx.mongo.repository.impl.ProductRepositoryImpl;
import io.vertx.codegen.annotations.ProxyClose;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.mongo.MongoClient;

/**
 * @author krissadewo
 * @date 4/24/21 10:48 AM
 */
@ProxyGen
@VertxGen
public interface ProductRepository {

    static ProductRepository create(MongoClient mongoClient) {
        return new ProductRepositoryImpl(mongoClient);
    }

    static ProductRepository createProxy(Vertx vertx, String address) {
        return new ProductRepositoryVertxEBProxy(vertx, address);
    }

    void save(Product product, Handler<AsyncResult<String>> resultHandler);

    void update(Product product, Handler<AsyncResult<String>> resultHandler);

    @ProxyClose
    void close();

}
