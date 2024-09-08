package ru.manannikov.bootcupscoffeebar.security;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService service;

    @ModelAttribute
    public void globalAttributes(Model model) {
        model.addAttribute("thisEndpoint", "/users");
    }
    /**
     * Список сотрудников будет не очень большим, поэтому пагинация не требуется.
     * @param model -> ассоциативный массив объектов, которые должны быть доступны в контексте шаблона Thymeleaf user-list.
     * @return user-list -> таблица со списком всех пользователей
     */
    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping({"", "/"})
    public String findAll(Model model) {
        List<UserDto> users = service.findAll().stream().map(UserDto::fromEntity).toList();
        model.addAttribute("users", users);

        model.addAttribute("deleteEndpoint", "/users/delete");

        return "user/user-list";
    }
    /**
     * @param model -> ассоциативный массив объектов, которые должны быть доступны в контексте шаблона Thymeleaf user-form.
     * @return user-form -> форма регистрации сотрудника.
     */
    @PreAuthorize("hasAuthority('USER_CREATE')")
    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("user", UserFormDto.fromEntity(new UserEntity()));
        return "user/user-form";
    }

    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @GetMapping("/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        UserFormDto user = UserFormDto.fromEntity(service.findById(id));
        model.addAttribute("user", user);
        return "user/user-form";
    }
    /**
     *
     * @param user
     * @return
     */
    @PreAuthorize("hasAnyAuthority('USER_CREATE', 'USER_UPDATE')")
    @PostMapping("/save")
    public String save(
        @ModelAttribute UserFormDto user,
        BindingResult bindingResult
    ) {
        if (!user.password().equals(user.confirmPassword())) {
            bindingResult.addError(new FieldError("user", "confirmPassword", "Пароли должны совпадать"));
        }
        if (bindingResult.hasErrors()) {
            return "user/user-form";
        }
        service.save(user.toEntity());
        return "redirect:/users";
    }
    /**
     * Операция группового удаления, браузер отправляет HTTP-запрос типа POST на этот эндпоинт после того как пользователь нажал на кнопку "Уволить сотрудников".
     * @param ids -> Список идентификаторов увольняемых сотрудников, передаются в формате: id=1&id=2&id=3&...;
     * @return редирект на список сотрудников.
     */
    @PreAuthorize("hasAuthority('USER_DELETE')")
    @PostMapping("/delete")
    public String delete(
        @RequestParam(name = "id", required = false) List<Long> ids
    ) {
        if (ids != null) {
            ids.forEach(service::delete);
        }
        return "redirect:/users";
    }
}
