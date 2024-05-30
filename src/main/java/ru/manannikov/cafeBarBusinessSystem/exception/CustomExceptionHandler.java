package ru.manannikov.cafeBarBusinessSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
// Аналог @Config для класса, в котором определены методы, обрабатывающие исключения определенных типов.
// TODO
public class CustomExceptionHandler {

    @ExceptionHandler
    // Возвращает код ответа 404 и страницу  not_found.html. Передает в нее сообщение перехваченного исключения
    public ResponseEntity<String> catchEntityNotFoundException(EntityNotFoundException e) {
        return new ResponseEntity<>("./error/not_found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> catchException(Exception e) {
        return null;
    }
}
