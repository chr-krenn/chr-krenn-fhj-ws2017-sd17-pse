package org.se.lab.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "enumeration_item")
public class EnumerationItem implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * constructor for Hibernate
	 */
	protected EnumerationItem() {}
	
	/**
	 * unique identifier for the enumeration. Auto-generated/incremented by database
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
}