package org.se.lab.data;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class UserProfileDAOTest extends AbstractDAOTest {

    public UserProfile up = new UserProfile("Fitz", "Phantom", "fritz.phantom@gmail.com", "555-432", "555-321", "Person");
    public UserProfile up2 = new UserProfile("Test", "Phantom", "fritz.phantom@gmail.com", "555-432", "555-321", "Phantom");


    public static UserProfileDAOImpl updao = new UserProfileDAOImpl();

    static {
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

