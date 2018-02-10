package org.se.lab.db.data;

import org.se.lab.utils.ArgumentChecker;

import javax.persistence.*;
import java.io.Serializable;

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


    public UserContact(User user, int contact) {
        setUser(user);
        setContactId(contact);
    }

    /**
     * Constructor for Hibernate
     */

    public UserContact() {
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        ArgumentChecker.assertNotNull(user, "user");

        this.user = user;
        user.addUserContacts(this);
    }

    public int getContactId() {
        return contact;
    }

    public void setContactId(int contact) {
        ArgumentChecker.assertValidNumber(contact,"contactId");
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
