package ru.manannikov.bootcupscoffeebar.product;

import jakarta.validation.constraints.NotBlank;

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
