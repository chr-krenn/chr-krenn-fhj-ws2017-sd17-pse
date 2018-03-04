package org.se.lab.integration;

import org.jboss.arquillian.container.test.api.Deployment;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.se.lab.db.dao.UserDAO;
import org.se.lab.db.data.Community;
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
public class TemplateServiceICTest {
	
	@Inject protected UserService userService;
	@Inject protected ActivityStreamService activityStreamService;
	@Inject protected CommunityService communityService;
	@Inject protected EnumerationService enumerationService;
	@Inject protected PostService postService;
	@Inject protected PrivateMessageService pmService;
	
	protected User user1;
	protected User user2;
	protected Community community1;
	protected File file1;
	protected Post post1;
	protected PrivateMessage pm1;
	protected UserContact contact1;
	protected UserProfile profile1;
	
	
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
		user1 = new User("Homer", "password");
		user2 = new User("Lisa", "*****");
		community1 = new Community("Container", "Glassfish", 1, false);
		post1 = new Post(null, community1, user1, "This took way longer than i thought", new Date());
		file1 = new File();
		file1.setData(new byte[4]);
		profile1 = new UserProfile("Homer", "Simpson", "742 Evergreen Terrace", "?????", "Springfield", "USA", "????", "Atoms", "chunkylover53@aol.com", "555-7334", "555-7334", "Nucular Expert");
		contact1 = new UserContact(user1, 2);
		
		
		tx.begin();
	}
	
	@After
	public void tearDown() throws Exception {
		tx.rollback();
	}
	

}
