package id.blacklabs.vertx.mongo.document.converter;

import id.blacklabs.vertx.mongo.document.Product;
import io.vertx.core.json.JsonObject;

/**
 * @author krissadewo
 * @date 4/26/21 10:15 PM
 */
public class ProductConverter {

    public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Product obj) {
        for (java.util.Map.Entry<String, Object> member : json) {
            switch (member.getKey()) {
                case "color":
                    if (member.getValue() instanceof String) {
                        obj.setColor((String) member.getValue());
                    }
                    break;
                case "id":
                case "_id":
                    if (member.getValue() instanceof String) {
                        obj.setId((String) member.getValue());
                    }
                    break;
                case "name":
                    if (member.getValue() instanceof String) {
                        obj.setName((String) member.getValue());
                    }
                    break;
                case "qty":
                    if (member.getValue() instanceof Number) {
                        obj.setQty(((Number) member.getValue()).intValue());
                    }
                    break;
                case "sku":
                    if (member.getValue() instanceof String) {
                        obj.setSku((String) member.getValue());
                    }
                    break;
            }
        }
    }

    public static void toJson(Product obj, JsonObject json) {
        toJson(obj, json.getMap());
    }

    public static void toJson(Product obj, java.util.Map<String, Object> json) {
        if (obj.getColor() != null) {
            json.put("color", obj.getColor());
        }

        if (obj.getId() != null) {
            json.put("id", obj.getId());
        }

        if (obj.getName() != null) {
            json.put("name", obj.getName());
        }

        json.put("qty", obj.getQty());

        if (obj.getSku() != null) {
            json.put("sku", obj.getSku());
        }
    }
}
