package org.example.phonebookexample;

import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.*;
import com.openpojo.validation.test.impl.DefaultValuesNullTester;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

/**
 * @author Nicholas Drone on 6/29/17.
 */
public class PojoValidator
{
    public static Validator build()
    {
        return build(true);
    }

    public static Validator build(boolean withDefaultValuesNull)
    {
        ValidatorBuilder builder = ValidatorBuilder.create()
            // Rules
            .with(new GetterMustExistRule(), new NoPrimitivesRule(), new NoPublicFieldsRule(),
                new SerializableMustHaveSerialVersionUIDRule(), new SetterMustExistRule())
            // Testers
            .with(new GetterTester(), new SetterTester());

        if (withDefaultValuesNull)
        {
            builder.with(new DefaultValuesNullTester());
        }
        return builder.build();
    }
}
