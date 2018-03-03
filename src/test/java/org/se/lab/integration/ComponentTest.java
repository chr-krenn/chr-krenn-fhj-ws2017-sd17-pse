package org.se.lab.integration;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ComponentTest {

	@Deployment
	public static JavaArchive createDeployment() {
	    return ShrinkWrap.create(JavaArchive.class)
	      .addClasses(CapsService.class, CapsConverter.class, ConvertToLowerCase.class)
	      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Inject
	private CapsService capsService;
	     
	@Test
	public void givenWord_WhenUppercase_ThenLowercase(){
	    assertTrue("capitalize".equals(capsService.getConvertedCaps("CAPITALIZE")));
	    assertEquals("capitalize", capsService.getConvertedCaps("CAPITALIZE"));
	}
	
	
	
	
	
	
}
