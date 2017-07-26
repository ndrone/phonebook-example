package org.example.phonebookexample.app;

/**
 * @author Nicholas Drone on 7/26/17.
 */
public class BadRequestException extends RuntimeException
{
    public BadRequestException(String message)
    {
        super(message);
    }
}
