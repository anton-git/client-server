package com.noname.hiretask.server.settings;

import java.util.*;

/**
 * Parser of arguments passed to the app.
 */
public class AppArgumentsParser implements Parser {

    @Override
    public Settings parse(String[] args) throws InitializationParameterException {

        if (args.length == 0) {
            return new Settings(DEFAULT_PORT, DEFAULT_FOLDER, DEFAULT_PROC_COUNT);
        }
        if (args.length > 6) {
            //exit early if there are to many argument
            throw new InitializationParameterException("Incorrect number of arguments");
        }

        final Map<String, Object> values = new HashMap<>(3);
        values.put(ARG_PORT, null);
        values.put(ARG_DATA, null);
        values.put(ARG_PROC_COUNT, null);

        final List<String> argsList = Arrays.asList(args);
        final Iterator<String> iterator = argsList.iterator();

        while (iterator.hasNext()) {
            final String key = iterator.next();

            if (!iterator.hasNext()) {
                //no value for option is present
                throw new InitializationParameterException("Incorrect number of arguments");
            }

            if (values.get(key) != null) {
                //option (key) was already parsed, so this one is duplicated
                throw new InitializationParameterException("Incorrect arguments");
            }

            final String value = iterator.next();
            final Object parsedValue = parseKeyValue(key, value);
            values.put(key, parsedValue);
        }

        return new Settings((Integer) values.computeIfAbsent(ARG_PORT, k -> DEFAULT_PORT),
                            (String) values.computeIfAbsent(ARG_DATA, k -> DEFAULT_FOLDER),
                            (Integer) values.computeIfAbsent(ARG_PROC_COUNT, k -> DEFAULT_PROC_COUNT));
    }

    private Object parseKeyValue(String key, String value) throws InitializationParameterException {
        switch (key) {
            case ARG_PORT:
                return parsePort(value);
            case ARG_DATA:
                return parseFolder(value);
            case ARG_PROC_COUNT:
                return parseProcCount(value);
            default:
                throw new InitializationParameterException("Incorrect arguments");
        }
    }

    private int parsePort(final String value) throws InitializationParameterException {
        if (value == null || value.isEmpty()) {
            throw new InitializationParameterException("port", InitializationExceptionReason.NO_VALUE);
        }

        try {
            final int val = Integer.valueOf(value);
            if (val < MIN_PORT_NUMBER || val > MAX_PORT_NUMBER) {
                throw new InitializationParameterException("port", InitializationExceptionReason.INCORRECT_RANGE);
            }
            return val;
        } catch (NumberFormatException e) {
            throw new InitializationParameterException("port", InitializationExceptionReason.INCORRECT_TYPE);
        }
    }

    private String parseFolder(final String value) throws InitializationParameterException {
        if (value == null || value.isEmpty()) {
            throw new InitializationParameterException("dataFolder", InitializationExceptionReason.NO_VALUE);
        }

        return value;
    }

    private int parseProcCount(final String value) throws InitializationParameterException {
        if (value == null || value.isEmpty()) {
            throw new InitializationParameterException("proc_count", InitializationExceptionReason.NO_VALUE);
        }

        try {
            final int val = Integer.valueOf(value);
            if (val < MIN_THREADS_COUNT_NUMBER || val > MAX_TREAD_COUNT_NUMBER) {
                throw new InitializationParameterException("proc_count", InitializationExceptionReason.INCORRECT_RANGE);
            }
            return val;
        } catch (NumberFormatException e) {
            throw new InitializationParameterException("proc_count", InitializationExceptionReason.INCORRECT_TYPE);
        }
    }
}
