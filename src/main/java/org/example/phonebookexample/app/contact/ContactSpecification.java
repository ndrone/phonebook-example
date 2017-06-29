package org.example.phonebookexample.app.contact;

import org.example.phonebookexample.app.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author Nicholas Drone on 6/28/17.
 */
public class ContactSpecification implements Specification<Contact>
{
    private static final String SQL_LIKE = "%";

    private SearchCriteria searchCriteria;

    ContactSpecification(SearchCriteria searchCriteria)
    {
        Assert.notNull(searchCriteria, "SearchCriteria must not be null");
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Contact> root, CriteriaQuery<?> criteriaQuery,
        CriteriaBuilder criteriaBuilder)
    {
        switch (searchCriteria.getOperation())
        {
            case LIKE:
                return criteriaBuilder.like(root.get(searchCriteria.getKey()), SQL_LIKE
                        + searchCriteria.getValue() + SQL_LIKE);
            case EQUALS:
                return criteriaBuilder.equal(root.get(searchCriteria.getKey()),
                        searchCriteria.getValue());

            default:
                return null;

        }
    }
}
