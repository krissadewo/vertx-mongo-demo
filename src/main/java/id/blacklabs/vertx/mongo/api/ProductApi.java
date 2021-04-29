package id.blacklabs.vertx.mongo.api;

import id.blacklabs.vertx.mongo.api.response.HttpResponse;
import id.blacklabs.vertx.mongo.document.Product;
import id.blacklabs.vertx.mongo.dto.ProductDTO;
import id.blacklabs.vertx.mongo.service.ProductService;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.util.function.Tuple2;

import java.util.List;

/**
 * @author krissadewo
 * @date 4/24/21 3:09 PM
 */
public class ProductApi implements BaseApi {

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
        service.save(new ProductDTO().toDocument(context.getBodyAsString()), new PromiseResponseHandler<>() {
            @Override
            public void onSuccess(String result) {
                HttpResponse.Single single = HttpResponse.Single.builder()
                    .status(result)
                    .build();

                doSuccessResponse(context, single);
            }

            @Override
            public void onFailure(Throwable cause) {
                doFailedResponse(context);

            }
        });
    }

    private void find(RoutingContext context) {
        ProductDTO dto = new ProductDTO();

        Product param = dto.toParam(context.getBodyAsString());
        int limit = Integer.parseInt(context.queryParams().get("limit"));
        int offset = Integer.parseInt(context.queryParams().get("offset"));

        service.find(param, limit, offset, new PromiseResponseHandler<>() {
            @Override
            public void onSuccess(Tuple2<List<Product>, Long> result) {
                HttpResponse.Many many = HttpResponse.Many.builder().build();
                many.setData(dto.toDTO(result.getT1()));
                many.setRows(result.getT2());

                doSuccessResponse(context, many);
            }

            @Override
            public void onFailure(Throwable cause) {
            }
        });
    }

    private void update(RoutingContext context) {
        service.update(new ProductDTO().toDocument(context.getBodyAsString()), new PromiseResponseHandler<>() {
            @Override
            public void onSuccess(String result) {
                HttpResponse.Single single = HttpResponse.Single.builder()
                    .status(result)
                    .build();

                doSuccessResponse(context, single);
            }

            @Override
            public void onFailure(Throwable cause) {
                doFailedResponse(context);
            }
        });
    }

}
