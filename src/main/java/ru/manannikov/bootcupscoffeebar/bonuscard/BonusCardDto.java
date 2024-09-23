package ru.manannikov.bootcupscoffeebar.bonuscard;

import java.math.BigDecimal;

public record BonusCardDto(
  BigDecimal amount,
  Double discountPercent
) {
  public static BonusCardDto fromEntity(BonusCardEntity bonusCardEntity) {
    return new BonusCardDto(
      bonusCardEntity.getAmount(),
      bonusCardEntity.getDiscountPercent() * 100.0
    );
  }
}
