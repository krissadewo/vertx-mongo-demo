package id.blacklabs.vertx.mongo.dto;

import id.blacklabs.vertx.mongo.document.Product;
import id.blacklabs.vertx.mongo.dto.generic.BaseDto;
import io.vertx.core.json.Json;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author krissadewo
 * @date 4/28/21 5:44 PM
 */
@Data
@NoArgsConstructor
public class ProductDto implements BaseDto<ProductDto, Product>, Dto {

    private String id;

    private String name;

    private String sku;

    private String type;

    private int qty;

    private String color;

    @Override
    public ProductDto toDto(Product object) {
        ProductDto dto = new ProductDto();
        dto.setId(object.getId().toHexString());
        dto.setName(object.getName());
        dto.setQty(object.getQty());
        dto.setColor(object.getColor());
        dto.setSku(object.getSku());

        return dto;
    }

    @Override
    public Collection<ProductDto> toDto(Collection<Product> collection) {
        return collection.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public Product toDocument(ProductDto dto) {
        Product object = new Product();
        object.setId(dto.getId() != null ? new ObjectId(dto.getId()) : null);
        object.setName(dto.getName());
        object.setQty(dto.getQty());
        object.setColor(dto.getColor());
        object.setSku(dto.getSku());

        return object;
    }

    @Override
    public ProductDto toParam(String json) {
        if (json != null) {
            return Json.decodeValue(json, ProductDto.class);
        }

        return new ProductDto();
    }

    @Override
    public Collection<Product> toDocument(Collection<ProductDto> collection) {
        return null;
    }

    @Override
    public ProductDto fromJson(String json) {
        if (json != null) {
            return Json.decodeValue(json, ProductDto.class);
        }

        return null;
    }
}
