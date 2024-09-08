package ru.manannikov.bootcupscoffeebar.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.manannikov.bootcupscoffeebar.exceptions.EntityNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public List<ProductEntity> findAll() {
        return repository.findAll();
    }

    public Page<ProductEntity> findPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return repository.findAll(pageable);
    }

    public ProductEntity findById(Long id) {
        return repository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                    String.format("Продукт с идентификатором %d не найден.", id)
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
