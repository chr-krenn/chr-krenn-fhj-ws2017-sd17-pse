package org.se.lab.data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class AbstractDAOTest {
	
	protected static EntityManagerFactory factory;
	protected static EntityManager em;
	
	@BeforeClass
	public static void connect() {
		factory = Persistence.createEntityManagerFactory("pse_test");
	}
	
	@AfterClass
	public static void disconnect() {
		factory.close();
		factory = null;
	}

}
