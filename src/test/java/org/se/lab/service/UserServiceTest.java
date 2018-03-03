package org.se.lab.service;

import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.se.lab.db.dao.CommunityDAO;
import org.se.lab.db.dao.EnumerationDAO;
import org.se.lab.db.dao.UserContactDAO;
import org.se.lab.db.dao.UserDAO;
import org.se.lab.db.dao.UserProfileDAO;
import org.se.lab.db.data.Community;
import org.se.lab.db.data.Enumeration;
import org.se.lab.db.data.User;
import org.se.lab.db.data.UserContact;
import org.se.lab.db.data.UserProfile;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(EasyMockRunner.class)
public class UserServiceTest {

    public static final int ID = 1;
    public static final String USERNAME = "username";
    public static final String PASSWORD = "$2a$10$Wa/BTCLKT80QzamNUt4O9uMcetbU97DFI3yw7.9UpA.Ld8VcegNMO";

    @TestSubject
    private UserService userService = new UserServiceImpl();

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private UserDAO userDAO;
    @Mock
    private UserContactDAO userContactDAO;
    @Mock
    private UserProfileDAO userProfileDAO;
    
    @Mock
    private CommunityDAO communityDAO;
    
    @Mock
    private EnumerationDAO enumDAO;

    private User user1;
    private User user2;
    private UserProfile userProfile1;
    private UserProfile userProfile2;
    private UserContact userContact2;


    @Before
    public void setUp() throws Exception {
        user1 = new User(USERNAME, PASSWORD);
        user2 = new User("username2", "pwd");
        user1.setId(1);
        user2.setId(2);

        userProfile1 = new UserProfile("James", "Bond", "Abbey 12", "72FE4", "London", "England", "43", "MI6", "james.bond@gmail.com", "test", "test", "test userprofile");
        userProfile2 = new UserProfile("Erika", "Musterfrau", "Neuholdgasse 3", "1130", "Vienna", "Austria", "103", "Alpha", "erika.musterfrau@edu.fh-joanneum.at", "03165678", "066489101112", "test2");
        user1.setUserProfile(userProfile1);
        user2.setUserProfile(userProfile2);

        new UserContact(user1, 2);
        userContact2 = new UserContact(user2, 1);
    }

    @After
    public void tearDown() throws Exception {
    }

    
    /*
     * Happy path
     */
    
    @Test
    public void insert_Successful() {
        expect(userDAO.insert(user1)).andReturn(user1);
        replay(userDAO);
        
        userService.insert(user1);
        verify(userDAO);
    }

    @Test
    public void deleteByUser_Successful() {
        userDAO.delete(user1);
        expectLastCall();
        replay(userDAO);

        userService.delete(user1);
        verify(userDAO);
    }

    @Test
    public void login_Successful() {
        expect(userDAO.findByUsername(USERNAME)).andReturn(user1);
        replay(userDAO);

        User user = userService.login(user1.getUsername(), "password");
        Assert.assertTrue(BCrypt.checkpw("password", user.getPassword()));
        Assert.assertThat(user, equalTo(user1));
        verify(userDAO);
    }

    @Test
    public void login_Fail() {
        expect(userDAO.findByUsername(USERNAME)).andReturn(user1);
        replay(userDAO);

        User user = userService.login(USERNAME, "wrongPassword");
        Assert.assertThat(user, nullValue());
        verify(userDAO);
    }

    @Test
    public void getAllContactsByUser() {
        List<UserContact> userContactList = new ArrayList<>();
        UserContact contact1 = null;
        UserContact contact2 = null;

        contact1 = new UserContact(user1, 2);
        UserContact contact3 = new UserContact(user1, 3);
        contact2 = new UserContact(user2, 1);


        userContactList.add(contact1);
        userContactList.add(contact2);
        userContactList.add(contact3);

        expect(userContactDAO.findContactsbyUser(user1)).andReturn(userContactList);
        expect(userDAO.findById(eq(1))).andStubReturn(user1);
        expect(userDAO.findById(eq(2))).andStubReturn(user2);
        expect(userDAO.findById(eq(3))).andStubReturn(null);
        replay(userContactDAO, userDAO);
        List<User> allContacts = userService.getContactsOfUser(user1);

        Assert.assertEquals(userContactList.size()-1, allContacts.size()); // null is not a contact
        verify(userDAO);
        verify(userContactDAO);
    }
    

