package id.blacklabs.vertx.mongo.repository;

import io.vertx.core.Promise;
import io.vertx.core.shareddata.Shareable;

import java.util.List;

/**
 * @author krissadewo
 * @date 4/26/21 2:51 PM
 */
public interface CrudRepository<T> extends Shareable {

    void save(T document, Promise<String> promise);

    void update(T document, Promise<String> promise);

    void delete(String id, Promise<String> promise);

    void findById(String id, Promise<T> promise);

    void find(T param, int limit, int offset, Promise<List<T>> promise);

    void count(T param, Promise<Long> promise);

}
