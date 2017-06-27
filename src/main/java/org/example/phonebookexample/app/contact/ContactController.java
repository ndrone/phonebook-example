package org.example.phonebookexample.app.contact;

import org.example.phonebookexample.app.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Nicholas Drone on 6/12/17.
 */
@Controller
@RequestMapping("/api")
public class ContactController
{
    private final Logger log = LoggerFactory.getLogger(ContactController.class);

    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository)
    {
        Assert.notNull(contactRepository, "ContactRepository must not be null");
        this.contactRepository = contactRepository;
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    @ResponseBody
    public List<Contact> fetchAllContacts()
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

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Contact fetchContact(@PathVariable("id") Long id)
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
    public Contact saveContact(Contact contact)
    {
        log.info("Saving contact");
        log.debug("Saving contact: {}", contact);
        return contactRepository.save(contact);
    }

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteContact(@PathVariable("id") Long id) throws Exception
    {
        log.info("Deleting contact");
        log.debug("Deleting Contact: {}", id);
        contactRepository.delete(id);
    }
}
