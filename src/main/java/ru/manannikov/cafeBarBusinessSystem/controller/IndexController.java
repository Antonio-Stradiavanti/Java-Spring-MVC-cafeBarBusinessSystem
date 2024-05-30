package ru.manannikov.cafeBarBusinessSystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// Контроллер домашней страницы
@RequestMapping("/")
public class IndexController {
    // Пока домашней страницы у нас нет возвращается перенаправление на /product
    public String homePage() {
       return null;
    }

}
