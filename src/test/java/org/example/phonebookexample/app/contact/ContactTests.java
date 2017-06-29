package org.example.phonebookexample.app.contact;

import org.example.phonebookexample.PojoValidator;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.openpojo.reflection.impl.PojoClassFactory;

/**
 * @author Nicholas Drone on 6/12/17.
 */
public class ContactTests
{
    @Test
    public void toStringNeverNull()
    {
        Assert.assertThat("toString must not null ever for logging", new Contact().toString(),
            Matchers.notNullValue());
    }

    @Test
    public void pojoStructureAndBehavior()
    {
        PojoValidator.build().validate(PojoClassFactory.getPojoClass(Contact.class));
    }
}
