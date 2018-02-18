package org.se.lab.db.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.se.lab.db.data.Community;
import org.se.lab.db.data.Enumeration;
import org.se.lab.db.data.User;

import java.util.List;

import javax.persistence.PersistenceException;

public class CommunityDAOTest extends AbstractDAOTest {
	
	private User user1;
	private Community community1;
	private Community community2;
	
	private static UserDAOImpl udao = new UserDAOImpl();
	
	private static CommunityDAOImpl cdao = new CommunityDAOImpl();
	private static EnumerationDAOImpl edao = new EnumerationDAOImpl();
	
    static {
    	cdao.setEntityManager(em);
    	udao.setEntityManager(em);
    	edao.setEntityManager(em);
    }
	
    @Before
    public void setup() {
        tx.begin();

        user1 = udao.createUser("Donald Trump", "NurSauer");
        community1 = new Community("TestCommunity", "a description", user1.getId());
        
    }

    @Override
    @Test
    public void testCreate() {
    	cdao.insert(community1);
    	
    	Assert.assertEquals(community1, cdao.findByName(community1.getName()));
    }

    @Override
    @Test
    public void testModify() {
    	Community persistedCommunity = cdao.insert(community1);
    	
    	persistedCommunity.setDescription("other description");
    	
    	cdao.update(persistedCommunity);
    	Assert.assertEquals(persistedCommunity.getDescription(), 
    			cdao.findByName(community1.getName()).getDescription());
    }
    
    @Test
    public void testFindById(){
    	Community persistedCommunity = cdao.insert(community1);
    	
    	Assert.assertEquals(community1.getName(), 
    			cdao.findById(persistedCommunity.getId()).getName());
    }
    
    @Test
    public void testFindAll(){
    	Community persistedCommunity = cdao.insert(community1);
    	
    	List<Community> communities = cdao.findAll();
    	
    	Assert.assertEquals(true, communities.contains(persistedCommunity));
    }
    
    @Test
    public void testFindByState(){
    	Community persistedCommunity = cdao.createCommunity(community1.getName(), community1.getDescription(), user1.getId());
    	
    	Enumeration enumerationCommunity = persistedCommunity.getState();
    	Enumeration e = edao.createEnumeration(1);
    	
    	
    	
    	//TODO: change Enumeration class: make state public or modify comDao findComByState(Enumeration) 
    	//instead of findComByState(Enumeration.State)
    	    	
    	//List<Community> communities = cdao.findCommunitiesByState();
    	
    	//Assert.assertEquals(persistedCommunity, communities.contains(persistedCommunity));
    }

    @Override
    @Test(expected = PersistenceException.class)
    public void testRemove() {
    	Community persistedCommunity = cdao.insert(community1);
    	
    	persistedCommunity.setDescription("other description");
    	
    	cdao.delete(persistedCommunity);
    	
    	Assert.assertEquals(null, cdao.findByName(community1.getName()));
    }
    
    @After
    @Test
    public void tearDown(){
    	
    }

}
