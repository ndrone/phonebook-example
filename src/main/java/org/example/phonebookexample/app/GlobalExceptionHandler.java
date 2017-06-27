package org.example.phonebookexample.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A catch all for any exception out of the controllers that are not specifically handled
 * already
 *
 * @author Nicholas Drone on 6/17/17.
 */
@ControllerAdvice
public class GlobalExceptionHandler
{
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Catches any unhandled exceptions, logs them and then returns a 500 Http status code
     * These should be fixed asap.
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorMessage unHandledException(Exception e)
    {
        log.error("Error: {}", e);
        return new ErrorMessage(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResourceNotFoundException resourceNotFoundException(ResourceNotFoundException e)
    {
        return e;
    }
}
