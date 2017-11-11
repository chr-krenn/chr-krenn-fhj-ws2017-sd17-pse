package org.se.lab.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UserProfileDAOTest extends AbstractDAOTest {

    public UserProfile  up = new UserProfile("James", "Bond", "London" , "james.bond@gmail.com", "MI6", "test" , "test", "test userprofile");
    public UserProfile  up2 = new UserProfile("Heinz", "Bond", "London" , "james.bond@gmail.com", "MI6", "test" , "test", "test userprofile");


    public UserProfileDAOImpl updao = new UserProfileDAOImpl();

    @Before
    @Override
    public void setup() {
        tx.begin();
        updao.setEntityManager(em);
    }

    @Test
    @Override
    public void testCreate() {
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
}

