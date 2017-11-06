package org.se.lab.data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "userprofile")
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    public UserProfile(String firstname, String lastname, String email, String phone, String mobile, String description) {
        setFirstname(firstname);
        setLastname(lastname);
        setEmail(email);
        setPhone(phone);
        setMobile(mobile);
        setDescription(description);
    }

    protected UserProfile() {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;

    public int getId() {
        return id;
    }


    @Column(name = "firstname")
    private String firstname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        if(firstname == null)
            throw new IllegalArgumentException();
        this.firstname = firstname;
    }


    @Column(name = "lastname")
    private String lastname;

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        if(lastname == null)
            throw new IllegalArgumentException();
        this.lastname = lastname;
    }


    @Column(name = "email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email == null)
            throw new IllegalArgumentException();
        this.email = email;
    }


    @Column(name = "phone")
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if(phone == null)
            throw new IllegalArgumentException();
        this.phone = phone;
    }


    @Column(name = "mobile")
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        if(mobile == null)
            throw new IllegalArgumentException();
        this.mobile = mobile;
    }


    @Column(name = "description")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description == null)
            throw new IllegalArgumentException();
        this.description = description;
    }


    @OneToOne (mappedBy="userprofile")
    private User user;

    public User getUser() {return user;}

    public void setUser(User user) {
        if(user == null)
            throw new IllegalArgumentException();
        this.user = user;
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
        UserProfile other = (UserProfile) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserProfile [firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", phone=" + phone + ", mobile=" + mobile + ", description=" + description + "]";
    }

}
