package id.blacklabs.vertx.mongo.api;

import id.blacklabs.vertx.mongo.document.Product;
import id.blacklabs.vertx.mongo.document.Sales;
import id.blacklabs.vertx.mongo.service.SalesService;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author krissadewo
 * @date 4/26/21 2:36 PM
 */
public class SalesApi {

    private final SalesService service;

    private static final Logger logger = LoggerFactory.getLogger(SalesApi.class);

    @Builder
    public SalesApi(Router router, SalesService service) {
        this.service = service;

        router.clear();
        router.route().handler(BodyHandler.create());
        router.post("/sales").handler(this::save);
    }

    private void save(RoutingContext context) {
        service.save(new SalesDTO().toDocument(context.getBodyAsString()), event -> {
            logger.info(event.result());

            if (event.succeeded()) {
                context.response()
                    .putHeader("content-type", "application/json")
                    .end("success");
            } else {
                context.response()
                    .putHeader("content-type", "application/json")
                    .end("failed");
            }
        });
    }

    @Getter
    @Setter
    @NoArgsConstructor
    static class SalesDTO implements Serializable {

        private String id;

        private Product product;

        private int qty;

        private Date createdTime;

        public SalesDTO toDTO(Sales object) {
            SalesDTO dto = new SalesDTO();
            dto.setId(object.getId());
            dto.setQty(object.getQty());
            dto.setCreatedTime(object.getCreatedTime());

            return dto;
        }

        public Collection<SalesDTO> toDTO(Collection<Sales> collection) {
            return collection.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        }

        public Sales toDocument(String json) {
            return Json.decodeValue(json, Sales.class);
        }
    }
}
