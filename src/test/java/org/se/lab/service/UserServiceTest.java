package org.se.lab.service;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.se.lab.data.*;
import org.se.lab.service.dao.UserContactDAO;
import org.se.lab.service.dao.UserDAO;
import org.se.lab.service.dao.UserProfileDAO;
import org.se.lab.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

@RunWith(EasyMockRunner.class)
public class UserServiceTest {

    public static final int ID = 1;
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

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

    private User user1;
    private User user2;
    private UserProfile userProfile1;
    private UserProfile userProfile2;
    private UserContact userContact1;
    private UserContact userContact2;


    @Before
    public void setUp() throws Exception {
        user1 = new User(USERNAME, PASSWORD);
        user2 = new User("username2", "pwd");
        user1.setId(1);
        user2.setId(2);

        userProfile1 = new UserProfile("James", "Bond", "Abbey 12", "72FE4", "London", "England", "43",  "MI6", "james.bond@gmail.com", "test" , "test", "test userprofile");
        userProfile2 = new UserProfile("Erika", "Musterfrau", "Neuholdgasse 3", "1130", "Vienna", "Austria", "103", "Alpha","erika.musterfrau@edu.fh-joanneum.at", "03165678", "066489101112", "test2");
        user1.setUserProfile(userProfile1);
        user2.setUserProfile(userProfile2);

        userContact1 = new UserContact(user1, 2);
        userContact2 = new UserContact(user2, 1);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void insert_Successful() {
        expect(userDAO.insert(user1)).andReturn(user1);

        userService.insert(user1);
    }

    @Test
    public void deleteByUser_Successful() {
        userDAO.delete(user1);
        expectLastCall();

        userService.delete(user1);
    }

    @Test
    public void login_Successful() {
        expect(userDAO.findByUsername(USERNAME)).andReturn(user1);
        replay(userDAO);

        User user = userService.login(user1.getUsername(), user1.getPassword());
        Assert.assertThat(user, equalTo(user1));
    }

    @Test
    public void login_Fail() {
        expect(userDAO.findByUsername(USERNAME)).andReturn(user1);
        replay(userDAO);

        User user = userService.login(USERNAME, "wrongPassword");
        Assert.assertThat(user, nullValue());
    }

    @Ignore
    @Test
    public void getAllContactsByUser() {
        List<UserContact> userContactList = new ArrayList<>();
        UserContact contact1 = new UserContact(user1, 3);
        UserContact contact2 = new UserContact(user2, 2);

        userContactList.add(contact1);
        userContactList.add(contact2);

        expect(userContactDAO.findAll()).andReturn(userContactList);
        replay(userContactDAO);
        List<User> allContacts = userService.getContactsOfUser(user1);

        Assert.assertThat(allContacts.size(), is(1));
        Assert.assertThat(allContacts.get(0), is(contact1));
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
        Assert.assertThat(allUsers.size(), is(2));
    }

    @Test
    public void getUserProfilById() {
        expect(userProfileDAO.findById(user1.getId())).andReturn(userProfile1);
        replay(userProfileDAO);

        UserProfile userProfile1Result = userService.getUserProfilById(1);
        Assert.assertThat(userProfile1Result.getUser(), is(user1));
    }

    @Test
    public void getAllUserProfiles() {
        List<UserProfile> userProfiles = new ArrayList<>();
        userProfiles.add(userProfile1);
        userProfiles.add(userProfile2);

        expect(userProfileDAO.findAll()).andReturn(userProfiles);
        replay(userProfileDAO);

        List<UserProfile> userProfilesResult = userService.getAllUserProfiles();
        Assert.assertThat(userProfiles.size(), is(2));
    }

    @Test
    public void addContact_Succesful() {

        expect(userDAO.findByUsername(USERNAME)).andReturn(user1);
        replay(userDAO);

        expect(userContactDAO.doesContactExistForUserId(user1.getId(),user2.getId())).andReturn(false);
        expect(userContactDAO.insert(userContact2)).andReturn(userContact2);
        replay(userContactDAO);

        userService.addContact(user2, user1.getUsername());
    }

    @Test(expected = ServiceException.class)
    public void addContact_Fail() {
        expect(userDAO.findByUsername(USERNAME)).andReturn(user1);
        replay(userDAO);

        expect(userContactDAO.doesContactExistForUserId(user1.getId(),user2.getId())).andReturn(true);
        replay(userContactDAO);

        userService.addContact(user2, user1.getUsername());
    }

    @Test(expected = ServiceException.class)
    public void removeContact_Fail() {
        expect(userDAO.findByUsername(user1.getUsername())).andReturn(user1);
        replay(userDAO);

        expect(userContactDAO.doesContactExistForUserId(user1.getId(),user2.getId())).andReturn(false);
        replay(userContactDAO);

        userService.removeContact(user2, user1.getUsername());
    }

    @Test
    public void removeContact_Successful() {
        expect(userDAO.findByUsername(user1.getUsername())).andReturn(user1);
        replay(userDAO);

        expect(userContactDAO.doesContactExistForUserId(user1.getId(),user2.getId())).andReturn(true);

        userContactDAO.deleteContactForUserIdAndContactId(user1.getId(),user2.getId());
        expectLastCall();
        replay(userContactDAO);

        userService.removeContact(user2,user1.getUsername());
    }

    @Test
    public void deleteById_Successful() {

        expect(userDAO.findById(user1.getId())).andReturn(user1);

        userDAO.delete(user1);
        expectLastCall();
        replay(userDAO);

        userService.delete(user1.getId());
    }

}
