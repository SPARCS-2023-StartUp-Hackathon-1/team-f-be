package com.sparcs.teamf.common.util;


import com.sparcs.teamf.api.error.exception.HttpException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Repeat {

    private static final int DEFAULT_MAX_ATTEMPTS = 10;

    private Repeat() {
    }

    public static <T> Optional<T> repeat(Supplier<Optional<T>> supplier,
            Predicate<Optional<T>> needToRepeat,
            Supplier<HttpException> exceptionSupplier) throws InterruptedException {

        for (int attempt = 0; attempt < DEFAULT_MAX_ATTEMPTS; attempt++) {
            Optional<T> result;
            try {
                result = supplier.get();
                if (needToRepeat.test(result)) {
                    Thread.sleep(1000);
                }
                return result;
            } catch (RuntimeException ignored) {
            }
        }
        throw exceptionSupplier.get();
    }
}
