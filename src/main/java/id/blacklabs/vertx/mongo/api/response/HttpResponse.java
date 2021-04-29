package id.blacklabs.vertx.mongo.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author krissadewo
 * @date 1/29/21 10:19 AM
 */
public class HttpResponse implements Serializable {

    private static final long serialVersionUID = 8947296487836491359L;

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Many {

        private Object data;

        private String status;

        private Long rows;

    }

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Single {

        private Object data;

        private String status;
    }

}
