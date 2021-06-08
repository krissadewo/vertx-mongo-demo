package id.blacklabs.vertx.mongo.common.argument;

import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author krissadewo
 * @date 2/12/21 10:34 AM
 * <p>
 * Handle multiple arguments in an {@link java.util.function.Function}
 * It's like {@link reactor.util.function.Tuple2} but each argument can be null
 */
@Getter
public class Arg2<T1, T2> implements Iterable<Object>, Serializable {

    private static final long serialVersionUID = -6998305977219086789L;

    final T1 t1;
    final T2 t2;

    Arg2(T1 t1, T2 t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public Object[] toArray() {
        return new Object[]{t1, t2};
    }

    public List<Object> toList() {
        return Arrays.asList(toArray());
    }

    @Override
    public Iterator<Object> iterator() {
        return Collections.unmodifiableList(toList()).iterator();
    }

}
