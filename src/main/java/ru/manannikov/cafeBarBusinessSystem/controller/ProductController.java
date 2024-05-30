package ru.manannikov.cafeBarBusinessSystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.manannikov.cafeBarBusinessSystem.service.ProductService;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
// TODO
public class ProductController {

    private final ProductService service;

    @GetMapping("")
    // возвращает list.html, передает в него список всех продуктов из бд
    public String findAll() {
        return null;
    }

    @GetMapping("/new")
    // возвращает form.html для создания нового продукта
    public String createForm() {
        return null;
    }

    @GetMapping("/{id]")
    // возвращает form.html для редактирования продукта у которого id = id переданному в url
    public String editForm(@PathVariable Long id) {
        return null;
    }

    @PostMapping("/save")
    // Возвращает редирект на GET /product
    public void save() {

    }



}
