package org.se.lab.integration;

import org.jboss.arquillian.container.test.api.Deployment;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.se.lab.db.dao.EnumerationDAO;
import org.se.lab.db.dao.UserContactDAO;
import org.se.lab.db.data.Community;
import org.se.lab.db.data.Enumeration;
import org.se.lab.db.data.File;
import org.se.lab.db.data.Post;
import org.se.lab.db.data.PrivateMessage;
import org.se.lab.db.data.User;
import org.se.lab.db.data.UserContact;
import org.se.lab.db.data.UserProfile;
import org.se.lab.service.*;

import java.util.Date;

import javax.inject.Inject;
import javax.transaction.UserTransaction;

@RunWith(Arquillian.class)
public abstract class TemplateServiceICTest {
	
	@Inject protected UserService userService;
	@Inject protected ActivityStreamService activityStreamService;
	@Inject protected CommunityService communityService;
	@Inject protected EnumerationService enumerationService;
	@Inject protected PostService postService;
	@Inject protected PrivateMessageService pmService;
	@Inject protected EnumerationDAO enumDao;
	@Inject protected UserContactDAO contactDao;
	
	protected boolean isset = false;
	
	@Inject protected UserTransaction tx;
	
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
		createEnums();
		
		tx.commit();
		
		tx.begin();
		
	}
	
	public void createEnums() {
		
		if(isset) {
			return;
		}else {
			enumDao.createEnumeration(1);
			enumDao.createEnumeration(2);
			enumDao.createEnumeration(3);
			enumDao.createEnumeration(4);
			enumDao.createEnumeration(5);
			enumDao.createEnumeration(6);
			enumDao.createEnumeration(7);
			enumDao.createEnumeration(8);
			isset = true;
		}
		

	}
	
	
	@After
	public void tearDown() throws Exception {
		
		tx.rollback();
	}
	

}
