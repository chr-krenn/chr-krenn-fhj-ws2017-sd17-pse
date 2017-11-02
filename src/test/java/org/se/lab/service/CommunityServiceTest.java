package org.se.lab.service;

import org.easymock.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.se.lab.data.Community;
import org.se.lab.data.CommunityDAO;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.is;

@RunWith(EasyMockRunner.class)
public class CommunityServiceTest {

    public static final int ID = 1;
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String APPROVE_STATE = "approve";
    public static final String PENDING_STATE = "pending";

    @TestSubject
    private CommunityService communityService = new CommunityService();

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private CommunityDAO communityDAO;

    @Test
    public void approve() {
        Community community = new Community(ID, NAME, DESCRIPTION);

        Community communityResult = new Community(ID, NAME, DESCRIPTION);
        community.setState(APPROVE_STATE);

        Capture<Community> communityCapture = new Capture<Community>();
        expect(communityDAO.update(capture(communityCapture))).andReturn(communityResult);
        replay(communityDAO);

        communityService.approve(community);
        Assert.assertThat(communityCapture.getValue().getState(), is(APPROVE_STATE));
    }

    @Test
    public void request() {
        Community community = new Community(ID, NAME, DESCRIPTION);

        Community communityResult = new Community(ID, NAME, DESCRIPTION);
        community.setState(PENDING_STATE);

        Capture<Community> communityCapture = new Capture<Community>();
        expect(communityDAO.insert(capture(communityCapture))).andReturn(communityResult);
        replay(communityDAO);

        communityService.request(community);
        Assert.assertThat(communityCapture.getValue().getState(), is(PENDING_STATE));
    }
}
