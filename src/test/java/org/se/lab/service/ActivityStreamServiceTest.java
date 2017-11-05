package org.se.lab.service;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.se.lab.data.Community;
import org.se.lab.data.Post;
import org.se.lab.data.PostDAO;
import org.se.lab.data.User;

import java.util.Date;

@RunWith(EasyMockRunner.class)
public class ActivityStreamServiceTest {

    public static final String DESCRIPTION = "description";
    public static final String NAME = "name";
    public static final int ID = 1;
    @TestSubject
    private ActivityStreamService activityStreamService = new ActivityStreamService();

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private PostDAO dao;

    private Community community;
    private Post post;

    @Before
    public void setup() {
        community = new Community(NAME, DESCRIPTION);
        post = new Post(1, null, community, new User(2, "username", "password"),
                "msg", new Date());
    }

    @Test
    public void insert_withoutCommunity() {
//        expect(dao.insert(community))

    }

}
