package id.blacklabs.vertx.mongo.common;

import io.vertx.core.Vertx;
import io.vertx.core.shareddata.LocalMap;

import java.util.function.Supplier;

/**
 * @author krissadewo
 * @date 4/26/21 3:46 PM
 */
public class RepositoryContext {

    private final LocalMap<String, Object> sharedDependencies;

    final Object lock = new Object();

    public RepositoryContext(Vertx vertx) {
        sharedDependencies = vertx.sharedData().getLocalMap("Dependencies");
    }

    public <T> void putIfAbsent(Class<T> clazz, Supplier<T> supplier) {
        synchronized (lock) {
            if (get(clazz) == null) {
                sharedDependencies.put(clazz.getName(), supplier.get());
            }

            get(clazz);
        }
    }

    public <T> T get(Class<T> clazz) {
        return clazz.cast(sharedDependencies.get(clazz.getName()));
    }
}
