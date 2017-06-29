package org.example.phonebookexample.app.contact;

import org.example.phonebookexample.PhonebookExampleApplicationTests;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author Nicholas Drone on 6/12/17.
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ContactControllerTests extends PhonebookExampleApplicationTests
{
    @Test
    public void fetchAllContacts() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.get("/api/contacts").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", IsCollectionWithSize.hasSize(50000)));
    }

    @Test
    public void fetchContact() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.get("/api/contact/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.number", Matchers.is(1)));
    }

    @Test
    public void saveContact() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/api/contact", new Contact())
            .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.number", Matchers.notNullValue()));
    }

    @Test
    public void deleteContact() throws Exception
    {
        mvc.perform(
            MockMvcRequestBuilders.delete("/api/contact/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/api/contact/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void equalsSearch() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.get("/api/contacts").param("search", "firstname=Tommy,")
            .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[*]firstname",
                Matchers.everyItem(Matchers.equalTo("Tommy"))));
    }

    @Test
    public void likeSearch() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.get("/api/contacts").param("search", "firstname:Cha,")
            .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[*]firstname",
                Matchers.everyItem(Matchers.containsString("Cha"))));
    }
}
