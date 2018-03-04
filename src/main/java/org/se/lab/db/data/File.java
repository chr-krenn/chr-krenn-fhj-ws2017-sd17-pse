package org.se.lab.db.data;

import org.se.lab.utils.ArgumentChecker;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

@Entity
@Table(name = "file")
public class File implements Serializable {
    private static final long serialVersionUID = 1L;

    public File(User user,String filename,byte[] data){
        ArgumentChecker.assertNotNull(user,"user");
        ArgumentChecker.assertNotNullAndEmpty(filename,"filename");
        ArgumentChecker.assertNotNull(data,"data");
        setData(data);
        setUser(user);
        setFilename(filename);
    }
    
    public File (Community community,String filename,byte[] data){
        ArgumentChecker.assertNotNull(community,"community");
        ArgumentChecker.assertNotNullAndEmpty(filename,"filename");
        ArgumentChecker.assertNotNull(data,"data");
        setData(data);
        setCommunity(community);
        setFilename(filename);
    }
    
    public File() {}

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
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "community_id")
    private Community community;

    public byte[] getData() {
        return Arrays.copyOf(data, data.length);
    }

    public void setData(byte[] file) {
        this.data = Arrays.copyOf(file, file.length);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
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

    /**
     * Object Methods
     */

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
		result = prime * result + ((filename == null) ? 0 : filename.hashCode());
		result = prime * result + id;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		File other = (File) obj;
		if (!Arrays.equals(data, other.data))
			return false;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		if (id != other.id)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "File [id=" + id + ", data=" + Arrays.toString(data) + ", filename=" + filename + ", user=" + user + "]";
	}
}
