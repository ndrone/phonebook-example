package org.example.phonebookexample.app;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Nicholas Drone on 6/29/17.
 */
public class SearchOperationTest
{
    @Test
    public void valueOf()
    {
        SearchOperation searchOperation = SearchOperation.fromString("=");
        Assert.assertEquals(SearchOperation.EQUALS, searchOperation);
    }
}
