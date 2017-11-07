package org.se.lab.data;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	CommunityTest.class,
	PostTest.class,
    UserContactTest.class,
	UserProfileTest.class,
	EnumerationItemTest.class,
	EnumerationTest.class,
	CommunityDAOTest.class,
	PostDAOTest.class
})
public class DataTestSuite {
	
}
