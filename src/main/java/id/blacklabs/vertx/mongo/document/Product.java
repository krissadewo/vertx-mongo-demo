package id.blacklabs.vertx.mongo.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

/**
 * @author krissadewo
 * @date 4/24/21 10:45 AM
 */
@Data
@NoArgsConstructor
public class Product implements DocumentAware {

    @BsonId
    private ObjectId id;

    private String name;

    private String sku;

    private String type;

    private int qty;

    private String color;

}
