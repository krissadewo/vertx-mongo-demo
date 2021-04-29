package id.blacklabs.vertx.mongo.context;

import io.vertx.core.Vertx;
import io.vertx.core.shareddata.LocalMap;

import java.util.function.Supplier;

/**
 * @author krissadewo
 * @date 4/26/21 3:46 PM
 */
public class ConfigContext {

    private final LocalMap<String, Object> sharedConfig;

    final Object lock = new Object();

    public ConfigContext(Vertx vertx) {
        sharedConfig = vertx.sharedData().getLocalMap("config-context");
    }

    public <T> void putIfAbsent(Class<T> clazz, Supplier<T> supplier) {
        synchronized (lock) {
            if (get(clazz) == null) {
                sharedConfig.put(clazz.getName(), supplier.get());
            }

            get(clazz);
        }
    }

    public <T> T get(Class<T> clazz) {
        return clazz.cast(sharedConfig.get(clazz.getName()));
    }
}
