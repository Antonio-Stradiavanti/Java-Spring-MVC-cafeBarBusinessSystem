package ru.manannikov.cafeBarBusinessSystem.dto;

import ru.manannikov.cafeBarBusinessSystem.model.ProductEntity;

// Назвначение Dto классов -> передача данных от сервера к клиенту, они обеспечивают правильное форматирование JSON, HTML
public record ProductDto (
    Long id,
    String name,
    String description
)
{
    public static ProductDto mapProductEntityToDto(ProductEntity productEntity) {
        return new ProductDto(productEntity.getId(), productEntity.getName(), productEntity.getDescription());
    }

    public ProductEntity mapToEntity() {
        return new ProductEntity(id, name, description);
    }
}
