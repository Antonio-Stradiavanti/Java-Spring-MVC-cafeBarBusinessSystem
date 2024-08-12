package ru.manannikov.cafeBarBusinessSystem.dto;

import jakarta.validation.constraints.NotBlank;
import ru.manannikov.cafeBarBusinessSystem.model.ProductEntity;

public record ProductDto (
    Long id,

    @NotBlank
    String name,

    String description
)
{
    public ProductEntity toEntity() {
        return new ProductEntity(id, name, description);
    }

    public static ProductDto fromEntity(ProductEntity productEntity) {
        return new ProductDto(productEntity.getId(), productEntity.getName(), productEntity.getDescription());
    }
}
