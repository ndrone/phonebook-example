package org.example.phonebookexample.app.contact;

import org.example.phonebookexample.app.SearchCriteria;
import org.example.phonebookexample.app.SearchOperation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 * @author Nicholas Drone on 6/29/17.
 */
public class ContactSpecificationTests
{
    Root            root            = Mockito.mock(Root.class);
    Path            path            = Mockito.mock(Path.class);
    CriteriaQuery   criteriaQuery   = Mockito.mock(CriteriaQuery.class);
    CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);

    @Before
    public void setUp()
    {
        Mockito.reset(root, path, criteriaQuery, criteriaBuilder);

        Mockito.when(root.get(Mockito.anyString())).thenReturn(path);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorFailure()
    {
        new ContactSpecification(null);
    }

    @Test
    public void likePredicate()
    {
        ContactSpecification specification = new ContactSpecification(
            new SearchCriteria("firstname", SearchOperation.LIKE, "tom"));

        specification.toPredicate(root, criteriaQuery, criteriaBuilder);

        Mockito.verify(root, Mockito.times(1)).get(Mockito.eq("firstname"));
        Mockito.verify(criteriaBuilder, Mockito.times(1)).like(Mockito.eq(path),
            Mockito.eq("%tom%"));
    }

    @Test
    public void equalsPredicate()
    {
        ContactSpecification specification = new ContactSpecification(
                new SearchCriteria("firstname", SearchOperation.EQUALS, "tom"));

        specification.toPredicate(root, criteriaQuery, criteriaBuilder);

        Mockito.verify(root, Mockito.times(1)).get(Mockito.eq("firstname"));
        Mockito.verify(criteriaBuilder, Mockito.times(1)).equal(Mockito.eq(path),
                Mockito.eq("tom"));
    }
}
