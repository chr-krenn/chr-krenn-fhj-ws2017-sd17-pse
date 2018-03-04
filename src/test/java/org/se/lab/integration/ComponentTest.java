package org.se.lab.integration;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ComponentTest {

	private final static String persistence = "<persistence xmlns=\"http://java.sun.com/xml/ns/persistence\" version=\"1.0\">\n" + 
			"<persistence-unit name=\"pse\" transaction-type=\"JTA\">\n" + 
			"    <class>org.se.lab.integration.Car</class>\n" + 
			"    <properties>\n" + 
			"        <property name=\"javax.persistence.jdbc.driver\" value=\"org.hsqldb.jdbcDriver\"/>\n" + 
			"        <property name=\"javax.persistence.jdbc.url\" value=\"jdbc:hsqldb:mem:testdb\"/>\n" + 
			"        <property name=\"javax.persistence.jdbc.user\" value=\"sa\"/>\n" + 
			"        <property name=\"javax.persistence.jdbc.password\" value=\"\"/>\n" + 
			"        <property name=\"eclipselink.ddl-generation\" value=\"create-tables\"/>\n" + 
			"        <property name=\"hibernate.hbm2ddl.auto\" value=\"update\" />\n" + 
			"    </properties>\n" + 
			"</persistence-unit>\n" + 
			"</persistence>\n";
	
	@Deployment
	public static JavaArchive createDeployment() {
	    return ShrinkWrap.create(JavaArchive.class)
	      .addClasses(CarEJB.class, CapsService.class, CapsConverter.class, ConvertToLowerCase.class)
	      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
	      .addAsManifestResource(new StringAsset(persistence), "persistence.xml");
	}
	
	@Inject
	private CapsService capsService;
	
	@Inject
	private CarEJB carEJB;
	     
	@Test
	public void givenWord_WhenUppercase_ThenLowercase(){
	    assertTrue("capitalize".equals(capsService.getConvertedCaps("CAPITALIZE")));
	    assertEquals("capitalize", capsService.getConvertedCaps("CAPITALIZE"));
	    
	}
	
	@Test
	public void testCars() {
	    assertTrue(carEJB.findAllCars().isEmpty());
	    Car c1 = new Car();
	    c1.setName("Impala");
	    Car c2 = new Car();
	    c2.setName("Lincoln");
	    carEJB.saveCar(c1);
	    carEJB.saveCar(c2);
	  
	    assertEquals(2, carEJB.findAllCars().size());
	  
	    carEJB.deleteCar(c1);
	    System.out.println(carEJB.findAllCars().get(0).getName());
	    assertEquals(1, carEJB.findAllCars().size());
	}
	
	
	
	
}