    @Test
    public void update_Successful() {
        expect(userDAO.update(user1)).andReturn(user1);
        replay(userDAO);

        userService.update(user1);
        verify(userDAO);
    }

    @Test
    public void findAll() {
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        expect(userDAO.findAll()).andReturn(users);
        replay(userDAO);

        List<User> allUsers = userService.findAll();
        Assert.assertThat(allUsers.size(), is(2));
        verify(userDAO);
    }

    @Test
    public void getUserProfilById() {
        expect(userProfileDAO.findById(user1.getId())).andReturn(userProfile1);
        replay(userProfileDAO);

        UserProfile userProfile1Result = userService.getUserProfilById(1);
        Assert.assertThat(userProfile1Result.getUser(), is(user1));
        verify(userProfileDAO);
    }
    
    @Test
    public void addPictureToProfile() {
        expect(userProfileDAO.update(userProfile1)).andReturn(userProfile1);
        replay(userProfileDAO);

        userService.addPictureToProfile(userProfile1);
        verify(userProfileDAO);
    }

    @Test
    public void getAllUserProfiles() {
        List<UserProfile> userProfiles = new ArrayList<>();
        userProfiles.add(userProfile1);
        userProfiles.add(userProfile2);

        expect(userProfileDAO.findAll()).andReturn(userProfiles);
        replay(userProfileDAO);

        List<UserProfile> userProfilesResult = userService.getAllUserProfiles();
        Assert.assertThat(userProfilesResult.size(), is(2));
        verify(userProfileDAO);
    }
    
    @Test
    public void getAllCommunitiesForUser() {
    	List<Community> communities =  new ArrayList<>();
    	communities.add(new Community("1", "one", 1));
    	communities.add(new Community("2", "two", 1));
    	communities.get(0).addUsers(user1);
    	communities.get(1).addUsers(user1);
    	
        expect(communityDAO.findAll()).andStubReturn(communities);
        replay(communityDAO);
        
        Assert.assertEquals(communities.size(), userService.getAllCommunitiesForUser(user1).size());
        verify(communityDAO);
    }
    
    @Test
    public void getAdmins() {
    	List<User> admin = new ArrayList<>();
    	admin.add(user1);
    	expect(enumDAO.findUsersByEnumeration(4)).andStubReturn(admin);
        replay(enumDAO);
        
        Assert.assertEquals(admin.size(), userService.getAdmins().size());
        verify(enumDAO);
        
    }
    
    @SuppressWarnings("serial")
	@Test
    public void hasUserTheRole_true() {
    	User mock = EasyMock.createMock(User.class);
    	List<Enumeration> roles = new ArrayList<>();
    	
    	roles.add(new Enumeration() {
    		@Override
    		public String getName() {
				return User.ROLE.ADMIN.name();	
    		}
    	});
    	roles.add(new Enumeration() {
    		@Override
    		public String getName() {
				return User.ROLE.PORTALADMIN.name();	
    		}
    	});
    	expect(mock.getId()).andStubReturn(1);
    	expect(mock.getRoles()).andStubReturn(roles);
    	replay(mock);
    	expect(userDAO.findById(mock.getId())).andStubReturn(mock);
    	replay(userDAO);
    	
    	userService.hasUserTheRole(User.ROLE.PORTALADMIN, user1);
    	verify(userDAO);
    }
    
    @Test
    public void hasUserTheRole_false() {

    	expect(userDAO.findById(user1.getId())).andStubReturn(user1);
    	replay(userDAO);
    	
    	userService.hasUserTheRole(User.ROLE.PORTALADMIN, user1);
    	verify(userDAO);
    }

