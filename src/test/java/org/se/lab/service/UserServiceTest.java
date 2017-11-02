package org.se.lab.service;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.se.lab.data.User;
import org.se.lab.data.UserContact;
import org.se.lab.data.UserContactDAO;
import org.se.lab.data.UserDAO;

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

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User(ID, USERNAME, PASSWORD);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void insert_Successful() {
        expect(userDAO.insert(user)).andReturn(user);

        userService.insert(user);
    }

    @Ignore //cant handle because of mock doesn`t have an exception in method signature
    @Test(expected = ServiceException.class)
    public void insert_ThrowException() {
        expect(userDAO.insert(user)).andThrow(new Exception());
        replay();
        userService.insert(user);
    }

    @Test
    public void delete_Successful() {
        userDAO.delete(user);
        expectLastCall();

        userService.delete(user);
    }

    @Test
    public void login_Successful() {
        expect(userDAO.loadByUsername(USERNAME)).andReturn(user);
        replay(userDAO);

        userService.login(user.getUsername(), user.getPassword());
    }

    @Test(expected = ServiceException.class)
    public void login_Fail() {
        expect(userDAO.loadByUsername(USERNAME)).andReturn(user);
        replay(userDAO);

        userService.login(USERNAME, "wrongPassword");
    }

    @Test
    public void getAllContactsByUser() {
        List<UserContact> userContactList = new ArrayList<>();
        UserContact contact1 = new UserContact(1,user, 3);
        UserContact contact2 = new UserContact(2,new User(2,"username2","pwd"),2);
        userContactList.add(contact1);
        userContactList.add(contact2);

        expect(userContactDAO.findAll()).andReturn(userContactList);
        replay(userContactDAO);
        List<UserContact> allContacts = userService.getAllContactsByUser(user);

        Assert.assertThat(allContacts.size(),is(1));
        Assert.assertThat(allContacts.get(0),is(contact1));
    }

}
