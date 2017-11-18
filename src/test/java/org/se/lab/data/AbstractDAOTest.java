package org.se.lab.data;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.assertNotNull;

public abstract class AbstractDAOTest {
	
	protected static final String persistencUnitName = "pse";
	protected static EntityManagerFactory factory;
	protected static EntityManager em;
	protected static EntityTransaction tx;
	protected static EnumerationDAOImpl edao = new EnumerationDAOImpl();
	
	
	@BeforeClass
	public static void connect() {
		factory = Persistence.createEntityManagerFactory(persistencUnitName);
		assertNotNull(factory);
		em = factory.createEntityManager();
		assertNotNull(em);
		tx = em.getTransaction();
		assertNotNull(tx);
		
		// Everyone needs Enumeration anyways
		edao.setEntityManager(em);
		tx.begin();
		for (int i = 1; i <= 7; i++) { 
			if (edao.findById(i) == null)
				edao.createEnumeration(i);
		}
		tx.commit();
	
	}
	
	@AfterClass
	public static void disconnect() {
		/*//Destroy Enums -- Messes with Tests, rather truncate before deploy
		tx.begin();
		List<Enumeration> enums = edao.findAll();
		for (int i = enums.size() - 1; i >= 0; i--) { 
			edao.delete(enums.get(i));
		}
		tx.commit();
		*/
		
		if(em == null) return;
		em.close();
		factory.close();
	}

	@Before
	public void setup() {
		tx.begin();
	}
	
	@After
	public void teardown() {
		if (tx.isActive() ) {
			tx.rollback();
		}
	}
	
	abstract public void testCreate();
	abstract public void testModify();
	abstract public void testRemove();
	
}