    @Test
    public void addContact_Succesful() {

        expect(userDAO.findByUsername(USERNAME)).andReturn(user1);
        replay(userDAO);

        expect(userContactDAO.doesContactExistForUserId(user1.getId(), user2.getId())).andReturn(false);
        expect(userContactDAO.insert(userContact2)).andReturn(userContact2);
        replay(userContactDAO);

        userService.addContact(user2, user1.getUsername());
        verify(userContactDAO);
        verify(userDAO);
    }

    @Test(expected = ServiceException.class)
    public void addContact_Fail() {
        expect(userDAO.findByUsername(USERNAME)).andReturn(user1);
        replay(userDAO);

        expect(userContactDAO.doesContactExistForUserId(user1.getId(), user2.getId())).andReturn(true);
        replay(userContactDAO);

        userService.addContact(user2, user1.getUsername());
        verify(userDAO);
        verify(userContactDAO);
    }

    @Test(expected = ServiceException.class)
    public void removeContact_Fail() {
        expect(userDAO.findByUsername(user1.getUsername())).andReturn(user1);
        replay(userDAO);

        expect(userContactDAO.doesContactExistForUserId(user1.getId(), user2.getId())).andReturn(false);
        replay(userContactDAO);

        userService.removeContact(user2, user1.getUsername());
        verify(userDAO);
        verify(userContactDAO);
    }

    @Test
    public void removeContact_Successful() {
        expect(userDAO.findByUsername(user1.getUsername())).andReturn(user1);
        replay(userDAO);

        expect(userContactDAO.doesContactExistForUserId(user1.getId(), user2.getId())).andReturn(true);

        userContactDAO.deleteContactForUserIdAndContactId(user1.getId(), user2.getId());
        expectLastCall();
        replay(userContactDAO);

        userService.removeContact(user2, user1.getUsername());
        verify(userDAO);
        verify(userContactDAO);
    }

    @Test
    public void deleteById_Successful() {

        expect(userDAO.findById(user1.getId())).andReturn(user1);

        userDAO.delete(user1);
        expectLastCall();
        replay(userDAO);

        userService.delete(user1.getId());
        verify(userDAO);
    }
    
    /*
     * Exceptions
     */
    
    @Test(expected=ServiceException.class)
    public void insert_withIllegalArgument() {
    	expect(userDAO.insert(user1)).andThrow(new IllegalArgumentException());
    	replay(userDAO);
    	
    	userService.insert(user1);
    }
    
    @Test(expected=ServiceException.class)
    public void insert_withExceptionArgument() {
    	expect(userDAO.insert(user1)).andThrow(new RuntimeException());
    	replay(userDAO);
    	
    	userService.insert(user1);
    }
    
    @Test(expected=ServiceException.class)
    public void delete_withIllegalArgument() {
    	userDAO.delete(user1);
    	expectLastCall().andThrow(new IllegalArgumentException());
    	replay(userDAO);
    	
    	userService.delete(user1);
    }
    
    @Test(expected=ServiceException.class)
    public void delete_withExceptionArgument() {
    	userDAO.delete(user1);
    	expectLastCall().andThrow(new RuntimeException());
    	replay(userDAO);
    	
    	userService.delete(user1);
    }
    
    @Test(expected=ServiceException.class)
    public void login_withNoResult() {
    	expect(userDAO.findByUsername(EasyMock.anyString())).andThrow(new NoResultException());
    	replay(userDAO);
    	
    	userService.login("Scriptkiddy", "passwort1");
    }
    
    @Test(expected=ServiceException.class)
    public void login_withIllegalArgument() {
    	expect(userDAO.findByUsername(EasyMock.anyString())).andThrow(new IllegalArgumentException());
    	replay(userDAO);
    	
    	userService.login("Scriptkiddy", "passwort1");
    }
    
    @Test(expected=ServiceException.class)
    public void login_withExceptionArgument() {
    	expect(userDAO.findByUsername(EasyMock.anyString())).andThrow(new RuntimeException());
    	replay(userDAO);
    	
    	userService.login("Scriptkiddy", "passwort1");
    }
    
    @Test(expected=ServiceException.class)
    public void addContact_withIllegalArgument() {
    	expect(userDAO.findByUsername(EasyMock.anyString())).andThrow(new IllegalArgumentException());
    	replay(userDAO);
    	
    	userService.addContact(user1, "Irgendwer");
    }
    
