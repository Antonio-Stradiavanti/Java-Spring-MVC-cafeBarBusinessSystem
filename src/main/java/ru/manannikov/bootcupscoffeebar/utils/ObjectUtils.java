package ru.manannikov.bootcupscoffeebar.utils;

/**
 * Служебные статические методы проекта
 */
public class ObjectUtils {
    public static <T> T doTry(ThrowingSupplier<T> function) {
        try {
            return function.get();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
