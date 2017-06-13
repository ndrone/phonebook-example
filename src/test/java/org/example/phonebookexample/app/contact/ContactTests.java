package org.example.phonebookexample.app.contact;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.*;
import com.openpojo.validation.test.impl.BusinessIdentityTester;
import com.openpojo.validation.test.impl.DefaultValuesNullTester;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

/**
 * @author Nicholas Drone on 6/12/17.
 */
public class ContactTests
{
    @Test
    public void toStringNeverNull()
    {
        Assert.assertThat("not null", new Contact(), Matchers.notNullValue());
    }

    @Test
    public void pojoStructureAndBehavior()
    {
        PojoClass contactPojo = PojoClassFactory.getPojoClass(Contact.class);

        Validator validator = ValidatorBuilder.create()
            // Rules
            .with(new GetterMustExistRule(), new NoPrimitivesRule(), new NoPublicFieldsRule(),
                new SerializableMustHaveSerialVersionUIDRule(), new SetterMustExistRule())
            // Testers
            .with(new DefaultValuesNullTester(), new GetterTester(), new SetterTester(),
                new BusinessIdentityTester())
            .build();

        validator.validate(contactPojo);
    }
}
