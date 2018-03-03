package org.se.lab.integration;

import org.jboss.arquillian.container.test.api.Deployment;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.se.lab.db.dao.PostDAO;
import org.se.lab.db.data.Community;
import org.se.lab.db.data.Post;
import org.se.lab.service.*;


import java.util.List;

import javax.inject.Inject;

@RunWith(Arquillian.class)
public class CommunityServiceICTest {

	@Inject Post c;
	//CommunityServiceImpl service;
	@Inject PostDAO dao;

	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "pse.jar")
				
				.addPackages(true, "org.se.lab.db")
				//.addPackages(true, "org.se.lab.service")
				//.addPackages(true, "org.se.lab.utils")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				//.addAsManifestResource("META-INF/arquillian.xml", "arquillian.xml")
				;		
		System.out.println(jar.toString(true));
		return jar;
	}

	@Test
	public void test() {
		System.out.println("Hello Test");
		System.out.println(c);
		System.out.println(dao);
		System.out.println();
	}

}
