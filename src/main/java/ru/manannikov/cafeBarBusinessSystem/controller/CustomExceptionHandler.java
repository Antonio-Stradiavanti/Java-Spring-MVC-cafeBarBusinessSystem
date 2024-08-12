package ru.manannikov.cafeBarBusinessSystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import ru.manannikov.cafeBarBusinessSystem.exception.EntityNotFoundException;

@ControllerAdvice
// Аналог @Config для класса, в котором определены методы, обрабатывающие исключения определенных типов.
public class CustomExceptionHandler {

    // Возвращает код ответа 404 и страницу  not_found.html. Передает в нее сообщение перехваченного исключения
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView catchEntityNotFoundException(EntityNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error/not_found");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }

    // Вызывается когда для запроса не найден обработчик
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView catchNoHandlerFoundException(NoResourceFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error/not_found");
        modelAndView.addObject("message", "Страница не найдена");
        return modelAndView;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    // Большинство веб фреймворков посылают этот статус код по умолчанию, если контроллер выплюнул необработанное исключение;
    public ModelAndView catchException(Exception e) {
        ModelAndView modelAndView = new ModelAndView("error/generic_error");
        // Так гораздо удобнее.
        modelAndView.addObject("name", e.getClass().getName());

        String message = e.getLocalizedMessage();
        if (!message.isEmpty()) message = message.substring(0, message.length() - 1);
        modelAndView.addObject("message", message);

        modelAndView.addObject("stackTrace", e.getStackTrace());
        return modelAndView;
    }
}
