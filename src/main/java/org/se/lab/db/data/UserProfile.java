package org.se.lab.db.data;

import org.se.lab.utils.ArgumentChecker;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

@Entity
@Table(name = "userprofile")
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "picture", columnDefinition = "mediumblob")
    private byte[] picture;
    @Column(name = "address")
    private String address;
    @Column(name = "plz")
    private String plz;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @Column(name = "room")
    private String room;
    @Column(name = "team")
    private String team;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "description")
    private String description;


    @OneToOne(mappedBy = "userprofile")
    private User user;


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

    public UserProfile() {
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        ArgumentChecker.assertNotNullAndEmpty(firstname, "firstname");
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        ArgumentChecker.assertNotNullAndEmpty(lastname, "lastname");
        this.lastname = lastname;
    }

    public byte[] getPicture() {
        if (picture == null) {
            return null;
        }
        return Arrays.copyOf(picture, picture.length);
    }

    public void setPicture(byte[] picture) {
        this.picture = Arrays.copyOf(picture, picture.length);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        ArgumentChecker.assertNotNullAndEmpty(address, "address");
        this.address = address;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user == null)
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
