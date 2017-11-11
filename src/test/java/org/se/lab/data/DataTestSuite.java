package org.se.lab.data;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		CommunityTest.class,
		EnumerationTest.class,
		PostTest.class,
		PrivateMessageTest.class,
    	UserContactTest.class,
		UserProfileTest.class,
		UserTest.class
})
public class DataTestSuite {
	
}
