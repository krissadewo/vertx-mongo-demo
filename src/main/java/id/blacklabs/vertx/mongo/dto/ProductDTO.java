package id.blacklabs.vertx.mongo.dto;

import id.blacklabs.vertx.mongo.document.Product;
import id.blacklabs.vertx.mongo.dto.generic.BaseDTO;
import io.vertx.core.json.Json;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author krissadewo
 * @date 4/28/21 5:44 PM
 */
@Data
@NoArgsConstructor
public class ProductDTO implements BaseDTO<ProductDTO, Product> {

    private String id;

    private String name;

    private String sku;

    private String type;

    private int qty;

    private String color;

    @Override
    public ProductDTO toDTO(Product object) {
        ProductDTO dto = new ProductDTO();
        dto.setId(object.getId().toHexString());
        dto.setName(object.getName());
        dto.setQty(object.getQty());
        dto.setColor(object.getColor());
        dto.setSku(object.getSku());

        return dto;
    }

    @Override
    public Collection<ProductDTO> toDTO(Collection<Product> collection) {
        return collection.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public Product toDocument(String json) {
        if (json != null) {
            return Json.decodeValue(json, Product.class);
        }

        return null;
    }

    @Override
    public Product toParam(String json) {
        if (json != null) {
            Product param = Json.decodeValue(json, Product.class);

            Product product = new Product();
            product.setId(param.getId());

            return product;
        }

        return null;
    }

    @Override
    public Collection<Product> toDocument(Collection<ProductDTO> collection) {
        return null;
    }
}
