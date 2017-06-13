package org.example.phonebookexample.app.contact;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Nicholas Drone on 6/12/17.
 */
@Controller //needed so that @WebMvcTest will pick up this controller
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
        log.info("Fetching all contacts");
        return contactRepository.findAll();
    }

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Contact fetchContact(@PathVariable("id") String id)
    {
        log.info("Fetching contact");
        log.debug("Fetching contact with id: {}", id);
        return contactRepository.findOne(Long.valueOf(id));
    }

    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    @ResponseBody
    public Contact saveContact(Contact contact)
    {
        log.info("Saving contact");
        log.debug("Saving contact: {}", contact);
        return contactRepository.save(contact);
    }
}
