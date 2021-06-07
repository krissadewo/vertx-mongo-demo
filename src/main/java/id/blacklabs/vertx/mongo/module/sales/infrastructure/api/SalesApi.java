package id.blacklabs.vertx.mongo.module.sales.infrastructure.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import id.blacklabs.vertx.mongo.api.BaseApi;
import id.blacklabs.vertx.mongo.api.response.HttpResponse;
import id.blacklabs.vertx.mongo.common.Handler;
import id.blacklabs.vertx.mongo.dto.SalesDto;
import id.blacklabs.vertx.mongo.module.sales.domain.usecase.CrudOperation;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

/**
 * @author krissadewo
 * @date 4/26/21 2:36 PM
 */
@Singleton
public class SalesApi implements BaseApi {

    private final CrudOperation operation;

    private static final Logger logger = LoggerFactory.getLogger(SalesApi.class);

    @Inject
    public SalesApi(@Named("router") Router router, CrudOperation operation) {
        this.operation = operation;

        router.route().handler(BodyHandler.create());
        router.post("/sales").handler(this::save);
    }

    private void save(RoutingContext context) {
        operation.save(new SalesDto().fromJson(context.getBodyAsString()), new Handler<>() {
            @Override
            public void success(String result) {
                HttpResponse.Single single = HttpResponse.Single.builder()
                    .status(result)
                    .build();

                doSuccessResponse(context, single);
            }

            @Override
            public void failure(Throwable cause) {
            }
        });
    }

}
