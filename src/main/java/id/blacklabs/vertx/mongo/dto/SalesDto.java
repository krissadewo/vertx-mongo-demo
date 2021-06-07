package id.blacklabs.vertx.mongo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import id.blacklabs.vertx.mongo.document.Sales;
import id.blacklabs.vertx.mongo.dto.generic.BaseDto;
import io.vertx.core.json.Json;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author krissadewo
 * @date 4/28/21 5:51 PM
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SalesDto implements BaseDto<SalesDto, Sales>, Dto {

    private String id;

    private ProductDto product;

    private int qty;

    private long createdTime;

    @Override
    public SalesDto toDto(Sales object) {
        SalesDto dto = new SalesDto();
        dto.setId(object.getId());
        dto.setQty(object.getQty());
        dto.setCreatedTime(object.getCreatedTime());

        return dto;
    }

    @Override
    public Collection<SalesDto> toDto(Collection<Sales> collection) {
        return collection.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public Sales toDocument(SalesDto dto) {
        Sales sales = new Sales();
        sales.setProduct(new ProductDto().toDocument(dto.getProduct()));
        sales.setQty(dto.getQty());
        sales.setCreatedTime(dto.getCreatedTime());

        return sales;
    }

    @Override
    public SalesDto toParam(String json) {
        return null;
    }

    @Override
    public Collection<Sales> toDocument(Collection<SalesDto> collection) {
        return null;
    }

    @Override
    public SalesDto fromJson(String json) {
        return Json.decodeValue(json, SalesDto.class);
    }
}
