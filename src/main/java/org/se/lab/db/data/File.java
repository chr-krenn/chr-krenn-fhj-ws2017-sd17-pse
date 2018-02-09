package org.se.lab.db.data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "file")
public class File implements Serializable {

    protected File(){}

    public File(User user,String filename,byte[] data){
        setData(data);
        setUser(user);
        setFilename(filename);
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "data", columnDefinition="mediumblob")
    private byte[] data;

    @Column(name="filename", nullable = false, unique = true)
    private String filename;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] file) {
        this.data = file;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
