package ru.manannikov.bootcupscoffeebar.client;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.manannikov.bootcupscoffeebar.bonuscard.BonusCardEntity;
import ru.manannikov.bootcupscoffeebar.telegrambot.RegistrationState;

import java.time.LocalDate;

import static ru.manannikov.bootcupscoffeebar.telegrambot.RegistrationState.ASK_REGISTRATION;

@Entity
@Table(name = "clients")
@Getter @Setter
@Builder
public class ClientEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long chatId;
    private String languageCode;

    private String name;
    private LocalDate birthday;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private RegistrationState registrationState = ASK_REGISTRATION;

    @OneToOne(
        mappedBy = "client",
        cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private BonusCardEntity bonusCard;
}
