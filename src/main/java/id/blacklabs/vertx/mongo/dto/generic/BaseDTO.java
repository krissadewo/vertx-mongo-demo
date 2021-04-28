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
public interface BaseDTO<D, E extends DocumentAware> extends Serializable {

    D toDTO(E object);

    Collection<D> toDTO(Collection<E> collection);

    E toDocument(String json);

    E toParam(String json);

    Collection<E> toDocument(Collection<D> collection);
}
