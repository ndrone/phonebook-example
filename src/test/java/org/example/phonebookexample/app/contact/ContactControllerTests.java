package org.example.phonebookexample.app.contact;

import com.jayway.jsonpath.JsonPath;
import org.example.phonebookexample.PhonebookExampleApplicationTests;
import org.example.phonebookexample.app.ApplicationUtils;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Nicholas Drone on 6/12/17.
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ContactControllerTests extends PhonebookExampleApplicationTests
{

    private static final String API_CONTACT  = "/api/contact";
    private static final String API_CONTACTS = "/api/contacts";
    private static final String SEARCH       = "search";

    @Test
    public void fetchAllContacts() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.get(API_CONTACTS).accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", IsCollectionWithSize.hasSize(50000)));
    }

    @Test
    public void fetchContact() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.get(API_CONTACT
            + "/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.number", Matchers.is(1)));
    }

    @Test
    public void saveContact() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post(API_CONTACT, new Contact())
            .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.number", Matchers.notNullValue()));
    }

    @Test
    public void deleteContact() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.delete(API_CONTACT
            + "/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());

        mvc.perform(MockMvcRequestBuilders.get(API_CONTACT
            + "/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void equalsSearch() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.get(API_CONTACTS).param(SEARCH, "firstname=Tommy,")
            .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[*]firstname",
                Matchers.everyItem(Matchers.equalTo("Tommy"))));
    }

    @Test
    public void likeSearch() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.get(API_CONTACTS).param(SEARCH, "firstname:Cha,")
            .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[*]firstname",
                Matchers.everyItem(Matchers.containsString("Cha"))));
    }

    @Test
    public void birthdayLessThanSearch() throws Exception
    {
        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.get(API_CONTACTS).param(SEARCH, "birthday<07/23/2017,")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        List<String> birthdays = JsonPath.parse(mvcResult.getResponse().getContentAsString())
            .read("$[*]birthday");

        birthdays.forEach(birthday -> Assert.assertThat("Birthday should be less than 07/23/2017",
            LocalDate.parse(birthday, ApplicationUtils.FORMATTER),
            Matchers.lessThan(LocalDate.of(2017, 7, 23))));
    }

    @Test
    public void birthdayGreaterThanSearch() throws Exception
    {
        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.get(API_CONTACTS).param(SEARCH, "birthday>01/01/1970,")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        List<String> birthdays = JsonPath.parse(mvcResult.getResponse().getContentAsString())
            .read("$[*]birthday");

        birthdays
            .forEach(birthday -> Assert.assertThat("Birthday should be greater than 01/01/1970",
                LocalDate.parse(birthday, ApplicationUtils.FORMATTER),
                Matchers.greaterThan(LocalDate.ofEpochDay(0))));
    }

    @Test
    public void searchBadRequest() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.get(API_CONTACTS).param(SEARCH, "")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
