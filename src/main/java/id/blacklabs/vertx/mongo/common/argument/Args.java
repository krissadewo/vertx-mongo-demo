package id.blacklabs.vertx.mongo.common.argument;

import java.util.function.Function;

/**
 * @author krissadewo
 * @date 2/12/21 10:42 AM
 */
public abstract class Args implements Function {

    public static <T1, T2> Arg2<T1, T2> of(T1 t1, T2 t2) {
        return new Arg2<>(t1, t2);
    }

    public static <T1, T2, T3> Arg3<T1, T2, T3> of(T1 t1, T2 t2, T3 t3) {
        return new Arg3<>(t1, t2, t3);
    }

    public static <T1, T2, T3, T4> Arg4<T1, T2, T3, T4> of(T1 t1, T2 t2, T3 t3, T4 t4) {
        return new Arg4<>(t1, t2, t3, t4);
    }
}