    @Test(expected=ServiceException.class)
    public void addContact_withIllegalArgumentinUser() {

    	User corrupted = EasyMock.createMock(User.class);
    	
    	expect(corrupted.getId()).andReturn(user1.getId()).once()
    		.andThrow(new IllegalArgumentException());
    	
    	replay(corrupted);
        expect(userDAO.findByUsername(USERNAME)).andReturn(corrupted);
        replay(userDAO);

        expect(userContactDAO.doesContactExistForUserId(user1.getId(), user2.getId())).andReturn(false);
        expect(userContactDAO.insert(userContact2)).andReturn(userContact2);
        replay(userContactDAO);

        userService.addContact(user2, user1.getUsername());
    }
    
    @Test(expected=ServiceException.class)
    public void addContact_withExceptioninUser() {

    	User corrupted = EasyMock.createMock(User.class);
    	
    	expect(corrupted.getId()).andReturn(user1.getId()).once()
    		.andThrow(new RuntimeException());
    	
    	replay(corrupted);
        expect(userDAO.findByUsername(USERNAME)).andReturn(corrupted);
        replay(userDAO);

        expect(userContactDAO.doesContactExistForUserId(user1.getId(), user2.getId())).andReturn(false);
        expect(userContactDAO.insert(userContact2)).andReturn(userContact2);
        replay(userContactDAO);

        userService.addContact(user2, user1.getUsername());
    }
    
    @Test(expected=ServiceException.class)
    public void addContact_withExistingContactFails() {
    	expect(userDAO.findByUsername(EasyMock.anyString())).andThrow(new RuntimeException());
    	replay(userDAO);
    	
    	userService.addContact(user1, "irgendwer");
    }
    
    @Test(expected=ServiceException.class)
    public void emoveContact_withExceptionArgument() {
    	expect(userDAO.findByUsername(EasyMock.anyString())).andThrow(new RuntimeException());
    	replay(userDAO);
    	
    	userService.removeContact(user1, "SomeName");
    }
    
    @Test(expected=ServiceException.class)
    public void removeContact_withIllegalArgument() {
    	expect(userDAO.findByUsername(EasyMock.anyString())).andThrow(new IllegalArgumentException());
    	replay(userDAO);
    	
    	userService.removeContact(user1, "SomeName");
    }
    
    @Test(expected=ServiceException.class)
    public void getAllContactsByUser_withExceptionArgument() {
    	expect(userContactDAO.findContactsbyUser(user1)).andThrow(new RuntimeException());
    	replay(userContactDAO);
    	
    	userService.getAllContactsByUser(user1);
    }
    
    @Test(expected=ServiceException.class)
    public void getAllContactsByUser_withIllegalArgument() {
    	expect(userContactDAO.findContactsbyUser(user1)).andThrow(new IllegalArgumentException());
    	replay(userContactDAO);
    	
    	userService.getAllContactsByUser(user1);
    }
    
    @Test(expected=ServiceException.class)
    public void updateUser_withExceptionArgument() {
    	expect(userDAO.update(user1)).andThrow(new RuntimeException());
    	replay(userDAO);
    	
    	userService.update(user1);
    }
    
    @Test(expected=ServiceException.class)
    public void updateUser_withIllegalArgument() {
    	expect(userDAO.update(user1)).andThrow(new IllegalArgumentException());
    	replay(userDAO);
    	
    	userService.update(user1);
    }

    @Test(expected=ServiceException.class)
    public void findAll_withExceptionArgument() {
    	expect(userDAO.findAll()).andThrow(new RuntimeException());
    	replay(userDAO);
    	
    	userService.findAll();
    }
    
    @Test(expected=ServiceException.class)
    public void findAll_withIllegalArgument() {
    	expect(userDAO.findAll()).andThrow(new IllegalArgumentException());
    	replay(userDAO);
    	
    	userService.findAll();
    }
    
