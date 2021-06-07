package id.blacklabs.vertx.mongo.common;

import id.blacklabs.vertx.mongo.document.Product;
import id.blacklabs.vertx.mongo.document.Sales;
import id.blacklabs.vertx.mongo.dto.ProductDto;
import id.blacklabs.vertx.mongo.dto.SalesDto;
import org.bson.types.ObjectId;

/**
 * @author krissadewo
 * @date 4/30/21 10:16 AM
 */
public class DummyData {

    public Product product() {
        Product product = new Product();
        product.setName("Honda City");
        product.setColor("RED");
        product.setQty(2);
        product.setSku("1234JNHJJ");
        product.setType("SEDAN");

        return product;
    }

    public SalesDto sales() {
        ProductDto product = new ProductDto();
        product.setId("60bdd3b7e4d1f82e38762dc3");
        product.setName("Honda City");
        product.setColor("RED");
        product.setQty(2);
        product.setSku("1234JNHJJ");
        product.setType("SEDAN");

        SalesDto sales = new SalesDto();
        sales.setProduct(product);
        sales.setQty(20);

        return sales;
    }
}
