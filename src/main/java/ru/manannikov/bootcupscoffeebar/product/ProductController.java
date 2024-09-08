package ru.manannikov.bootcupscoffeebar.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Обработчик запросов типа GET или POST на эндпоинт "/product".
 */
@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private static final String URL_PARAMETERS = "?page_number=%d&page_size=%d";
    private static final String DEFAULT_PAGE_SIZE = "20";

    private final ProductService service;

    @ModelAttribute
    public void globalAttributes(Model model) {
        model.addAttribute("thisEndpoint", "/product");
    }
    /**
     * Товаров в кофейне много и у каждого из них своя категория, поэтому пагинация необходима.
     * @param model используется для того, чтобы добавить список товаров в контекст Thymeleaf (сделать его объектом контекста Thymeleaf);
     * @return возвращает product-list.html.
     */
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    @GetMapping({"", "/"})
    public String getAll(
        @RequestParam(name = "page_number", defaultValue = "1", required = false) int pageNumber,
        @RequestParam(name = "page_size", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
        Model model
    ) {
//        if (1 == 1)
//            throw new RuntimeException("");
        Page<ProductEntity> page = service.findPage(pageNumber, pageSize);

        List<ProductDto> products = page.getContent().stream()
                .map(ProductDto::fromEntity)
                .toList();

        model.addAttribute("products", products);

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("pageSize", pageSize);

        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalElements", page.getTotalElements());

        model.addAttribute("deleteEndpoint", "/product/delete");

        return "product/product-list";
    }
    /**
     * В форме должны присутствовать все поля, у отсутствующих будет значение по умолчанию, то есть null;
     * @param model используется как связующее звено между контроллером и представлением, атрибуты model становятся переменными контекста Thymeleaf;
     * @return product-form.html для создания нового продукта.
     */
    @PreAuthorize("hasAuthority('PRODUCT_CREATE')")
    @GetMapping("/new")
    public String create(
        @RequestParam(name = "page_number", defaultValue = "1", required = false) int pageNumber,
        @RequestParam(name = "page_size", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
        Model model
    ) {
        model.addAttribute("product", ProductDto.fromEntity(new ProductEntity()));

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("pageSize", pageSize);

        return "product/product-form";
    }
    /**
     * При отправке формы, браузер выполняет http запрос типа POST для редактирования продукта у которого id = переданному в url идентификатору.
     * @param id суррогатный первичный ключ пользователя;
     * @param model
     * @return product-form -> форма для редактирования продукта
     */
    @PreAuthorize("hasAuthority('PRODUCT_UPDATE')")
    @GetMapping("/{id}")
    public String edit(
        @RequestParam(name = "page_number", defaultValue = "1", required = false) int pageNumber,
        @RequestParam(name = "page_size", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
        @PathVariable Long id,
        Model model
    ) {
        ProductDto product = ProductDto.fromEntity(service.findById(id));
        model.addAttribute("product", product);

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("pageSize", pageSize);
        return "product/product-form";
    }
    /**
     * Бразузер отправляет запрос на этот эндпоинт после того как пользователь нажал на кнопку "сохранить" в форме "product/form"
     * @param product -> Dto, сериализованный из значений полей формы;
     * @return редирект на список всех продуктов.
     */
    @PreAuthorize("hasAnyAuthority('PRODUCT_CREATE', 'PRODUCT_UPDATE')")
    @PostMapping("/save")
    public String save(
        @RequestParam(name = "page_number", defaultValue = "1", required = false) int pageNumber,
        @RequestParam(name = "page_size", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
        @ModelAttribute ProductDto product
    ) {
        service.save(product.toEntity());
        return "redirect:/product" + String.format(URL_PARAMETERS, pageNumber, pageSize);
    }
    /**
     * Операция группового удаления, браузер отправляет HTTP-запрос типа POST на этот эндпоинт после того как пользователь нажал на кнопку "Удалить продукты".
     * @param ids -> Список идентификаторов удаляемых продуктов, передаются в формате: id=1&id=2&id=3&...;
     * @return редирект на список продуктов, с учетом текущей страницы.
     */
    @PreAuthorize("hasAuthority('PRODUCT_DELETE')")
    @PostMapping("/delete")
    public String delete(
        @RequestParam(name = "page_number", defaultValue = "1", required = false) int pageNumber,
        @RequestParam(name = "page_size", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
        @RequestParam(name = "id", required = false) List<Long> ids
    ) {
        if (ids != null) {
            ids.forEach(service::delete);
        }
        return "redirect:/product" + String.format(URL_PARAMETERS, pageNumber, pageSize);
    }

}
