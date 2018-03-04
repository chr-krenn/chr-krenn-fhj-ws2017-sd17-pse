package org.se.lab.integration;

import org.jboss.arquillian.container.test.api.Deployment;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.se.lab.db.dao.UserDAO;
import org.se.lab.db.data.Post;
import org.se.lab.db.data.User;
import org.se.lab.service.*;

import javax.inject.Inject;
import javax.persistence.EntityTransaction;
import javax.transaction.UserTransaction;

@RunWith(Arquillian.class)
public class CommunityServiceICTest {

	@Inject private Post c;
	//CommunityServiceImpl service;
	@Inject private UserDAO dao;
	
	@Inject
	UserTransaction tx;
	
	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "pse.jar")
				
				.addPackages(true, "org.se.lab.db")
				.addPackages(true, "org.se.lab.service")
				.addPackages(true, "org.se.lab.utils")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsManifestResource("META-INF/arquillian_db.xml", "persistence.xml")
				//.addAsManifestResource("META-INF/arquillian.xml", "arquillian.xml")
				;		
		System.out.println(jar.toString(true));
		return jar;
	}

	@Before
	public void setup() throws Exception {
		tx.begin();
	}
	
	@After
	public void tearDown() throws Exception {
		tx.rollback();
	}
	
	@Test
	public void test_workingArquillian() throws Exception {
		
		System.out.println("\n\tHello Test");
		System.out.println(c);
		
		
	}
	
	@Test
	public void test_persistingDAO_workingArquillian() {
		System.out.println("\n\tPersisting User via dao");
		User user = new User("simpson", "password");
		user.setUsername("Homer");
		
		System.out.println(user);
		try {
			dao.insert(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(dao.findAll());
	}
	
	@Test
	public void test_persistDAO_workingArquillian() {
		System.out.println("\n\tPersisting User via Service");
	}

}
