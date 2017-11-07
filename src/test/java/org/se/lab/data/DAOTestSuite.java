package org.se.lab.data;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	PostDAOTest.class,
	CommunityDAOTest.class
})
public class DAOTestSuite {
	
}