    @Test(expected=ServiceException.class)
    public void getUserProfilById_withExceptionArgument() {
    	expect(userProfileDAO.findById(1)).andThrow(new RuntimeException());
    	replay(userProfileDAO);
    	
    	userService.getUserProfilById(1);
    }
    
    @Test(expected=ServiceException.class)
    public void getUserProfilById_withIllegalArgument() {
    	expect(userProfileDAO.findById(1)).andThrow(new IllegalArgumentException());
    	replay(userProfileDAO);
    	
    	userService.getUserProfilById(1);
    }
    
    @Test(expected=ServiceException.class)
    public void getAllUserProfiles_withExceptionArgument() {
    	expect(userProfileDAO.findAll()).andThrow(new RuntimeException());
    	replay(userProfileDAO);
    	
    	userService.getAllUserProfiles();
    }
    
    @Test(expected=ServiceException.class)
    public void getAllUserProfiles_withIllegalArgument() {
    	expect(userProfileDAO.findAll()).andThrow(new IllegalArgumentException());
    	replay(userProfileDAO);
    	
    	userService.getAllUserProfiles();
    }
    
    @Test(expected=ServiceException.class)
    public void getAllCommunitiesForUser_withExceptionArgument() {
    	expect(communityDAO.findAll()).andThrow(new RuntimeException());
    	replay(communityDAO);
    	
    	userService.getAllCommunitiesForUser(user1);
    }
    
    @Test(expected=ServiceException.class)
    public void getAllCommunitiesForUser_withIllegalArgument() {
    	expect(communityDAO.findAll()).andThrow(new IllegalArgumentException());
    	replay(communityDAO);
    	
    	userService.getAllCommunitiesForUser(user1);
    }
    
    @Test(expected=ServiceException.class)
    public void deleteUser_withExceptionArgument() {
    	
    	expect(userDAO.findById(1)).andStubReturn(user1);
    	userDAO.delete(user1);
    	EasyMock.expectLastCall().andThrow(new RuntimeException());
    	replay(userDAO);
    	
    	userService.delete(1);
    }
    
    @Test(expected=ServiceException.class)
    public void deleteUser_withIllegalArgument() {
    	expect(userDAO.findById(1)).andStubReturn(user1);
    	userDAO.delete(user1);
    	EasyMock.expectLastCall().andThrow(new IllegalArgumentException());
    	replay(userDAO);
    	
    	userService.delete(1);
    }
    
    @Test(expected=ServiceException.class)
    public void findById_withExceptionArgument() {
    	
    	expect(userDAO.findById(1)).andThrow(new RuntimeException());
    	replay(userDAO);
    	
    	userService.findById(1);
    }
    
    @Test(expected=ServiceException.class)
    public void findById_withIllegalArgument() {
    	expect(userDAO.findById(1)).andThrow(new IllegalArgumentException());
    	replay(userDAO);
    	
    	userService.findById(1);
    }
    
    @Test(expected=ServiceException.class)
    public void addPictureToProfile_withExceptionArgument() {
    	
    	userProfileDAO.update(userProfile1);
    	EasyMock.expectLastCall().andThrow(new RuntimeException());
    	replay(userProfileDAO);
    	
    	userService.addPictureToProfile(userProfile1);
    }
    
    @Test(expected=ServiceException.class)
    public void addPictureToProfile_withIllegalArgument() {
    	userProfileDAO.update(userProfile1);
    	EasyMock.expectLastCall().andThrow(new IllegalArgumentException());
    	replay(userProfileDAO);
    	
    	userService.addPictureToProfile(userProfile1);
    }
    
    @Test(expected=ServiceException.class)
    public void getAdmins_withExceptionArgument() {
    	
    	expect(enumDAO.findUsersByEnumeration(4)).andThrow(new RuntimeException());
    	replay(enumDAO);
    	
    	userService.getAdmins();
    }
    
    @Test(expected=ServiceException.class)
    public void getAdmins_withIllegalArgument() {
    	expect(enumDAO.findUsersByEnumeration(4)).andThrow(new IllegalArgumentException());
    	replay(enumDAO);
    	
    	userService.getAdmins();
    }
    
}
