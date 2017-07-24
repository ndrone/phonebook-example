package org.example.phonebookexample.app;

import org.springframework.util.Assert;

/**
 * @author Nicholas Drone on 6/28/17.
 */
public class SearchCriteria
{
    private String          key;
    private SearchOperation operation;
    private Object          value;

    public SearchCriteria(String key, SearchOperation operation, Object value)
    {
        Assert.hasText(key, "key must not be null");
        this.key = key;
        Assert.notNull(operation, "operation must not be null");
        this.operation = operation;
        Assert.notNull(value, "value must not be null");
        this.value = value;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public SearchOperation getOperation()
    {
        return operation;
    }

    public void setOperation(SearchOperation operation)
    {
        this.operation = operation;
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }
}
