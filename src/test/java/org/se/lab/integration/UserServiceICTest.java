package org.se.lab.integration;

import org.junit.Test;
import org.se.lab.db.data.User;

public class UserServiceICTest extends TemplateServiceICTest {

	
	@Test
	public void test() {
		User user = new User("Homer", "password");
		userService.insert(user);
	}
	
}
