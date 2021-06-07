package id.blacklabs.vertx.mongo.common;

/**
 * @author krissadewo
 * @date 6/7/21 1:19 PM
 */
public abstract class HandlerImpl<T> extends Handler<T> {

    public abstract void success(T t);

    public abstract void failure(Throwable throwable);
}
