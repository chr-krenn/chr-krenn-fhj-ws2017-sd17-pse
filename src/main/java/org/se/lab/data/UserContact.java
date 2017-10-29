package org.se.lab.data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "contact")
public class UserContact implements Serializable {

    private static final long serialVersionUID = 1L;

    public UserContact(int id, User user, int contact)
    {
        setId(id);
        setUser(user);
        setContactId(contact);
    }

    protected UserContact()
    {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;

    public void setId(int id) {
        if (id <= 0)
            throw new IllegalArgumentException();
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name="fk_user_id")
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
        return "UserContacts [id=" + id + ", userId=" + user.getId() + ", contactId=" + getContactId() + "]";
    }
}
