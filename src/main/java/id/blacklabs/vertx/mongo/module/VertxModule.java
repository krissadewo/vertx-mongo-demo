package id.blacklabs.vertx.mongo.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

import javax.inject.Named;

/**
 * @author krissadewo
 * @date 6/7/21 6:55 PM
 */
public class VertxModule extends AbstractModule {

    private final Vertx vertx;

    public final Router router;

    public VertxModule(Vertx vertx, Router router) {
        this.vertx = vertx;
        this.router = router;
    }

    @Provides
    @Named("vertx")
    Vertx vertx() {
        return this.vertx;
    }

    @Provides
    @Named("router")
    Router router() {
        return this.router;
    }

}
