package org.example.phonebookexample.app.contact;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Nicholas Drone on 6/12/17.
 */
@Entity
public class Contact implements Serializable
{
    private static final long serialVersionUID = 6782082928144550811L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long              number;

    @Column(length = 6)
    private String            title;

    @Column(name = "givenname", length = 20)
    private String            firstname;

    @Column(length = 1)
    private String            middleinitial;

    @Column(name = "surname", length = 23)
    private String            lastname;

    @Column(length = 100)
    private String            streetaddress;

    @Column(length = 100)
    private String            city;

    @Column(length = 22)
    private String            state;

    @Column(length = 15)
    private String            zipcode;

    @Column(length = 100)
    private String            emailaddress;

    @Column(length = 25)
    private String            telephonenumber;

    @Column(length = 10)
    private String            birthday;

    @Column(length = 70)
    private String            occupation;

    @Column(length = 70)
    private String            company;

    public Long getNumber()
    {
        return number;
    }

    public void setNumber(final Long number)
    {
        this.number = number;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(final String title)
    {
        this.title = title;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(final String firstname)
    {
        this.firstname = firstname;
    }

    public String getMiddleinitial()
    {
        return middleinitial;
    }

    public void setMiddleinitial(final String middleinitial)
    {
        this.middleinitial = middleinitial;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(final String lastname)
    {
        this.lastname = lastname;
    }

    public String getStreetaddress()
    {
        return streetaddress;
    }

    public void setStreetaddress(final String streetaddress)
    {
        this.streetaddress = streetaddress;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(final String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(final String state)
    {
        this.state = state;
    }

    public String getZipcode()
    {
        return zipcode;
    }

    public void setZipcode(final String zipcode)
    {
        this.zipcode = zipcode;
    }

    public String getEmailaddress()
    {
        return emailaddress;
    }

    public void setEmailaddress(final String emailaddress)
    {
        this.emailaddress = emailaddress;
    }

    public String getTelephonenumber()
    {
        return telephonenumber;
    }

    public void setTelephonenumber(final String telephonenumber)
    {
        this.telephonenumber = telephonenumber;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(final String birthday)
    {
        this.birthday = birthday;
    }

    public String getOccupation()
    {
        return occupation;
    }

    public void setOccupation(final String occupation)
    {
        this.occupation = occupation;
    }

    public String getCompany()
    {
        return company;
    }

    public void setCompany(final String company)
    {
        this.company = company;
    }

    /**
     *
     * @return Formatted String representation of this Object.
     */
    @Override
    public String toString()
    {
        return String.format(
            "Customer[id=%d, title='%s', firstname='%s', middleinitial='%s', lastname='%s', streetaddress='%s', city='%s', state='%s', zipcode='%s', emailaddress='%s', telephonenumber='%s', birthday='%s', occupation='%s', company='%s']",
            number, title, firstname, middleinitial, lastname, streetaddress, city, state, zipcode,
            emailaddress, telephonenumber, birthday, occupation, company);
    }
}
