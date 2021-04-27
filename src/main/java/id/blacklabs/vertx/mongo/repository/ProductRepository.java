package id.blacklabs.vertx.mongo.repository;

import id.blacklabs.vertx.mongo.document.Product;
import id.blacklabs.vertx.mongo.repository.impl.ProductRepositoryImpl;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.shareddata.Shareable;
import io.vertx.ext.mongo.MongoClient;

/**
 * @author krissadewo
 * @date 4/24/21 10:48 AM
 */
@ProxyGen
@VertxGen
public interface ProductRepository extends Shareable {

    static ProductRepository create(MongoClient mongoClient) {
        return new ProductRepositoryImpl(mongoClient);
    }

    static ProductRepository createProxy(Vertx vertx, String address) {
        return new ProductRepositoryVertxEBProxy(vertx, address);
    }

    void save(Product document, Handler<AsyncResult<String>> resultHandler);

    void update(Product document, Handler<AsyncResult<String>> resultHandler);

    void delete(String id, Handler<AsyncResult<String>> resultHandler);

    void findById(String id, Handler<AsyncResult<Product>> resultHandler);

    void find(Product param, int limit, int offset, Handler<AsyncResult<Product>> resultHandler);

}
