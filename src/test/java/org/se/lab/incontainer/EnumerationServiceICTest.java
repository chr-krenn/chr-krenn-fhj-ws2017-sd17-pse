package org.se.lab.incontainer;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.se.lab.db.data.Enumeration;

public class EnumerationServiceICTest extends TemplateServiceICTest {

	
	//Enumeration findById(int id);
	@Test
	public void findById() {
		List<Enumeration> enums = enumDao.findAll();
		
		for (Enumeration e : enums) {
			assertEquals(e.getName(), enumerationService.findById(e.getId()).getName());
		}
		
	}

    //Enumeration getPending();
	@Test
	public void getPending() {
		Enumeration pending = enumerationService.getPending();
		assertEquals(
				enumerationService.findById(Enumeration.State.PENDING.getValue()).getName(),
				pending.getName());
	}

    //Enumeration getApproved();
	@Test
	public void getApproved() {
		Enumeration approved = enumerationService.getApproved();
		assertEquals(
				enumerationService.findById(Enumeration.State.APPROVED.getValue()).getName(), 
				approved.getName());
	}

    //Enumeration getRefused();
	@Test
	public void getRefused() {
		Enumeration refused = enumerationService.getRefused();
		assertEquals(
				enumerationService.findById(Enumeration.State.REFUSED.getValue()).getName(),
				refused.getName());
	}
	
}
