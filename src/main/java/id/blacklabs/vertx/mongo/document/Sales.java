package id.blacklabs.vertx.mongo.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.vertx.core.json.Json;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;

import java.util.Date;

/**
 * @author krissadewo
 * @date 4/24/21 7:53 PM
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class Sales {

    @BsonId
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
