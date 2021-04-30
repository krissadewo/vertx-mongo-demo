package id.blacklabs.vertx.mongo.common;

import id.blacklabs.vertx.mongo.document.Product;

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
}
