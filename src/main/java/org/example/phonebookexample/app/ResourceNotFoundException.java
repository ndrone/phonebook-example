package org.example.phonebookexample.app;

/**
 * @author Nicholas Drone on 6/17/17.
 */
public class ResourceNotFoundException extends RuntimeException
{

    public ResourceNotFoundException(String message)
    {
        super(message);
    }
}
