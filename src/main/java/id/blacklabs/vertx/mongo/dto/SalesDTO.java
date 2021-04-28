package id.blacklabs.vertx.mongo.dto;

import id.blacklabs.vertx.mongo.document.Product;
import id.blacklabs.vertx.mongo.document.Sales;
import id.blacklabs.vertx.mongo.dto.generic.BaseDTO;
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
public class SalesDTO implements BaseDTO<SalesDTO, Sales> {

    private String id;

    private Product product;

    private int qty;

    private long createdTime;

    @Override
    public SalesDTO toDTO(Sales object) {
        SalesDTO dto = new SalesDTO();
        dto.setId(object.getId());
        dto.setQty(object.getQty());
        dto.setCreatedTime(object.getCreatedTime());

        return dto;
    }

    @Override
    public Collection<SalesDTO> toDTO(Collection<Sales> collection) {
        return collection.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public Sales toDocument(String json) {
        return Json.decodeValue(json, Sales.class);
    }

    @Override
    public Sales toParam(String json) {
        return null;
    }

    @Override
    public Collection<Sales> toDocument(Collection<SalesDTO> collection) {
        return null;
    }
}
