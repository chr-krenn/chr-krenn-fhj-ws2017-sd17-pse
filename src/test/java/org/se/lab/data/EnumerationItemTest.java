package org.se.lab.data;

import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EnumerationItemTest {

	private EnumerationItem enumerationItem;
	private static CommunityDAOImpl cdao = new CommunityDAOImpl();
	
	@Before
	public void setUp() throws Exception{
		enumerationItem = new EnumerationItem(1);
	}

	@After
	public void tearDown() throws Exception{
		
	}
	
	@Test
	public void testConstructor(){
		Assert.assertEquals(1, enumerationItem.getId());
	}
	
	@Test
	public void testConstructorProtected(){
		EnumerationItem actual = new EnumerationItem();
		Assert.assertTrue(actual instanceof EnumerationItem );
	}	
	
	@Test
	public void testId(){
		enumerationItem.setId(2);
		Assert.assertEquals(2, enumerationItem.getId());
	}
	
	@Test
	public void testCommunityState(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("pse_test");
		assertNotNull(factory);
		EntityManager em = factory.createEntityManager();
		assertNotNull(em);
		EntityTransaction tx = em.getTransaction();
		assertNotNull(tx);
		cdao.setEntityManager(em);
		tx.begin();
		Community com = cdao.createCommunity("TestCom1", "Test Community for State Test");
		com.setState(new EnumerationItem(2));
		tx.commit();
		
		Assert.assertEquals(com.getState(), new EnumerationItem(2));
		
		tx.begin();
		cdao.delete(com);
		tx.commit();
		
	}
}