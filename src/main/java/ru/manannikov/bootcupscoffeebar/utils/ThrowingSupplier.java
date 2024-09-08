package ru.manannikov.bootcupscoffeebar.utils;

@FunctionalInterface
public interface ThrowingSupplier<T> {
    T get() throws Exception;
}
