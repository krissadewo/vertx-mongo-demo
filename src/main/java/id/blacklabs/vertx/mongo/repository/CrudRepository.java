package id.blacklabs.vertx.mongo.repository;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * @author krissadewo
 * @date 4/26/21 2:51 PM
 */
public interface CrudRepository<T> {

    void save(T document, Handler<AsyncResult<String>> resultHandler);

    void update(T document, Handler<AsyncResult<String>> resultHandler);

    void delete(String id, Handler<AsyncResult<String>> resultHandler);

    void findById(String id, Handler<AsyncResult<T>> resultHandler);

    void find(T param, int limit, int offset, Handler<AsyncResult<T>> resultHandler);

}
