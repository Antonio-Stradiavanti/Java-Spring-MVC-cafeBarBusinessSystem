package ru.manannikov.cafeBarBusinessSystem.util;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.manannikov.cafeBarBusinessSystem.dto.ProductDto;
import ru.manannikov.cafeBarBusinessSystem.model.ProductEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EntityToDtoMapper {
    ProductDto mapProductEntityToDto(ProductEntity productEntity);
    ProductEntity mapProductDtoToEntity(ProductDto productDto);
}
