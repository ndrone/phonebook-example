package org.example.phonebookexample.app;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Nicholas Drone on 6/28/17.
 */
public enum SearchOperation
{
        LIKE(':'), EQUALS('='), LESS_THAN('<'), GREATER_THAN('>');

    private static final String OR = "|";

    private final char          operation;

    SearchOperation(char operation)
    {
        this.operation = operation;
    }


    public static String operationCaptureRegex()
    {
        return "("
            + LIKE.operation + OR + EQUALS.operation + OR + LESS_THAN.operation + OR
            + GREATER_THAN.operation + ")";
    }

    public static SearchOperation fromString(String operation)
    {
        Optional<SearchOperation> first = Arrays.stream(SearchOperation.values())
            .filter(searchOperation -> operation
                .equalsIgnoreCase(String.valueOf(searchOperation.operation)))
            .findFirst();
        return first.orElse(null);
    }
}
