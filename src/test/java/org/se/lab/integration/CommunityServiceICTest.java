package org.se.lab.integration;

import org.jboss.arquillian.container.test.api.Deployment;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.se.lab.db.data.Community;
import org.se.lab.service.*;


import java.util.List;

import javax.inject.Inject;

@RunWith(Arquillian.class)
public class CommunityServiceICTest {

	@Inject
	CommunityServiceImpl service;

	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
				.addPackages(true, "org.se.lab.db")
				.addPackages(true, "org.se.lab.service")
				.addPackages(true, "org.se.lab.utils")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsManifestResource("META-INF/arquillian.xml", "arquillian.xml");
							
		System.out.println(jar.toString(true));
		return jar;
	}

	@Test
	public void test() {
		List<Community> coms = service.getApproved();
		Assert.assertNotNull(coms);
	}

}
