package id.blacklabs.vertx.mongo.module.product.infrastructure.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import id.blacklabs.vertx.mongo.api.BaseApi;
import id.blacklabs.vertx.mongo.api.response.HttpResponse;
import id.blacklabs.vertx.mongo.common.Handler;
import id.blacklabs.vertx.mongo.common.argument.Arg2;
import id.blacklabs.vertx.mongo.dto.ProductDto;
import id.blacklabs.vertx.mongo.module.product.domain.usecase.CrudOperation;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.util.Collection;

/**
 * @author krissadewo
 * @date 4/24/21 3:09 PM
 */
@Singleton
public class ProductApi implements BaseApi {

    private final CrudOperation operation;

    private static final Logger logger = LoggerFactory.getLogger(ProductApi.class);

    @Inject
    public ProductApi(@Named("router") Router router, CrudOperation operation) {
        this.operation = operation;

        router.route().handler(BodyHandler.create());
        router.post("/products").handler(this::save);
        router.get("/products").handler(this::find);
        router.put("/products").handler(this::update);
    }

    private void save(RoutingContext context) {
        operation.save(new ProductDto().fromJson(context.getBodyAsString()), new Handler<>() {
            public void success(String t) {
                HttpResponse.Single single = HttpResponse.Single.builder()
                    .status(t)
                    .build();

                doSuccessResponse(context, single);
            }

            public void failure(Throwable throwable) {
                doFailedResponse(context);
            }
        });
    }

    private void find(RoutingContext context) {
        ProductDto dto = new ProductDto();

        int limit = Integer.parseInt(context.queryParams().get("limit"));
        int offset = Integer.parseInt(context.queryParams().get("offset"));

        operation.find(dto, limit, offset, new Handler<>() {
            @Override
            public void success(Arg2<Collection<ProductDto>, Long> objects) {
                HttpResponse.Many many = HttpResponse.Many.builder()
                    .data(objects.getT1())
                    .rows(objects.getT2())
                    .build();

                doSuccessResponse(context, many);
            }

            @Override
            public void failure(Throwable throwable) {
                doFailedResponse(context);
            }
        });
    }

    private void update(RoutingContext context) {
        operation.update(new ProductDto().fromJson(context.getBodyAsString()), new Handler<>() {
            @Override
            public void success(String result) {
                HttpResponse.Single single = HttpResponse.Single.builder()
                    .status(result)
                    .build();

                doSuccessResponse(context, single);
            }

            @Override
            public void failure(Throwable cause) {
                doFailedResponse(context);
            }
        });
    }

}
