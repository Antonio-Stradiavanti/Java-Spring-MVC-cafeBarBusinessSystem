package ru.manannikov.bootcupscoffeebar.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер аутентификации
 */
@Controller
public class AuthController {
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
    /**
     * Привязан к кнопке logout;
     * @param request нужен, чтобы завершить текущий сеанс, сделать пользователя неавторизированным;
     * @return перенаправляет на страницу login.
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate();
        }
        return "redirect:/login";
    }
}
