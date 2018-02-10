package org.se.lab.db.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.se.lab.db.data.Enumeration;

public class EnumerationTest {

    private Enumeration enumeration;

    @Before
    public void setUp() throws Exception {
        enumeration = new Enumeration(1);
        enumeration.setName("Aktiv");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConstructor() {
        Assert.assertEquals(1, enumeration.getId());
        Assert.assertEquals("Aktiv", enumeration.getName());
    }

    @Test
    public void testConstructorProtected() {
        Enumeration actual = new Enumeration();
        Assert.assertTrue(actual instanceof Enumeration);
    }

    @Test
    public void testName() {
        enumeration.setName("Test");
        Assert.assertEquals("Test", enumeration.getName());
    }

    @Test
    public void testId() {
        enumeration.setId(2);
        Assert.assertEquals(2, enumeration.getId());
    }

    @Test
    public void testEquals() {
        Assert.assertTrue(enumeration.equals(enumeration));
        Assert.assertTrue(!enumeration.equals(null));
        Assert.assertTrue(!enumeration.equals(new Object()));
        Enumeration actual = new Enumeration(1);
        actual.setName("Activer");
        Assert.assertTrue(enumeration.equals(actual));
        actual = new Enumeration(2);
        actual.setName("Active");
        Assert.assertTrue(!enumeration.equals(actual));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidId() {
        enumeration.setId(-1);
    }
}