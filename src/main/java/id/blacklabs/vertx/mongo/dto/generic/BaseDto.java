package id.blacklabs.vertx.mongo.dto.generic;

import com.fasterxml.jackson.annotation.JsonInclude;
import id.blacklabs.vertx.mongo.document.DocumentAware;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author krissadewo
 * @date 4/28/21 5:45 PM
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface BaseDto<D, E extends DocumentAware> extends Serializable {

    D toDto(E object);

    Collection<D> toDto(Collection<E> collection);

    E toDocument(D dto);

    D toParam(String json);

    Collection<E> toDocument(Collection<D> collection);

    D fromJson(String json);
}
