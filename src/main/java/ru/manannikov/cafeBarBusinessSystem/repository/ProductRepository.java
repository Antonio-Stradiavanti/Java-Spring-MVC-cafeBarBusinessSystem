package ru.manannikov.cafeBarBusinessSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.manannikov.cafeBarBusinessSystem.model.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
