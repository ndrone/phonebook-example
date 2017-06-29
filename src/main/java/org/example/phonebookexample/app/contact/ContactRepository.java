package org.example.phonebookexample.app.contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Nicholas Drone on 6/12/17.
 */
public interface ContactRepository
    extends JpaRepository<Contact, Long>, JpaSpecificationExecutor<Contact>
{
}
