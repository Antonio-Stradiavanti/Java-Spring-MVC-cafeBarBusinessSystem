package ru.manannikov.bootcupscoffeebar.client;

import ru.manannikov.bootcupscoffeebar.bonuscard.BonusCardDto;

public record ClientDto(
  String name,
  BonusCardDto bonusCard
) {
  public static ClientDto fromEntity(ClientEntity clientEntity) {
    return new ClientDto(
      clientEntity.getName(),
      BonusCardDto.fromEntity(clientEntity.getBonusCard())
    );
  }
}
