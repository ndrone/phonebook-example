package org.example.phonebookexample.app.contact;

import org.example.phonebookexample.app.SearchOperation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author Nicholas Drone on 6/29/17.
 */
public class ContactSpecificationBuilderTests
{
    ContactSpecificationBuilder builder;

    @Before
    public void setUp()
    {
        builder = new ContactSpecificationBuilder();
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptySearchCriteria()
    {
        builder.build();
    }

    @Test
    public void multipleSearchCriteria()
    {
        builder.with("test", SearchOperation.LIKE, "test");
        builder.with("test2", SearchOperation.EQUALS, "test");
        Specification<Contact> specification = builder.build();
        Assert.assertNotNull("Spec should not be null", specification);
    }
}
