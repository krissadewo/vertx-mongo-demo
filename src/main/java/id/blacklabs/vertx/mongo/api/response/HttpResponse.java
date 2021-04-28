package id.blacklabs.vertx.mongo.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author krissadewo
 * @date 1/29/21 10:19 AM
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    public static class Single {

        private Object data;

        private String status;
    }

}
