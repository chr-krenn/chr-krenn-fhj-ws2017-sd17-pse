package org.se.lab.data;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        CommunityDAOTest.class,
        EnumerationDAOTest.class,
        PostDAOTest.class,
        PrivateMessageDAOTest.class,
        UserContactDAOTest.class,
        UserDAOTest.class,
        UserProfileDAOTest.class,
})
public class DAOTestSuite {
	
}
