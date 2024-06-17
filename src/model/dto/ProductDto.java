package model.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public record ProductDto(
        Integer id,
        String productCode,
        String productName,
        String productDescription,
        Date expiredDate
) {
}
