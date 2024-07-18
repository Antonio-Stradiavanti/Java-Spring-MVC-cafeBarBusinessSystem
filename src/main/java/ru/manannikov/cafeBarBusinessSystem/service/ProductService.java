package ru.manannikov.cafeBarBusinessSystem.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.manannikov.cafeBarBusinessSystem.dto.ProductDto;
import ru.manannikov.cafeBarBusinessSystem.exception.EntityNotFoundException;
import ru.manannikov.cafeBarBusinessSystem.model.ProductEntity;
import ru.manannikov.cafeBarBusinessSystem.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    // Сервис оперирует Enity, а контроллер -> Dto
    // Внедрение зависимости через конструктор. Аннотация @RequiredArgs создает конструктор с обязательными параметрами, к таковым относятся неизменяемые (финальные) свойства и свойства аннотированные аннотациями Spring Validation такими как @NotNull.
    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository repository;

    public List<ProductEntity> findAll() {
        return repository.findAll();
    }

    public ProductEntity findById(Long id) {
        return repository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                    String.format("Продукт с идентфикатором %d не найден.", id)
                )
            );
    }

    public ProductEntity save(ProductEntity product) {
        return repository.save(product);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
