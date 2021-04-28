package id.blacklabs.vertx.mongo.document;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @author krissadewo
 * @date 4/28/21 5:45 PM
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface DocumentAware extends Serializable {
}
