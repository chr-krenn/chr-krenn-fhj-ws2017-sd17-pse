package org.se.lab.data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Christopher Wegl
 *         <p>
 *         UserContact junction object with user and contact
 */

@Entity
@Table(name = "contact")
public class UserContact implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id unique identifier for the usercontact. Auto genereted by DB.
     */

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * user mapping of object of user
     */

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    /**
     * usercontact mapping with id of contact
     */

    @Column(name = "fk_contact_id")
    private int contact;

    /**
     * @param user    user object
     * @param contact contact id which gets connected to user object
     * @throws DatabaseException
     */

    public UserContact(User user, int contact) throws DatabaseException {
        setUser(user);
        setContactId(contact);
    }

    /**
     * Constructor for Hibernate
     */

    protected UserContact() {
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) throws DatabaseException {
        if (user == null)
            throw new DatabaseException("User must not be null");
        this.user = user;
        try {
            user.addUserContacts(this);
        } catch (DatabaseException e) {
            throw new DatabaseException("setUser() in UserContact: " + user, e);
        }
    }

    public int getContactId() {
        return contact;
    }

    public void setContactId(int contact) throws DatabaseException {
        if (contact <= 0)
            throw new DatabaseException("The contact id must not less or equal 0");
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
