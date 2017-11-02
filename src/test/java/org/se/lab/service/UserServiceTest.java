package org.se.lab.service;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.se.lab.data.*;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.is;

@RunWith(EasyMockRunner.class)
public class UserServiceTest {

    public static final int ID = 1;
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    @TestSubject
    private UserService userService = new UserService();

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private UserDAO userDAO;
    @Mock
    private UserContactDAO userContactDAO;
    @Mock
    private UserProfileDAO userProfileDAO;

    private User user1;
    private User user2;
    private UserProfile userProfile1;
    private UserProfile userProfile2;

    @Before
    public void setUp() throws Exception {
        user1 = new User(ID, USERNAME, PASSWORD);
        user2 = new User(2,"username2","pwd");

        userProfile1 = new UserProfile(1, user1,"Max", "Mustermann","max.mustermann@edu.fh-joanneum.at","03161234","06641234567", "test1");
        userProfile2 = new UserProfile(2, user2,"Erika", "Musterfrau","erika.musterfrau@edu.fh-joanneum.at","03165678","066489101112", "test2");

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void insert_Successful() {
        expect(userDAO.insert(user1)).andReturn(user1);

        userService.insert(user1);
    }

    @Ignore //cant handle because of mock doesn`t have an exception in method signature
    @Test(expected = ServiceException.class)
    public void insert_ThrowException() {
        expect(userDAO.insert(user1)).andThrow(new Exception());
        replay();
        userService.insert(user1);
    }

    @Test
    public void delete_Successful() {
        userDAO.delete(user1);
        expectLastCall();

        userService.delete(user1);
    }

    @Test
    public void login_Successful() {
        expect(userDAO.loadByUsername(USERNAME)).andReturn(user1);
        replay(userDAO);

        userService.login(user1.getUsername(), user1.getPassword());
    }

    @Test(expected = ServiceException.class)
    public void login_Fail() {
        expect(userDAO.loadByUsername(USERNAME)).andReturn(user1);
        replay(userDAO);

        userService.login(USERNAME, "wrongPassword");
    }

    @Test
    public void getAllContactsByUser() {
        List<UserContact> userContactList = new ArrayList<>();
        UserContact contact1 = new UserContact(1,user1, 3);
        UserContact contact2 = new UserContact(2,user2,2);
        userContactList.add(contact1);
        userContactList.add(contact2);

        expect(userContactDAO.findAll()).andReturn(userContactList);
        replay(userContactDAO);
        List<UserContact> allContacts = userService.getAllContactsByUser(user1);

        Assert.assertThat(allContacts.size(),is(1));
        Assert.assertThat(allContacts.get(0),is(contact1));
    }

    @Test
    public void update_Successful() {
        expect(userDAO.update(user1)).andReturn(user1);
        replay(userDAO);

        userService.update(user1);
    }

    @Test
    public void findAll() {
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        expect(userDAO.findAll()).andReturn(users);
        replay(userDAO);

        List<User> allUsers = userService.findAll();
        Assert.assertThat(allUsers.size(),is(2));
    }

    @Test
    public void getUserProfilById() {

        expect(userProfileDAO.findById(user1.getId())).andReturn(userProfile1);
        replay(userProfileDAO);

        UserProfile userProfile1Result = userService.getUserProfilById(1);
        Assert.assertThat(userProfile1Result.getUser(),is(user1));
    }

    @Test
    public void getAllUserProfiles() {
        List<UserProfile> userProfiles = new ArrayList<>();
        userProfiles.add(userProfile1);
        userProfiles.add(userProfile2);

        expect(userProfileDAO.findAll()).andReturn(userProfiles);
        replay(userProfileDAO);

        List<UserProfile> userProfilesResult = userService.getAllUserProfiles();
        Assert.assertThat(userProfiles.size(),is(2));
    }
}
