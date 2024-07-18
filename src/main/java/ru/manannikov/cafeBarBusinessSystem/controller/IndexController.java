package ru.manannikov.cafeBarBusinessSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
// Контроллер домашней страницы
@RequestMapping("/")
public class IndexController {
    // Пока домашней страницы у нас нет возвращается перенаправление на /product
    @GetMapping
    public String index() {
       return "redirect:/product";
    }

}
