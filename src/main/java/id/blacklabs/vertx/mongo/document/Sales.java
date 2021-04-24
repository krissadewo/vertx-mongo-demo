package id.blacklabs.vertx.mongo.document;

import io.vertx.codegen.annotations.DataObject;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author krissadewo
 * @date 4/24/21 7:53 PM
 */
@DataObject(generateConverter = true)
@NoArgsConstructor
public class Sales {

    private String id;

    private Product product;

    private int qty;

    private Date createdTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
