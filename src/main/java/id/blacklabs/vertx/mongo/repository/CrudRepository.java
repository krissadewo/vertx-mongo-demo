package id.blacklabs.vertx.mongo.repository;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.shareddata.Shareable;

import java.util.List;

/**
 * @author krissadewo
 * @date 4/26/21 2:51 PM
 */
public interface CrudRepository<T> extends Shareable {

    void save(T document, Handler<AsyncResult<String>> resultHandler);

    void update(T document, Handler<AsyncResult<String>> resultHandler);

    void delete(String id, Handler<AsyncResult<String>> resultHandler);

    void findById(String id, Handler<AsyncResult<T>> resultHandler);

    void find(T param, int limit, int offset, Handler<AsyncResult<List<T>>> resultHandler);

    void count(T param, Handler<AsyncResult<Long>> resultHandler);

}
