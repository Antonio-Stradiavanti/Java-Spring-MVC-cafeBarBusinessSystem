package ru.manannikov.cafeBarBusinessSystem.exception;

public class EntityNotFoundException extends RuntimeException {
    EntityNotFoundException(String message) {
        super(message);
    }
}
