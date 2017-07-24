package org.example.phonebookexample.app.contact;

import org.example.phonebookexample.app.ApplicationUtils;
import org.example.phonebookexample.app.SearchCriteria;
import org.example.phonebookexample.app.SearchOperation;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Nicholas Drone on 6/28/17.
 */
class ContactSpecificationBuilder
{
    private final List<SearchCriteria> params;

    ContactSpecificationBuilder()
    {
        this.params = new ArrayList<>();
    }

    ContactSpecificationBuilder with(String key, SearchOperation operation, Object value)
    {
        if ("birthday".equals(key))
        {
            value = LocalDate.parse((CharSequence) value, ApplicationUtils.FORMATTER);
        }
        this.params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    Specification<Contact> build()
    {
        // TODO: move this to controller to throw a bad request message
        Assert.notEmpty(params, "Search Parameters must not be empty");

        Iterator<SearchCriteria> iterator = params.iterator();
        Specifications<Contact> spec = Specifications
            .where(new ContactSpecification(iterator.next()));
        while (iterator.hasNext())
        {
            spec = spec.and(new ContactSpecification(iterator.next()));
        }
        return spec;
    }
}
