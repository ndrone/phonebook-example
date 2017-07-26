package org.example.phonebookexample.app.contact;

import org.example.phonebookexample.app.BadRequestException;
import org.example.phonebookexample.app.ResourceNotFoundException;
import org.example.phonebookexample.app.SearchOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nicholas Drone on 6/12/17.
 */
@RequestMapping("/api")
public class ContactController
{
    private static final int        KEY_INDEX       = 1;
    private static final int        OPERATION_INDEX = 2;
    private static final int        VALUE_INDEX     = 3;

    private static final String     SEPARATOR       = ",";
    private static final String     WORD            = "\\w+?";
    private static final String     DATE            = "\\d{2}/\\d{2}/\\d{4}";

    private final Logger            log             = LoggerFactory
        .getLogger(ContactController.class);

    private final Pattern           pattern         = Pattern.compile("("
        + WORD + ")" + SearchOperation.operationCaptureRegex() + "(" + WORD + "|" + DATE + ")"
        + SEPARATOR);

    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository)
    {
        Assert.notNull(contactRepository, "ContactRepository must not be null");
        this.contactRepository = contactRepository;
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    @ResponseBody
    public List<Contact> fetchAll()
    {
        log.info("Fetching all contacts.");
        List<Contact> contacts = contactRepository.findAll();
        log.debug("Returning {} contacts.", contacts.size());
        if (CollectionUtils.isEmpty(contacts))
        {
            throw new ResourceNotFoundException("No contacts found");
        }
        return contacts;
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET, params = "search")
    @ResponseBody
    public List<Contact> search(@RequestParam(value = "search") String search)
    {
        log.info("Search with params: {}", search);

        ContactSpecificationBuilder builder = new ContactSpecificationBuilder();
        Matcher matcher = pattern.matcher(search
            + SEPARATOR);
        while (matcher.find())
        {
            log.debug("Key: {} operation: '{}' value: {}", matcher.group(KEY_INDEX),
                matcher.group(OPERATION_INDEX), matcher.group(VALUE_INDEX));
            builder.with(matcher.group(KEY_INDEX),
                SearchOperation.fromString(matcher.group(OPERATION_INDEX)),
                matcher.group(VALUE_INDEX));
        }

        if (!builder.hasSearchParameters())
        {
            throw new BadRequestException("Incorrect search parameters");
        }

        return contactRepository.findAll(builder.build());
    }

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Contact fetch(@PathVariable("id") Long id)
    {
        log.info("Fetching contact");
        log.debug("Fetching contact with id: {}", id);
        Contact contact = contactRepository.findOne(id);
        log.debug("Found contact {}", contact);
        if (contact == null)
        {
            throw new ResourceNotFoundException("Contact not found");
        }
        return contact;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    @ResponseBody
    public Contact save(Contact contact)
    {
        log.info("Saving contact");
        log.debug("Saving contact: {}", contact);
        return contactRepository.save(contact);
    }

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") Long id) throws Exception
    {
        log.info("Deleting contact");
        log.debug("Deleting Contact: {}", id);
        contactRepository.delete(id);
    }
}
