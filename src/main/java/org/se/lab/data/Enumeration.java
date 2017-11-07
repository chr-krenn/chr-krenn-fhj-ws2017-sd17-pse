package org.se.lab.data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="enumeration")
public class Enumeration implements Serializable
{
	private static final long serialVersionUID = 1L;

	protected Enumeration()  
	{
	}
	
	public Enumeration(int id, String name)
	{
		setId(id);
		setName(name);
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


	@Column(name="NAME")
	private String name;
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		if(name == null)
			throw new IllegalArgumentException("Invalid parameter name!");
		
		this.name = name;
	}

	@Override
	public String toString()
	{
		return getId() + "," + getName() + "," + "***";
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
		
		Enumeration other = (Enumeration) obj;
		if (id != other.getId())
			return false;
		
		return true;
	}
}