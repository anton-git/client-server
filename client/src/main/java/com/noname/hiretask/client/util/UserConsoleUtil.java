package com.noname.hiretask.client.util;

import com.noname.hiretask.client.settings.GlobalSettings;
import com.noname.hiretask.client.command.UserInputValidationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static com.noname.hiretask.client.settings.GlobalSettings.DEFAULT_CHARSET_NAME;

/**
 * Class interacts with a user via console: gets data from user, and prints information for user.
 */
public class UserConsoleUtil {

    /**
     * Prints the message with a new line in the end.
     *
     * @param message a message to print
     */
    public static void println(final String message) {
        System.out.println(message);
    }

    /**
     * Prints the message. Next message will be printed on the same line.
     * @param message a message to print
     */
    public static void print(final String message) {
        System.out.print(message);
    }

    /**
     * Makes a new line - next message will be displayed on a new line.
     */
    public static void newLine() {
        System.out.println();
    }

    /**
     * Gets a string value from a user. Method asks a user to enter a values until it is correct - non-empty.
     * @param fieldName value for that fieldName user is asked to enter
     * @return non-empty String value
     */
    public static String getCorrectStringValue(final String fieldName) {
        final Scanner scanner = new Scanner(System.in, DEFAULT_CHARSET_NAME);
        while (true) {
            try {
                UserConsoleUtil.print(fieldName + ": ");
                String value = scanner.nextLine();
                validateNonEmptyValue(value);
                return value;
            } catch (UserInputValidationException e) {
                UserConsoleUtil.println("Validation error: " + e.getMessage());
                UserConsoleUtil.println("Try again.");
            }
        }
    }

    /**
     * Gets an Integer value from a user. Method asks a user to enter a values until it is correct - integer and positive.
     * @param fieldName value for that fieldName user is asked to enter
     * @return Integer positive value
     */
    public static Integer getCorrectNumberValue(final String fieldName) {
        final Scanner scanner = new Scanner(System.in, DEFAULT_CHARSET_NAME);
        while (true) {
            try {
                UserConsoleUtil.print(fieldName + ": ");
                Integer number = Integer.parseInt(scanner.nextLine());
                validatePositiveNumber(number);
                return number;
            } catch (UserInputValidationException | NumberFormatException e) {
                UserConsoleUtil.println("Validation error: " + e.getMessage());
                UserConsoleUtil.println("Try again.");
            }
        }
    }

    /**
     * Gets a date-time value from a user. Method asks a user to enter a values until it is correct - non-empty
     * and of specified format which is defined in {@link GlobalSettings#DATE_TIME_FORMATTER}
     * @param fieldName value for that fieldName user is asked to enter
     * @return Integer positive value
     */
    public static LocalDateTime getCorrectDateTimeValue(final String fieldName) {
        final Scanner scanner = new Scanner(System.in, DEFAULT_CHARSET_NAME);
        while (true) {
            try {
                UserConsoleUtil.print(fieldName + " (DD-MM-YYYY hh:mm  e.g. '01-09-2017 15:01') " + ": ");
                String value = scanner.nextLine();
                validateNonEmptyValue(value);
                return parseDateTime(value);
            } catch (UserInputValidationException e) {
                UserConsoleUtil.println("Validation error: " + e.getMessage());
                UserConsoleUtil.println("Try again.");
            }
        }
    }

    private static void validatePositiveNumber(Integer number) {
        if (number < 0) {
            throw new UserInputValidationException("Negative number.");
        }
    }

    private static void validateNonEmptyValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new UserInputValidationException("Empty value.");
        }
    }

    private static LocalDateTime parseDateTime(final String value) {
        try {
            return LocalDateTime.parse(value, GlobalSettings.DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new UserInputValidationException("Incorrect date time format.");
        }
    }

}
