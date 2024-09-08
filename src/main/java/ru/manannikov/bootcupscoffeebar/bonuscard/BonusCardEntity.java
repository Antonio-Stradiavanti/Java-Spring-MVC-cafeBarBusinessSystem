package ru.manannikov.bootcupscoffeebar.bonuscard;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.manannikov.bootcupscoffeebar.client.ClientEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "bonus_cards")
@Getter @Setter
public class BonusCardEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(
        optional = false,
        fetch = FetchType.LAZY,
        cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    // По умолчанию ссылается на столбец с первичным ключом
    @JoinColumn(unique = true)
    // name = client_id
    private ClientEntity client;

    private BigDecimal amount;
    private Double discountPercent;
}
