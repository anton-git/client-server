package com.noname.hiretask.client.util;

import com.noname.hiretask.client.settings.GlobalSettings;
import com.noname.hiretask.common.model.Bird;
import com.noname.hiretask.common.model.Sighting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Class that utilizes methods from printing data to console.
 */
public class ConsolePrinterHelper {

    private static final Logger log = LoggerFactory.getLogger(ConsolePrinterHelper.class);

    /**
     * Prints passed list of birds on the console in the defined table format.
     *
     * @param birds list of birds to print
     */
    public static void printBirds(final List<Bird> birds) {

        if (birds.isEmpty()) {
            UserConsoleUtil.println("No birds found.");
            return;
        }

        UserConsoleUtil.println("---- BIRDS LIST ----");
        printWithFixedWidth("name");
        printWithFixedWidth("color");
        printWithFixedWidth("width");
        printWithFixedWidth("height");
        UserConsoleUtil.newLine();

        birds.forEach(b -> {
            printWithFixedWidth(b.getName());
            printWithFixedWidth(b.getColor());
            printWithFixedWidth(String.valueOf(b.getWeight()));
            printWithFixedWidth(String.valueOf(b.getHeight()));
            UserConsoleUtil.newLine();
        });

        UserConsoleUtil.println("---- ");
        UserConsoleUtil.println("Total: " + birds.size());
        UserConsoleUtil.println("---- ");
    }

    /**
     * Prints passed list of sightings on the console in the defined table format.
     * @param sightings list of sightings to print
     */
    public static void printSightings(final List<Sighting> sightings) {

        if (sightings.isEmpty()) {
            UserConsoleUtil.println("No sightings found.");
            return;
        }

        UserConsoleUtil.println("---- SIGHTINGS LIST ----");
        printWithFixedWidth("Bird name");
        printWithFixedWidth("Datetime");
        printWithFixedWidth("Location");
        UserConsoleUtil.newLine();

        sightings.forEach(s -> {
            printWithFixedWidth(s.getBird());
            printWithFixedWidth(GlobalSettings.DATE_TIME_FORMATTER.format(s.getTime()));
            printWithFixedWidth(s.getLocation());
            UserConsoleUtil.newLine();
        });
    }

    private static void printWithFixedWidth(String value) {
        final int actualLength = value.length();

        if (actualLength < GlobalSettings.CONSOLE_TABLE_COLUMN_WIDTH) {

            StringBuilder builder = new StringBuilder(value);
            for (int i = GlobalSettings.CONSOLE_TABLE_COLUMN_WIDTH - actualLength; i > 0; i--) {
                builder.append(" ");
            }
            UserConsoleUtil.print(builder.toString());
        } else {
            UserConsoleUtil.print(value.substring(0, GlobalSettings.CONSOLE_TABLE_COLUMN_WIDTH));
        }
    }
}
