package org.se.lab.data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.assertNotNull;

public abstract class AbstractDAOTest {
	
	protected static final String persistencUnitName = "pse_test";
	protected static EntityManagerFactory factory;
	protected static EntityManager em;
	protected static EntityTransaction tx;
	
	
	@BeforeClass
	public static void connect() {
		factory = Persistence.createEntityManagerFactory("pse_test");
		assertNotNull(factory);
		em = factory.createEntityManager();
		assertNotNull(em);
		tx = em.getTransaction();
		assertNotNull(tx);
	}
	
	@AfterClass
	public static void disconnect() {
		if(em == null) return;
		em.close();
		factory.close();
	}

	@Before
	public void setup() {
		
	}
	
	@After
	public void teardown() {
		
	}
	
	abstract void testCreate();
	abstract void testModify();
	abstract void testRemove();
	
}
