package org.se.lab.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UserContactDAOTest extends AbstractDAOTest {

	private User u;
	private UserProfile up;
	private UserContact uc;
	private UserContact uc2;

	private UserDAOImpl udao = new UserDAOImpl();
	private UserContactDAOImpl ucdao = new UserContactDAOImpl();
	private UserProfileDAOImpl updao = new UserProfileDAOImpl();

	@Before
	@Override
	public void setup() {
		try {
			u = new User("James", "***");
			up = new UserProfile("James", "Bond", "Abbey 12", "72FE4", "London", "England", "43", "MI6", "james.bond@gmail.com", "test", "test", "test userprofile");
			uc = new UserContact(u, 2);
			uc2 = new UserContact(u, 3);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		tx.begin();
		udao.setEntityManager(em);
		updao.setEntityManager(em);
		ucdao.setEntityManager(em);

	}

	@Test
	@Override
	public void testCreate() {
		udao.insert(u);
		ucdao.insert(uc);
	}

	@Test
	@Override
	public void testModify() {
		udao.insert(u);
		UserContact persisted = ucdao.insert(uc);
		try {
			persisted.setContactId(3);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		ucdao.update(persisted);
		Assert.assertEquals(3, uc.getContactId());
	}

	@Test
	@Override
	public void testRemove() {
		udao.insert(u);
		ucdao.insert(uc);
		ucdao.delete(uc);
		UserContact uc3 = ucdao.findById(uc.getId());
		Assert.assertNull(uc3);
	}

	@Test
	public void testfindAll() {
		udao.insert(u);
		ucdao.insert(uc);
		ucdao.insert(uc2);
		List<UserContact> ucs = ucdao.findAll();
		Assert.assertEquals(2, ucs.size());
	}

	@Test
	public void testfindById() {
		udao.insert(u);
		ucdao.insert(uc);
		UserContact uc3 = ucdao.findById(uc.getId());
		Assert.assertEquals(uc, uc3);
	}

	@Test
	public void testdoesContactExistForUserIdtrue() {
		udao.insert(u);
		ucdao.insert(uc);
		Assert.assertTrue(ucdao.doesContactExistForUserId(2, u.getId()));
	}

	@Test
	public void testdoesContactExistForUserIdfalse() {
		udao.insert(u);
		ucdao.insert(uc);
		Assert.assertFalse(ucdao.doesContactExistForUserId(-1, u.getId()));
	}

	@Test
	public void testdeleteContactForUserIdAndContactId() {
		udao.insert(u);
		ucdao.insert(uc);
		Assert.assertTrue(ucdao.doesContactExistForUserId(2, u.getId()));
		ucdao.deleteContactForUserIdAndContactId(2, u.getId());
		Assert.assertFalse(ucdao.doesContactExistForUserId(2, u.getId()));
	}

	@Test
	public void findContactbyUser() {
		udao.insert(u);
		ucdao.insert(uc);
		ucdao.insert(uc2);

		List<UserContact> ucs = ucdao.findContactsbyUser(u);
		Assert.assertEquals(2, ucs.size());

		Assert.assertEquals(2, ucs.get(0).getContactId());
		Assert.assertEquals(3, ucs.get(1).getContactId());
	}

	@Test
	public void findContactbyUsernot() {
		udao.insert(u);
		ucdao.insert(uc);
		ucdao.insert(uc2);

		List<UserContact> ucs = ucdao.findContactsbyUser(u);
		Assert.assertNotEquals(1, ucs.size());

	}

	@Test
	public void testUserbyContact() {
		udao.insert(u);
		ucdao.insert(uc);

		List<UserContact> ucs = ucdao.findAll();
		Assert.assertEquals(1, ucs.size());
		Assert.assertEquals(uc.getId(), ucs.get(0).getId());

		Assert.assertEquals(u.getId(), ucs.get(0).getUser().getId());
		Assert.assertEquals("James", ucs.get(0).getUser().getUsername());
		Assert.assertEquals("***", ucs.get(0).getUser().getPassword());

		Assert.assertEquals(2, ucs.get(0).getContactId());
	}

	@Test
	public void testUserProfilebyContact() {
		udao.insert(u);
		ucdao.insert(uc);
		updao.insert(up);
		try {
			u.setUserProfile(up);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}

		List<UserContact> ucs = ucdao.findAll();
		Assert.assertEquals(1, ucs.size());
		Assert.assertEquals(uc.getId(), ucs.get(0).getId());

		Assert.assertEquals(up.getId(), ucs.get(0).getUser().getUserProfile().getId());
		Assert.assertEquals("James", ucs.get(0).getUser().getUserProfile().getFirstname());
		Assert.assertEquals("Bond", ucs.get(0).getUser().getUserProfile().getLastname());
		Assert.assertEquals("james.bond@gmail.com", ucs.get(0).getUser().getUserProfile().getEmail());

		Assert.assertEquals(2, ucs.get(0).getContactId());

	}

}
