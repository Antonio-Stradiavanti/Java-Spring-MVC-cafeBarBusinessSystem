package ru.manannikov.cafeBarBusinessSystem.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import ru.manannikov.cafeBarBusinessSystem.dto.ProductDto;
import ru.manannikov.cafeBarBusinessSystem.model.ProductEntity;
import ru.manannikov.cafeBarBusinessSystem.service.ProductService;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
// TODO
public class ProductController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);
    private final ProductService service;

    @GetMapping({"", "/"})
    // возвращает list.html, передает в него список всех продуктов из бд
    public String findAll(Model model) {
        if (1 == 1)
            throw new RuntimeException("");
        List<ProductDto> products = service.findAll()
                .stream()
                .map(ProductDto::mapProductEntityToDto)
                .toList();

        model.addAttribute("products", products);

        return "product/list";
    }

    @GetMapping("/new")
    // возвращает form.html для создания нового продукта
    public String createForm(Model model) {
        model.addAttribute("product", ProductDto.mapProductEntityToDto(new ProductEntity()));
        return "product/form";
    }

    // GET, т.к. метод возвращает form.html, а при отправке формы, браузер уже выполняет http запрос типа POST для редактирования продукта у которого id = переданному в url идентификатору.
    @GetMapping("/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        ProductDto product = ProductDto.mapProductEntityToDto(service.findById(id));
        model.addAttribute(
            "product",
            product
        );
        return "product/form";
    }

    // Возвращает редирект на GET /product, то есть получаем список всех продуктов
    @PostMapping("/save")
    public String save(@ModelAttribute ProductDto product) {
        service.save(product.mapToEntity());
        return "redirect:/product";
    }
    // GET, так как тут мы получаем форму, а операцию удаление выполняем внутренне.
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/product";
    }

}
