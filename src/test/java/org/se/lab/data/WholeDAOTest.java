package org.se.lab.data;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;


public class WholeDAOTest extends AbstractDAOTest{

	@Override
	public void testCreate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testModify() {
		CommunityDAOImpl cdao = new CommunityDAOImpl();
		UserDAOImpl udao = new UserDAOImpl();
		cdao.setEntityManager(em);
		udao.setEntityManager(em);
		
		List<Community> coms = cdao.findAll();
		Assert.assertTrue(coms.size() > 0);
		for(Community c : coms) {
			System.out.println(c.toString());
			for(User u : c.getUsers()) {
				System.out.println("\t" + u.toString());
				System.out.println("\t" + u.getUserProfile());
			}
		}
	}

	@Override
	public void testRemove() {
		// TODO Auto-generated method stub
		
	}

}
