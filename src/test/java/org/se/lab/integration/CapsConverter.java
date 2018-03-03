package org.se.lab.integration;

import javax.ejb.Stateless;

@Stateless
public class CapsConverter {
	public ConvertToLowerCase getLowerCase() {
		return new ConvertToLowerCase();
	}
}
