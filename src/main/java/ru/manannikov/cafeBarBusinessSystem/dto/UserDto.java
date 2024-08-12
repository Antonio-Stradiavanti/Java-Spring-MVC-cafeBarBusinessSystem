package ru.manannikov.cafeBarBusinessSystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ru.manannikov.cafeBarBusinessSystem.enums.Role;
import ru.manannikov.cafeBarBusinessSystem.model.UserEntity;

import java.time.LocalDate;

public record UserDto(
    Long id,

    @NotEmpty(message = "Введите адрес электронной почты")
    @Email(message = "Введите допустимый адрес электронной почты")
    String email,
    @NotBlank(message = "Введите пароль")
    @Size(min = 3, message = "Пароль должен содержать хотя бы 3 символа")
    String password,

    String lastname,
    String firstname,
    String patronym,

    @Size(min = 10, max = 16, message = "Введен номер телефона недопустимого формата.")
    @Pattern(regexp = "^([+]{1}[0-9]{1,3}\\s?[0-9()]{3,5}\\s?[0-9]{3}\\s?[0-9]{2}[-\\s]?[0-9]{2})$", message = "Введите номер телефона в формате, соответствующем стандарту T-REC-E.123")
    String phoneNumber,

    LocalDate birthday,
    Role role
) {
    public UserEntity toEntity() {
        return new UserEntity(
            id,

            email,
            password,

            lastname,
            firstname,
            patronym,

            phoneNumber,
            birthday,

            role
        );
    }

    public static UserDto fromEntity(UserEntity user) {
        return new UserDto(
            user.getId(),
            user.getUsername(),
            user.getPassword(),

            user.getLastname(),
            user.getFirstname(),
            user.getPatronym(),

            user.getPhoneNumber(),
            user.getBirthday(),
            user.getRole()
        );
    }
}
