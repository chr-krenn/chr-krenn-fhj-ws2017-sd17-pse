package org.se.lab.service;

import org.easymock.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.primefaces.model.NativeUploadedFile;
import org.primefaces.model.UploadedFile;
import org.se.lab.data.*;
import org.se.lab.service.dao.CommunityDAO;
import org.se.lab.service.dao.FileDao;
import org.se.lab.service.impl.CommunityServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(EasyMockRunner.class)
public class CommunityServiceTest {

	public static final int ID = 1;
	public static final String NAME = "testcommunity";
	public static final String DESCRIPTION = "testcommunity description";
	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);
	List<Community> communities;
	@TestSubject
	private CommunityService communityService = new CommunityServiceImpl();
	@Mock
	private EnumerationService enumerationService;
	@Mock
	private CommunityDAO communityDAO;
	@Mock
	private FileDao fileDao;
	private Community community1;
	private Community community2;
	private Community community3;

	@Before
	public void setUp() throws Exception {
		Enumeration pending = new Enumeration(1);
		pending.setName("pending");
		Enumeration approved = new Enumeration(2);
		approved.setName("approved");
		Enumeration refused = new Enumeration(3);
		refused.setName("refused");

		expect(enumerationService.getPending()).andStubReturn(pending);
		expect(enumerationService.getApproved()).andStubReturn(approved);
		expect(enumerationService.getRefused()).andStubReturn(refused);

		replay(enumerationService);

		community1 = new Community("name1", "description1",1);
		community1.setState(enumerationService.getApproved());
		community2 = new Community("name2", "description2",1);
		community2.setState(enumerationService.getPending());
		community3 = new Community("name3", "description3",1);
		community3.setState(enumerationService.getRefused());

		communities = new ArrayList<>();
	}

	@Test
	public void aatest() {
		Assert.assertThat(enumerationService.getApproved().getId(), is(2));
		Assert.assertThat(enumerationService.getApproved().getId(), is(2));
		Assert.assertThat(enumerationService.getApproved().getId(), is(2));
	}

	@Test
	public void approve() throws DatabaseException {
		Community community = new Community(NAME, DESCRIPTION,1);

		Community communityResult = new Community(NAME, DESCRIPTION,1);
		community.setState(enumerationService.getPending());

		Capture<Community> communityCapture = EasyMock.newCapture();
		expect(communityDAO.update(capture(communityCapture))).andReturn(communityResult);
		replay(communityDAO);

		communityService.approve(community);
		Assert.assertThat(communityCapture.getValue().getState(), is(enumerationService.getApproved()));
	}

	@Ignore("something wrong with mock configuration for new method request")
	@Test
	public void request() throws DatabaseException {
		Community community = new Community(NAME, DESCRIPTION,1);

        Community communityResult = new Community(NAME, DESCRIPTION,1);
        community.setState(enumerationService.getPending());

        Capture<Community> communityCapture = EasyMock.newCapture();
        expect(communityDAO.insert(capture(communityCapture))).andReturn(communityResult);
        replay(communityDAO);

        Assert.assertThat(communityCapture.getValue().getState(), is(enumerationService.getPending()));
	}

	@Test
	public void findAll_Successful() {
		communities.add(community1);
		communities.add(community2);

		expect(communityDAO.findAll()).andReturn(communities);

		communityService.findAll();
	}

	@Test
	public void getApproved_Successful() {
		communities.add(community1);

		expect(communityDAO.findApprovedCommunities()).andReturn(communities);

		communityService.getApproved();
	}

	@Test
	public void getPending_Successful() {
		communities.add(community2);

		expect(communityDAO.findPendingCommunities()).andReturn(communities);

		communityService.getPending();
	}

	@Test
	public void delete_Successful() {
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
	public void join_Successful() {
		User user = null;
		try {
			user = new User("username2", "pwd");
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		communityService.join(community1, user);
	}

	@Test(expected = ServiceException.class)
	public void join_Fail() {

		communityService.join(community1, null);
	}

	@Test
	public void findById_Successful() {
		expect(communityDAO.findById(ID)).andReturn(community1);

		communityService.findById(ID);
	}

	@Test
	public void refuse_Successful() throws DatabaseException {
		community3.setState(enumerationService.getPending());

		expect(communityDAO.update(community3)).andReturn(community3);
		replay(communityDAO);

		communityService.refuse(community3);

		Assert.assertThat(community3.getState(), is(enumerationService.getRefused()));
	}

	@Test(expected = ServiceException.class)
	public void refuse_Fail() {
		communityService.refuse(community3);
	}

	@Test(expected = ServiceException.class)
	public void uploadFile_throwException_MissingFilename(){
		UploadedFile uploadedFile = new NativeUploadedFile();

		communityService.uploadFile(new User(),uploadedFile);
	}

	@Test(expected = ServiceException.class)
	public void uploadFile_throwException_MissingUser(){
		UploadedFile uploadedFile = new NativeUploadedFile();

		communityService.uploadFile(null,uploadedFile);
	}

	@Test(expected = ServiceException.class)
	public void uploadFile_throwException_MissingFile(){
		communityService.uploadFile(new User(),null);
	}

	@Test
	public void getFiles(){
		User user = new User();
		File file = new File(user, "test", null);
		List<File> files = Collections.singletonList(file);


		expect(fileDao.findByUser(user)).andReturn(files);
		replay(fileDao);

		List<File> filesFromUser = communityService.getFilesFromUser(user);
		assertThat(filesFromUser,hasItem(file));
	}
}
