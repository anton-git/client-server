package com.noname.hiretask.server.settings;

/**
 * Reasons why initialization exception was thrown.
 */
public enum InitializationExceptionReason {
    /**
     * No value were found
     */
    NO_VALUE,

    /**
     * Incorrect type of value
     */
    INCORRECT_TYPE,

    /**
     * Value does not fit in allowed range of values
     */
    INCORRECT_RANGE,

    /**
     * Name of parameter is incorrect
     */
    INCORRECT_PARAMETER_NAME
}
