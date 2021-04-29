package id.blacklabs.vertx.mongo.api;

import id.blacklabs.vertx.mongo.api.response.HttpResponse;
import id.blacklabs.vertx.mongo.dto.SalesDTO;
import id.blacklabs.vertx.mongo.service.SalesService;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author krissadewo
 * @date 4/26/21 2:36 PM
 */
public class SalesApi implements BaseApi {

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
        service.save(new SalesDTO().toDocument(context.getBodyAsString()), new PromiseResponseHandler<>() {
            @Override
            public void onSuccess(String result) {
                HttpResponse.Single single = HttpResponse.Single.builder()
                    .status(result)
                    .build();

                doSuccessResponse(context, single);
            }

            @Override
            public void onFailure(Throwable cause) {
            }
        });
    }

}
