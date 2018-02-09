package org.se.lab.db.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.se.lab.db.data.DatabaseException;
import org.se.lab.db.data.User;
import org.se.lab.db.data.UserProfile;

import java.util.List;

public class UserProfileDAOTest extends AbstractDAOTest {

    public User u;
    public UserProfile up;
    public UserProfile  up2;

    public UserDAOImpl udao = new UserDAOImpl();
    public UserProfileDAOImpl updao = new UserProfileDAOImpl();

    @Before
    @Override
    public void setup() {
        tx.begin();
        udao.setEntityManager(em);
        updao.setEntityManager(em);
        try {
			u = new User("James", "***");
			up = new UserProfile("James", "Bond", "Abbey 12", "72FE4", "London", "England", "43",  "MI6", "james.bond@gmail.com", "test" , "test", "test userprofile");
	        up2 = new UserProfile("Heinz", "Bond","Neuholdgasse 123" ,"1130" , "Vienan", "Austria", "123", "MI6","james.bond@gmail.com" , "test" , "test", "test userprofile");
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
        

    }

    @Test
    @Override
    public void testCreate() {
        udao.insert(u);
        updao.insert(up);
    }

    @Test
    @Override
    public void testModify() {
        UserProfile persisted = updao.insert(up);
        persisted.setFirstname("Test");
        updao.update(persisted);
        Assert.assertEquals("Test", up.getFirstname());
    }

    @Test
    @Override
    public void testRemove() {
        updao.insert(up);
        updao.delete(up);
        UserProfile up3 = updao.findById(up.getId());
        Assert.assertNull(up3);
    }

    @Test
    public void testfindAll() {
        updao.insert(up);
        updao.insert(up2);
        List<UserProfile> ups = updao.findAll();
        Assert.assertEquals(2, ups.size());
    }

    @Test
    public void testfindById() {
        updao.insert(up);
        UserProfile up3 = updao.findById(up.getId());
        Assert.assertEquals(up, up3);
    }

    @Test
    public void testUserbyUserProfile() {
        udao.insert(u);
        updao.insert(up);
        try {
			u.setUserProfile(up);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}

        List<UserProfile> ups = updao.findAll();
        Assert.assertEquals(1, ups.size());
        Assert.assertEquals(up.getId(), ups.get(0).getId());

        Assert.assertEquals(u.getId(), ups.get(0).getUser().getId());
        Assert.assertEquals("James", ups.get(0).getUser().getUsername());
        Assert.assertEquals("***", ups.get(0).getUser().getPassword());
    }

}

