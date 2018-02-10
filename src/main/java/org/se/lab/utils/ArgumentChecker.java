package org.se.lab.utils;

import org.apache.commons.lang3.StringUtils;

public class ArgumentChecker {

    public static void assertNotNull(final Object argument, final String name) {
        if (argument == null) {
            throw new IllegalArgumentException("Argument null: " + name);
        }
    }

    public static void assertNotNullAndEmpty(final String argument, final String argumentName) {
        if (StringUtils.isEmpty(argument)) {
            throw new IllegalArgumentException("Argument empty: " + argumentName);
        }
    }

}
