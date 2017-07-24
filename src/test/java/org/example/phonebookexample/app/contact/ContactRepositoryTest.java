package org.example.phonebookexample.app.contact;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

/**
 * @author Nicholas Drone on 7/23/17.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ContactRepositoryTest
{
    @Autowired
    private ContactRepository contactRepository;

    @Test
    public void save()
    {
        Contact contact = new Contact();
        contact.setFirstname("FirstName");
        contact.setLastname("LastName");
        contact.setBirthday(LocalDate.now());

        contact = contactRepository.save(contact);
        Assert.assertThat("Contact is saved with id", contact.getNumber(), Matchers.notNullValue());
    }
}
