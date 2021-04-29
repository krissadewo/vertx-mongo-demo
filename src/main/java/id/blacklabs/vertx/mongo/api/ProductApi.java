package id.blacklabs.vertx.mongo.api;

import id.blacklabs.vertx.mongo.api.response.HttpResponse;
import id.blacklabs.vertx.mongo.document.Product;
import id.blacklabs.vertx.mongo.dto.ProductDTO;
import id.blacklabs.vertx.mongo.service.ProductService;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        router.get("/products").handler(this::find);
        router.put("/products").handler(this::update);
    }

    private void save(RoutingContext context) {
        service.save(new ProductDTO().toDocument(context.getBodyAsString()), event -> {
            logger.info(event.result());

            if (event.succeeded()) {
                HttpResponse.Single single = HttpResponse.Single.builder()
                    .status(event.result())
                    .build();

                doSuccessResponse(context, single);
            } else {
                doFailedResponse(context);
            }
        });
    }

    private void find(RoutingContext context) {
        ProductDTO dto = new ProductDTO();
        HttpResponse.Many many = HttpResponse.Many.builder().build();

        Product param = dto.toParam(context.getBodyAsString());
        int limit = Integer.parseInt(context.queryParams().get("limit"));
        int offset = Integer.parseInt(context.queryParams().get("offset"));

        service.find(param, limit, offset, event -> {
            if (event.succeeded()) {
                many.setData(dto.toDTO(event.result()));

                service.count(dto.toParam(context.getBodyAsString()), count -> {
                    if (count.succeeded()) {
                        many.setRows(count.result());

                        doSuccessResponse(context, many);
                    }
                });
            } else {
                doFailedResponse(context);
            }
        });
    }

    private void update(RoutingContext context) {
        service.update(new ProductDTO().toDocument(context.getBodyAsString()), event -> {
            if (event.succeeded()) {
                HttpResponse.Single single = HttpResponse.Single.builder()
                    .status(event.result())
                    .build();

                doSuccessResponse(context, single);
            } else {
                doFailedResponse(context);
            }
        });
    }

    private Future<Void> doSuccessResponse(RoutingContext context, Object object) {
        return context.response()
            .putHeader("content-type", "application/json")
            .end(Json.encode(object));
    }

    private Future<Void> doFailedResponse(RoutingContext context) {
        return context.response()
            .putHeader("content-type", "application/json")
            .end("failed");
    }

}
