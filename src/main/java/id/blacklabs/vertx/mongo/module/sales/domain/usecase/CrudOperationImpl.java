package id.blacklabs.vertx.mongo.module.sales.domain.usecase;

import com.google.inject.Inject;
import id.blacklabs.vertx.mongo.common.Handler;
import id.blacklabs.vertx.mongo.dto.ProductDto;
import id.blacklabs.vertx.mongo.dto.SalesDto;
import id.blacklabs.vertx.mongo.module.product.domain.port.ProductAdapter;
import id.blacklabs.vertx.mongo.module.sales.domain.port.SalesAdapter;

import java.util.concurrent.CompletableFuture;

/**
 * @author krissadewo
 * @date 6/5/21 3:26 PM
 */
public class CrudOperationImpl implements CrudOperation {

    SalesAdapter salesAdapter;

    ProductAdapter productAdapter;

    @Inject
    public CrudOperationImpl(SalesAdapter salesAdapter, ProductAdapter productAdapter) {
        this.salesAdapter = salesAdapter;
        this.productAdapter = productAdapter;
    }

    @Override
    public void save(SalesDto dto, Handler<String> handler) {
        Handler<ProductDto> productHandler = new Handler<>();

        productAdapter.findById(dto.getProduct().getId(), productHandler);

        productHandler
            .exitException(handler)
            .thenApply(productDto -> {
                if (productDto != null) {
                    dto.setProduct(productDto);

                    salesAdapter.save(dto, handler);
                }

                return CompletableFuture.completedFuture(handler);
            });
    }
}
