package org.example.phonebookexample.app.contact;

import com.openpojo.random.RandomFactory;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicholas Drone on 6/12/17.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ContactController.class)
public class ContactControllerIT
{
    @Autowired
    private MockMvc           mvc;

    @MockBean
    private ContactRepository contactRepository;

    @Test
    public void fetchAllContacts() throws Exception
    {
        int size = 100;
        Mockito.when(contactRepository.findAll()).thenReturn(generatedContacts(size));

        mvc.perform(MockMvcRequestBuilders.get("/api/contacts").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", IsCollectionWithSize.hasSize(size)));
    }

    @Test
    public void fetchAllContactsNoneFound() throws Exception
    {
        Mockito.when(contactRepository.findAll()).thenReturn(new ArrayList<>());

        mvc.perform(MockMvcRequestBuilders.get("/api/contacts").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void fetchContact() throws Exception
    {
        Mockito.when(contactRepository.findOne(Mockito.eq(1L))).thenAnswer(invocation -> {
            Contact contact = new Contact();
            contact.setNumber((Long) invocation.getArguments()[0]);
            return contact;
        });

        mvc.perform(MockMvcRequestBuilders.get("/api/contact/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.number", Matchers.is(1)));
    }

    @Test
    public void fetchContactNotFound() throws Exception
    {
        Mockito.when(contactRepository.findOne(Mockito.anyLong())).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.get("/api/contact/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void saveContact() throws Exception
    {
        Mockito.when(contactRepository.save(Mockito.any(Contact.class))).thenAnswer(invocation -> {
            Contact contact = (Contact) invocation.getArguments()[0];
            contact.setNumber(RandomFactory.getRandomValue(Long.class));
            return contact;
        });

        mvc.perform(MockMvcRequestBuilders.post("/api/contact", new Contact())
            .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.number", Matchers.notNullValue()));
    }

    private List<Contact> generatedContacts(int capacity)
    {
        List<Contact> contacts = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++)
        {
            Contact contact = new Contact();
            contact.setNumber(RandomFactory.getRandomValue(Long.class));
            contacts.add(contact);
        }
        return contacts;
    }
}
