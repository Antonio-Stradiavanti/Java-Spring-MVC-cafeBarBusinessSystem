package ru.manannikov.cafeBarBusinessSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="products")
@NoArgsConstructor
@Getter
@Setter
public class ProductEntity {
    @Id @GeneratedValue
    Long id;
    String name;
    String description;

    public ProductEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
