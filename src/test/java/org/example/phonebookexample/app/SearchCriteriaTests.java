package org.example.phonebookexample.app;

import org.example.phonebookexample.PojoValidator;
import org.junit.Test;

import com.openpojo.reflection.impl.PojoClassFactory;

/**
 * @author Nicholas Drone on 6/29/17.
 */
public class SearchCriteriaTests
{
    @Test
    public void pojoStructureAndBehavior()
    {
        PojoValidator.build(false).validate(PojoClassFactory.getPojoClass(SearchCriteria.class));
    }
}
