package id.blacklabs.vertx.mongo.api;

import id.blacklabs.vertx.mongo.document.Product;
import id.blacklabs.vertx.mongo.service.ProductService;
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
import java.util.stream.Collectors;

/**
 * @author krissadewo
 * @date 4/24/21 3:09 PM
 */
public class ProductApi {

    private final ProductService service;

    private static final Logger logger = LoggerFactory.getLogger(ProductApi.class);

    @Builder
    public ProductApi(Router router, ProductService service) {
        this.service = service;

        router.clear();
        router.route().handler(BodyHandler.create());
        router.post("/products").handler(this::save);
        router.get("/products").handler(this::get);
    }

    private void save(RoutingContext context) {
        service.save(new ProductDTO().toDocument(context.getBodyAsString()), event -> {
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

    private void get(RoutingContext context) {
        service.find(new ProductDTO().toDocument(context.getBodyAsString()), event -> {
            if (event.succeeded()) {
                context.response()
                    .putHeader("content-type", "application/json")
                    .end(Json.encode(new ProductDTO().toDTO(event.result())));
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
    static class ProductDTO implements Serializable {

        private String id;

        private String name;

        private String sku;

        private String type;

        private int qty;

        private String color;

        public ProductDTO toDTO(Product object) {
            ProductDTO dto = new ProductDTO();
            dto.setId(object.getId().toHexString());
            dto.setName(object.getName());
            dto.setQty(object.getQty());
            dto.setColor(object.getColor());
            dto.setSku(object.getSku());

            return dto;
        }

        public Collection<ProductDTO> toDTO(Collection<Product> collection) {
            return collection.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        }

        public Product toDocument(String json) {
            return Json.decodeValue(json, Product.class);
        }
    }
}
