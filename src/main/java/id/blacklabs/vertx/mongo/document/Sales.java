package id.blacklabs.vertx.mongo.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;

/**
 * @author krissadewo
 * @date 4/24/21 7:53 PM
 */

@Data
@NoArgsConstructor
public class Sales implements DocumentAware {

    @BsonId
    private String id;

    private Product product;

    private int qty;

    private long createdTime = System.currentTimeMillis();

}
