package org.se.lab.data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "userprofile")
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    public UserProfile(String firstname, String lastname, String address, String plz, String city, String country, String room, String team, String email, String phone, String mobile, String description) {
        setFirstname(firstname);
        setLastname(lastname);
        setAddress(address);
        setPlz(plz);
        setCity(city);
        setCountry(country);
        setRoom(room);
        setTeam(team);
        setEmail(email);
        setPhone(phone);
        setMobile(mobile);
        setDescription(description);
    }

    protected UserProfile() {
    }


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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


    @Column(name = "picture")
    private byte[] picture;

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }


    @Column(name = "address")
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if(address == null)
            throw new IllegalArgumentException();
        this.address = address;
    }


    @Column(name = "plz")
    private String plz;

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }


    @Column(name = "city")
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    @Column(name = "country")
    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    @Column(name = "room")
    private String room;

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }


    @Column(name = "team")
    private String team;

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }


    @Column(name = "email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Column(name = "phone")
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Column(name = "mobile")
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    @Column(name = "description")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
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
        return "UserProfile [firstname=" + firstname + ", lastname=" + lastname + ", address=" + address + ", plz=" + plz + ", city=" + city + ", country=" + country + ", room=" + room + ", team=" + team + ", email=" + email + ", phone=" + phone + ", mobile=" + mobile + ", description=" + description + "]";
    }
}
