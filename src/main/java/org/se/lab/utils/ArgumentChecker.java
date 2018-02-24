package org.se.lab.utils;

import org.apache.commons.lang3.StringUtils;

public class ArgumentChecker {

    public static void assertNotNull(final Object argument, final String name) {
        if (argument == null) {
            throw new IllegalArgumentException("Argument null: " + name);
        }
    }

    public static void assertNotNullAndEmpty(final String argument, final String argumentName) {
        if (StringUtils.isEmpty(StringUtils.trim(argument))) {
            throw new IllegalArgumentException("Argument empty: " + argumentName);
        }
    }

    public static void assertNotNullAndEmptyAndUnderMaxLength(final String argument, final String argumentName, int maxLength) {
        assertNotNullAndEmpty(argument, "argument");
        if (argument.length() > maxLength) {
            throw new IllegalArgumentException("Argument too long: " + argumentName);
        }
    }

    public static void assertValidNumber(final int argument, final String argumentName) {
        if (argument <= 0) {
            throw new IllegalArgumentException(String.format("Argument %s invalid with value %s", argumentName, argument));
        }
    }

    
    public static void assertValidNumberIncludingZeroAsValid(final int argument, final String argumentName) {
        if (argument < 0) {
            throw new IllegalArgumentException(String.format("Argument %s invalid with value %s", argumentName, argument));
        }
    }
}
