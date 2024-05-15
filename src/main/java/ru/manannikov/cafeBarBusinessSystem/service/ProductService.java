package ru.manannikov.cafeBarBusinessSystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.manannikov.cafeBarBusinessSystem.model.ProductEntity;
import ru.manannikov.cafeBarBusinessSystem.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    // Внедрение зависимости через конструктор. Аннотация @RequiredArgs создает конструктор с обязательными параметрами, к таковым относятся неизменяемые (финальные) свойства и свойства аннотированные аннотациями Spring Validation такими как @NotNull.
    private final ProductRepository productRepository;

    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }
    public ProductEntity findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
    public ProductEntity save(ProductEntity product) {
        return productRepository.save(product);
    }
    public void delete(ProductEntity product) {
        productRepository.delete(product);
    }
}
