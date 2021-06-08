package id.blacklabs.vertx.mongo.common.argument;

import lombok.Getter;

/**
 * @author krissadewo
 * @date 2/12/21 10:34 AM
 */
@Getter
public class Arg3<T1, T2, T3> extends Arg2<T1, T2> {

    private static final long serialVersionUID = -6998305977219086789L;

    final T3 t3;

    Arg3(T1 t1, T2 t2, T3 t3) {
        super(t1, t2);
        this.t3 = t3;
    }

    public Object[] toArray() {
        return new Object[]{t1, t2, t3};
    }

}
