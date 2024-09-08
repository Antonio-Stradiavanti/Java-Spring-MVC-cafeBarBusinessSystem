package ru.manannikov.bootcupscoffeebar.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Arrays;

public record UserFormDto(
        Long id,

        @NotEmpty(message = "Введите адрес электронной почты")
        @Email(message = "Введите допустимый адрес электронной почты")
        String email,

        @NotBlank(message = "Введите пароль")
        String password,
        @NotBlank(message = "Повторите введенный пароль")
        String confirmPassword,

        @Pattern(regexp = "^([А-Я])[-'а-я]{2,64}\\s([А-Я])[а-я]{2,32}(?<!ов)(?<!ич)(?<!ова)(?<!вна)\\s([А-Я]?)[а-я]{0,36}$", message = "Введите фамилию и имя")
        String fullName,

        @Size(min = 10, max = 16, message = "Номер телефона должен содержать от 10 до 16 символов включительно")
        @Pattern(regexp = "^([+]{1}[0-9]{1,3}\\s?[0-9()]{3,5}\\s?[0-9]{3}\\s?[0-9]{2}[-\\s]?[0-9]{2})$", message = "Введите номер телефона в формате, соответствующем стандарту T-REC-E.123")
        String phoneNumber,

        LocalDate birthday,
        Role role
) {
    public UserEntity toEntity() {

        String[] splitFullName = fullName.split(" ", 3);

        String[] names = new String[3];
        Arrays.fill(names, "");

        for (int i = 0; i < splitFullName.length; ++i) {
            names[i] = splitFullName[i];
        }

        return new UserEntity(
                id,

                email,
                password,

                names[0],
                names[1],
                names[2],

                phoneNumber,
                birthday,

                role
        );
    }

    public static UserFormDto fromEntity(UserEntity user) {
        return new UserFormDto(
                user.getId(),

                user.getUsername(),

                "",
                "",

                String.format("%s %s %s", user.getLastname(), user.getFirstname(), user.getPatronym()),

                user.getPhoneNumber(),
                user.getBirthday(),

                user.getRole()
        );
    }
}
