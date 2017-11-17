package org.se.lab.data;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 *  @author Christopher Wegl
 *
 *          UserContact junction object with user and contact
 *
 */

@Entity
@Table(name = "contact")
public class UserContact implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     * @param user
     *        user object
     * @param contact
     *        contact id which gets connected to user object
     */

    public UserContact(User user, int contact)
    {
        setUser(user);
        setContactId(contact);
    }

    /**
     * Constructor for Hibernate
     */

    protected UserContact()
    {
    }

    /**
     * id unique identifier for the usercontact. Auto genereted by DB.
     */

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    /**
     * user mapping of object of user
     */

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    public void setUser(User user) {
        if(user == null)
            throw new IllegalArgumentException();
        this.user = user;
        user.addUserContacts(this);
    }

    public User getUser(){
        return user;
    }

    /**
     * usercontact mapping with id of contact
     */

    @Column(name = "fk_contact_id")
    private int contact;

    public int getContactId() {
        return contact;
    }

    public void setContactId(int contact) {
        if (contact <= 0 )
            throw new IllegalArgumentException();
        this.contact = contact;
    }


    /**
     * Object Methods
     */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserContact other = (UserContact) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserContacts [userId=" + user.getId() + ", contactId=" + getContactId() + "]";
    }
}
