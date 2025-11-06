package com.weatherintegration.util;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * note this class is just for demo.
 */
public class Random {
    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    public static Boolean randomBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    public static Double randomDouble() {
        return ThreadLocalRandom.current().nextDouble();
    }

    public static <E extends Enum<E>> E randomEnum(Class<E> enumClass) {
        E[] values = enumClass.getEnumConstants();
        return values[ThreadLocalRandom.current().nextInt(values.length)];
    }
}
