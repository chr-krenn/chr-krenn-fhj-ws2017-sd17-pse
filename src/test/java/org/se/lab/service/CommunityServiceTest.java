package org.se.lab.service;

import org.easymock.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.se.lab.data.Community;
import org.se.lab.data.CommunityDAO;
import org.se.lab.data.Enumeration;
import org.se.lab.data.User;
import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.is;

@RunWith(EasyMockRunner.class)
public class CommunityServiceTest {

    public static final int ID = 1;
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final Enumeration APPROVE_STATE = new Enumeration(2);
    public static final Enumeration PENDING_STATE = new Enumeration(1);

    @TestSubject
    private CommunityService communityService = new CommunityService();

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private CommunityDAO communityDAO;

    private Community community1;
    private Community community2;
    List<Community> communities;

    @Before
    public void setUp() throws Exception {
        community1 = new Community("name1", "description1");
        community1.setState(APPROVE_STATE);
        community2 = new Community("name2", "description2");
        community2.setState(PENDING_STATE);

        communities = new ArrayList<>();
    }

    @Test
    public void approve() {
        Community community = new Community(NAME, DESCRIPTION);

        Community communityResult = new Community(NAME, DESCRIPTION);
        community.setState(APPROVE_STATE);

        Capture<Community> communityCapture = new Capture<Community>();
        expect(communityDAO.update(capture(communityCapture))).andReturn(communityResult);
        replay(communityDAO);

        communityService.approve(community);
        Assert.assertThat(communityCapture.getValue().getState(), is(APPROVE_STATE));
    }

    @Test
    public void request() {
        Community community = new Community(NAME, DESCRIPTION);

        Community communityResult = new Community(NAME, DESCRIPTION);
        community.setState(PENDING_STATE);

        Capture<Community> communityCapture = new Capture<Community>();
        expect(communityDAO.insert(capture(communityCapture))).andReturn(communityResult);
        replay(communityDAO);

        communityService.request(community);
        Assert.assertThat(communityCapture.getValue().getState(), is(PENDING_STATE));
    }

    @Test
    public void findAll_Successful(){
        communities.add(community1);
        communities.add(community2);

        expect(communityDAO.findAll()).andReturn(communities);

        communityService.findAll();
    }

    @Test
    public void getApproved_Successful(){
        communities.add(community1);

        expect(communityDAO.findApprovedCommunities()).andReturn(communities);

        communityService.getApproved();
    }

    @Test
    public void getPending_Successful(){
        communities.add(community2);

        expect(communityDAO.findPendingCommunities()).andReturn(communities);

        communityService.getPending();
    }

    @Test
    public void delete_Successful(){
        communityDAO.delete(community1);
        expectLastCall();

        communityService.delete(community1);
    }

    @Test
    public void update_Successful() {
        expect(communityDAO.update(community1)).andReturn(community1);

        communityService.update(community1);
    }

    @Test
    public void join_Successful(){
        User user = new User("username2", "pwd");
        communityService.join(community1,user);
    }

    @Test (expected = ServiceException.class)
    public void join_Fail(){

        communityService.join(community1,null);
    }

    @Test
    public void findById_Successful(){
        expect(communityDAO.findById(ID)).andReturn(community1);

        communityService.findById(ID);
    }
}
