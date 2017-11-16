package org.se.lab.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="enumeration_item")
public class EnumerationItem implements Serializable
{
	private static final long serialVersionUID = 1L;

	protected EnumerationItem()  
	{
	}
	
	public EnumerationItem(int id)
	{
		setId(id);
	}

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	public int getId()
	{
		return this.id;
	}
	
	public void setId(int id)
	{
		if(id < 0)
			throw new IllegalArgumentException("Invalid parameter id: " + id);
		
		this.id = id;
	}
	
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    public void setUser(User user) {
        if(user == null)
            throw new IllegalArgumentException();    
        this.user = user;
    }
    public User getUser(){
        return user;
    }	
	
    @ManyToOne
    @JoinColumn(name="enumeration_id")
    private Enumeration enumeration;
    public void setEnumeration(Enumeration enumeration) {
        if(enumeration == null)
            throw new IllegalArgumentException();        
        this.enumeration = enumeration;
    }
    public Enumeration getEnumeration(){
        return enumeration;
    }	
    
    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;
    public void setPost(Post post) {
        if(post == null)
            throw new IllegalArgumentException();        
        this.post = post;
    }
    public Post getPost(){
        return post;
    }    
    
    @OneToMany(
			targetEntity = Community.class,
			fetch=FetchType.EAGER)
    @JoinColumn(name="community_id")
    private List<Community> community = new ArrayList<Community>();
    public void addCommunity(Community community) {
        if(community == null)
            throw new IllegalArgumentException();        
        this.community.add(community);
    }
    public List<Community> getCommunity(){
        return community;
    }
	


	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		EnumerationItem other = (EnumerationItem) obj;
		if (id != other.getId())
			return false;
		
		return true;
	}
}