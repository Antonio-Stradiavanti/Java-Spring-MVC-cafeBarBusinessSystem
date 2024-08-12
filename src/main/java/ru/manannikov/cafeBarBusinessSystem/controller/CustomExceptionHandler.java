package ru.manannikov.cafeBarBusinessSystem.controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import ru.manannikov.cafeBarBusinessSystem.exception.EntityNotFoundException;

import static org.springframework.http.HttpStatus.*;
/**
 * Аналог @Controller для класса, в котором определены методы, обрабатывающие исключения определенных типов. Вместо @RequestMapping здесь используются @ExceptionHandler, методы-обработчики вызываются при возникновении исключений, перечисленных в их списке параметров.
 */
@ControllerAdvice
public class CustomExceptionHandler {
    /**
     * @param error Исключение типа EntityNotFoundException
     * @return код ответа 404 и страницу "not-found.html". Передает в нее сообщение перехваченного исключения.
    */
    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public ModelAndView catchEntityNotFoundException(EntityNotFoundException error) {
        ModelAndView modelAndView = new ModelAndView("error/not-found");
        modelAndView.addObject("message", error.getMessage());
        return modelAndView;
    }
    /**
     * @param error исключение типа NoResourceFoundException, вызывается когда для запроса не найден обработчик;
     * @return код ответа 404 страницу "not-found.html".
     */
    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public ModelAndView catchNoHandlerFoundException(NoResourceFoundException error) {
        ModelAndView modelAndView = new ModelAndView("error/not-found");
        modelAndView.addObject("message", "Страница не найдена");
        return modelAndView;
    }
    @ExceptionHandler
    @ResponseStatus(FORBIDDEN)
    public ModelAndView handleAccessDeniedException(AccessDeniedException error) {
        ModelAndView modelAndView = new ModelAndView("error/access-denied");
        modelAndView.addObject("message", "Отказано в доступе к запрашиваемому ресурсу");
        return modelAndView;
    }
    /**
     * Большинство веб фреймворков посылают статус код 500 при если выброшенное в одном из методов исключение не было обработано;
    */
    @ExceptionHandler
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ModelAndView catchException(Exception error) {
        ModelAndView modelAndView = new ModelAndView("error/generic-error");
        modelAndView.addObject("name", error.getClass().getName());

        String message = error.getLocalizedMessage();
        if (!message.isEmpty()) message = message.substring(0, message.length() - 1);
        modelAndView.addObject("message", message);

        modelAndView.addObject("stackTrace", error.getStackTrace());
        return modelAndView;
    }
}
