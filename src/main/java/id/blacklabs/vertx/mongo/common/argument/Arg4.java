package id.blacklabs.vertx.mongo.common.argument;

/**
 * @author krissadewo
 * @date 2/12/21 10:45 AM
 */
public class Arg4<T1, T2, T3, T4> extends Arg3<T1, T2, T3> {

    final T4 t4;

    Arg4(T1 t1, T2 t2, T3 t3, T4 t4) {
        super(t1, t2, t3);
        this.t4 = t4;
    }

    public Object[] toArray() {
        return new Object[]{t1, t2, t3, t4};
    }
}
