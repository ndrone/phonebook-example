package org.example.phonebookexample.app.contact;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nicholas Drone on 6/12/17.
 */
@Configuration
public class ContactConfiguration
{
    @Bean
    public ContactController contactController(ContactRepository contactRepository)
    {
        return new ContactController(contactRepository);
    }
}
