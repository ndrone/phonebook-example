package org.example.phonebookexample.app;

import java.time.format.DateTimeFormatter;

/**
 * @author Nicholas Drone on 7/23/17.
 */
public final class ApplicationUtils
{
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private ApplicationUtils()
    {
    }
}
