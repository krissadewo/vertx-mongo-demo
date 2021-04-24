package id.blacklabs.vertx.mongo.document;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import lombok.NoArgsConstructor;

/**
 * @author krissadewo
 * @date 4/24/21 10:45 AM
 */
@DataObject(generateConverter = true)
@NoArgsConstructor
public class Product {

    private String id;

    private String name;

    private String sku;

    private int qty;

    private String color;

    public Product(JsonObject json) {
        ProductConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        return JsonObject.mapFrom(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
