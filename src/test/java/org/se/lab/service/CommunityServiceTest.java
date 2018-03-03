package org.se.lab.service;

import org.easymock.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.primefaces.model.NativeUploadedFile;
import org.primefaces.model.UploadedFile;
import org.se.lab.db.dao.CommunityDAO;
import org.se.lab.db.dao.FileDAO;
import org.se.lab.db.data.*;

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
	private static final User user1 = new User("Test", "User");
    
    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);
    List<Community> communities;
    
    @TestSubject
    private CommunityService communityService = new CommunityServiceImpl();
    
    @Mock
    private EnumerationService enumerationService;
    
    @Mock
    private UserService userService;
    
    @Mock
    private PrivateMessageService pmService;
    
    @Mock
    private CommunityDAO communityDAO;
    
    @Mock
    private FileDAO fileDAO;
    
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
        
        Enumeration archived = new Enumeration(8);
        archived.setName("archived");

        expect(enumerationService.getPending()).andStubReturn(pending);
        expect(enumerationService.getApproved()).andStubReturn(approved);
        expect(enumerationService.getRefused()).andStubReturn(refused);
        expect(enumerationService.findById(8)).andStubReturn(archived);

        replay(enumerationService);

        community1 = new Community("name1", "description1", 1);
        community1.setState(enumerationService.getApproved());
        community2 = new Community("name2", "description2", 1);
        community2.setState(enumerationService.getPending());
        community3 = new Community("name3", "description3", 1);
        community3.setState(enumerationService.getRefused());

        communities = new ArrayList<>();
    }
    
    /*
     * Happy path
     */
    
    
    @Test
    public void test_leave() {
    	expect(communityDAO.update(community1)).andReturn(community1);
    	replay(communityDAO);
       
        communityService.leave(community1, new User("username2", "pwd"));
        verify(communityDAO);
    }
    
    @Test
    public void test_uploadFile() {
    	UploadedFile file = EasyMock.createMock(UploadedFile.class);
    	
    	expect(file.getFileName()).andStubReturn("Mockfile");
    	expect(file.getContents()).andStubReturn(new byte[1]);
    	expect(fileDAO.insert(EasyMock.anyObject())).andStubReturn(new File());
    	replay(file, fileDAO);
    	communityService.uploadFile(community1, file);
    	communityService.uploadFile(user1, file);
    	verify(fileDAO);
    }
    
    @Test
    public void deleteFile() {
    	File file = EasyMock.createMock(File.class);
    	fileDAO.delete(file);
    	EasyMock.expectLastCall();
    	replay(file, fileDAO);
    	
    	communityService.deleteFile(file);
    	verify(fileDAO);
    }
    
    @Test
    public void test_getFilesFromCommunity() {
    	expect(fileDAO.findByCommunity(EasyMock.anyObject())).andReturn(new ArrayList<>());
    	replay(fileDAO);
    	communityService.getFilesFromCommunity(community1);
    	verify(fileDAO);
    }
    
    @Test
    public void test_delete() {
    	
       
    	expect(communityDAO.update(community1)).andReturn(community1);
    	replay(communityDAO);
       
        
        communityService.delete(community1);
        verify(communityDAO);
        verify(enumerationService);
    }

    @Test
    public void approve() {
        Community community = new Community(NAME, DESCRIPTION, 1);

        Community communityResult = new Community(NAME, DESCRIPTION, 1);
        community.setState(enumerationService.getPending());

        Capture<Community> communityCapture = EasyMock.newCapture();
        expect(communityDAO.update(capture(communityCapture))).andReturn(communityResult);
        replay(communityDAO);

        communityService.approve(community);
        Assert.assertThat(communityCapture.getValue().getState(), is(enumerationService.getApproved()));
        
        verify(communityDAO);
    }

    @Test
    public void request() {
    	List<User> admins = new ArrayList<>();
    	admins.add(user1);
        expect(communityDAO.createCommunity(EasyMock.anyString(), EasyMock.anyString(), eq(1), eq(false))).andReturn(community1);
        expect(userService.findById(1)).andReturn(user1); 
        expect(userService.getAdmins()).andReturn(admins);
        replay(communityDAO, userService);
        communityService.request("Easy", "Mock", 1);
        verify(communityDAO);
        verify(userService);
    }

    @Test
    public void findAll_Successful() {
        communities.add(community1);
        communities.add(community2);

        expect(communityDAO.findAll()).andReturn(communities);
        replay(communityDAO);

        communityService.findAll();
        verify(communityDAO);
    }
    
    @Test
    public void test_findByName() {
    	final String name = "Hello";
    	expect(communityDAO.findByName(EasyMock.anyString())).andReturn(community1);
        replay(communityDAO);
        Assert.assertEquals(communityService.findByName(name).toString(), community1.toString());
        EasyMock.verify(communityDAO);
        verify(communityDAO);
    }

    @Test
    public void getApproved_Successful() {
        communities.add(community1);

        expect(communityDAO.findCommunitiesByState(Enumeration.State.APPROVED)).andReturn(communities);
        replay(communityDAO);
        
        communityService.getApproved();
        verify(communityDAO);
    }

    @Test
    public void getPending_Successful() {
        communities.add(community2);

        expect(communityDAO.findCommunitiesByState(Enumeration.State.PENDING)).andReturn(communities);
        replay(communityDAO);
        
        communityService.getPending();
        verify(communityDAO);
    }

    @Test
    public void update_Successful() {
        expect(communityDAO.update(community1)).andReturn(community1);
        replay(communityDAO);
        
        communityService.update(community1);
        verify(communityDAO);
    }

    @Test
    public void join_Successful() {
        User user = null;

        user = new User("username2", "pwd");

        communityService.join(community1, user);
    }

    @Test
    public void findById_Successful() {
        expect(communityDAO.findById(ID)).andReturn(community1);
        replay(communityDAO);
        
        communityService.findById(ID);
        verify(communityDAO);
    }

    @Test
    public void refuse_Successful() {
        community3.setState(enumerationService.getPending());

        expect(communityDAO.update(community3)).andReturn(community3);
        replay(communityDAO);

        communityService.refuse(community3);

        Assert.assertThat(community3.getState(), is(enumerationService.getRefused()));
        verify(communityDAO);
    }

    
    /*
     * Exception testing
     */
    
    @Test(expected = ServiceException.class)
    public void findAll_withIllegalArgumentException() {
    	expect(communityDAO.findAll()).andThrow(new IllegalArgumentException());
        replay(communityDAO);
        communityService.findAll();
    }
    
    @Test(expected = ServiceException.class)
    public void findAll_withException() {
    	expect(communityDAO.findAll()).andThrow(new RuntimeException());
        replay(communityDAO);

        communityService.findAll();
    }
    
    @Test(expected = ServiceException.class)
    public void getApproved_withIllegalArgumentException() {
    	expect(communityDAO.findCommunitiesByState(Enumeration.State.APPROVED)).andThrow(new IllegalArgumentException());
        replay(communityDAO);
        communityService.getApproved();
    }
    
    @Test(expected = ServiceException.class)
    public void getApproved_withException() {
    	expect(communityDAO.findCommunitiesByState(Enumeration.State.APPROVED)).andThrow(new RuntimeException());
        replay(communityDAO);
        communityService.getApproved();
    }
    
    @Test(expected = ServiceException.class)
    public void getPending_withIllegalArgumentException() {
    	expect(communityDAO.findCommunitiesByState(Enumeration.State.PENDING)).andThrow(new IllegalArgumentException());
        replay(communityDAO);
        communityService.getPending();
    }
    
    @Test(expected = ServiceException.class)
    public void getPending_withException() {
    	expect(communityDAO.findCommunitiesByState(Enumeration.State.PENDING)).andThrow(new RuntimeException());
        replay(communityDAO);
        communityService.getPending();
    }
    
    @Test(expected = ServiceException.class)
    public void notifyAdmins_withIllegalArgumentException() {
    	expect(communityDAO.createCommunity(EasyMock.anyString(), EasyMock.anyString(), eq(1), eq(false))).andReturn(community1);
        expect(userService.findById(1)).andThrow(new IllegalArgumentException());
        replay(communityDAO, userService);
        communityService.request("Easy", "Mock", 1);
    }
    
    @Test(expected = ServiceException.class)
    public void notifyAdmins_withException() {
    	expect(communityDAO.createCommunity(EasyMock.anyString(), EasyMock.anyString(), eq(1), eq(false))).andReturn(community1);
        expect(userService.findById(1)).andThrow(new RuntimeException());
        replay(communityDAO, userService);
        communityService.request("Easy", "Mock", 1);
    }
    
    @Test(expected = ServiceException.class)
    public void join_Fail() {

        communityService.join(community1, null);
    }
    
    @Test(expected = ServiceException.class)
    public void request_withIllegalArgumentException() {
    	expect(communityDAO.createCommunity(EasyMock.anyString(), EasyMock.anyString(), eq(1), eq(false))).andThrow(new IllegalArgumentException());
        replay(communityDAO);
        communityService.request("Easy", "Mock", 1);
    }
    
    @Test(expected = ServiceException.class)
    public void request_withException() {
    	expect(communityDAO.createCommunity(EasyMock.anyString(), EasyMock.anyString(), eq(1), eq(false))).andThrow(new RuntimeException());
        replay(communityDAO);
        communityService.request("Easy", "Mock", 1);
    }
    
    @Test(expected = ServiceException.class)
    public void findById_withIllegalArgumentException() {
    	expect(communityDAO.findById(eq(1))).andThrow(new IllegalArgumentException());
        replay(communityDAO);
        communityService.findById(1);
    }
    
    @Test(expected = ServiceException.class)
    public void findById_withException() {
    	expect(communityDAO.findById(eq(1))).andThrow(new RuntimeException());
        replay(communityDAO);
        communityService.findById(1);
    }
    
    @Test(expected = ServiceException.class)
    public void getFilesFromUser_withIllegalArgumentException() {
    	expect(fileDAO.findByUser(EasyMock.anyObject())).andThrow(new IllegalArgumentException());
        replay(fileDAO);
        communityService.getFilesFromUser(user1);
    }
    
    @Test(expected = ServiceException.class)
    public void getFilesFromUser_withException() {
    	expect(fileDAO.findByUser(EasyMock.anyObject())).andThrow(new RuntimeException());
        replay(fileDAO);
        communityService.getFilesFromUser(user1);
    }
    
    @Test(expected = ServiceException.class)
    public void deleteFile_withIllegalArgumentException() {
    	File file = EasyMock.createMock(File.class);
    	fileDAO.delete(file);
    	EasyMock.expectLastCall().andThrow(new IllegalArgumentException());
    	replay(file, fileDAO);
    	communityService.deleteFile(file);
    }
    
    @Test(expected = ServiceException.class)
    public void deleteFile_withException() {
    	File file = EasyMock.createMock(File.class);
    	expect(file.getFilename()).andStubReturn("Stub");
    	fileDAO.delete(file);
    	EasyMock.expectLastCall().andThrow(new RuntimeException());
    	replay(file, fileDAO);
    	communityService.deleteFile(file);
    	
    }
    
    @Test(expected = ServiceException.class)
    public void findByName_withIllegalArgumentException() {
    	expect(communityDAO.findByName(EasyMock.anyString())).andThrow(new IllegalArgumentException());
        replay(communityDAO);
        communityService.findByName("");
    }
    
    @Test(expected = ServiceException.class)
    public void uploadFile_withIllegalArgumentException() {
    	UploadedFile file = EasyMock.createMock(UploadedFile.class);
    	
    	expect(file.getFileName()).andStubReturn("Mockfile");
    	expect(file.getContents()).andStubReturn(new byte[1]);
    	expect(fileDAO.insert(EasyMock.anyObject())).andThrow(new IllegalArgumentException());
    	replay(file, fileDAO);
    	communityService.uploadFile(user1, file);
    }
    
    @Test(expected = ServiceException.class)
    public void findByName_withException() {
    	expect(communityDAO.findByName(EasyMock.anyString())).andThrow(new RuntimeException());
        replay(communityDAO);
        communityService.findByName("");
    }
    
    @Test(expected = ServiceException.class)
    public void fileUpload_withIllegalArgumentException() {
    	UploadedFile file = EasyMock.createMock(UploadedFile.class);
    	
    	expect(file.getFileName()).andStubReturn("Mockfile");
    	expect(file.getContents()).andReturn(new byte[1]);
    	expect(fileDAO.insert(EasyMock.anyObject())).andThrow(new IllegalArgumentException());
    	replay(file, fileDAO);
    	communityService.uploadFile(community1, file);
    }
    
    @Test(expected = ServiceException.class)
    public void fileUpload_withException() {
    	UploadedFile file = EasyMock.createMock(UploadedFile.class);
    	
    	expect(file.getFileName()).andStubReturn("Mockfile");
    	expect(file.getContents()).andReturn(new byte[1]);
    	expect(fileDAO.insert(EasyMock.anyObject())).andThrow(new RuntimeException());
    	replay(file, fileDAO);
    	communityService.uploadFile(community1, file);
    }
    
    @Test(expected = ServiceException.class)
    public void getFilesFfromCommunityd_withIllegalArgumentException() {
    	expect(fileDAO.findByCommunity(EasyMock.anyObject())).andThrow(new IllegalArgumentException());
    	replay(fileDAO);
    	communityService.getFilesFromCommunity(community1);
    }
    
    @Test(expected = ServiceException.class)
    public void getFilesFfromCommunity_withException() {
    	expect(fileDAO.findByCommunity(EasyMock.anyObject())).andThrow(new RuntimeException());
    	replay(fileDAO);
    	communityService.getFilesFromCommunity(community1);
    }

    
    @Test(expected = ServiceException.class)
    public void approve_withIllegalArgumentException() {
    	Community badCommunity = EasyMock.createMock(Community.class);
    	expect(badCommunity.getState()).andStubReturn(enumerationService.getPending());
    	badCommunity.setState(enumerationService.getApproved());
    	EasyMock.expectLastCall().andThrow(new IllegalArgumentException());
    	replay(badCommunity);
    	
        communityService.approve(badCommunity);
    }
    
    @Test(expected = ServiceException.class)
    public void approve_withException() {
    	Community badCommunity = EasyMock.createMock(Community.class);
    	expect(badCommunity.getName()).andStubReturn("Call for get name on an invalid community?");
    	expect(badCommunity.getState()).andStubReturn(enumerationService.getPending());
    	badCommunity.setState(enumerationService.getApproved());
    	EasyMock.expectLastCall().andThrow(new RuntimeException());
    	replay(badCommunity);
    	
        communityService.approve(badCommunity);
    }
    
    @Test(expected = ServiceException.class)
    public void refuse_withIllegalArgumentException() {
    	Community badCommunity = EasyMock.createMock(Community.class);
    	expect(badCommunity.getState()).andStubReturn(enumerationService.getPending());
    	badCommunity.setState(enumerationService.getRefused());
    	EasyMock.expectLastCall().andThrow(new IllegalArgumentException());
    	replay(badCommunity);
    	
        communityService.refuse(badCommunity);
    }
    
    @Test(expected = ServiceException.class)
    public void refuse_withException() {
    	  	
    	Community badCommunity = EasyMock.createMock(Community.class);
    	expect(badCommunity.getName()).andStubReturn("MyName");
    	expect(badCommunity.getState()).andStubReturn(enumerationService.getPending());
    	badCommunity.setState(enumerationService.getRefused());
    	EasyMock.expectLastCall().andThrow(new RuntimeException());
    	replay(badCommunity);
    	
        communityService.refuse(badCommunity);
    }
    
    @Test(expected = ServiceException.class)
    public void approve_withException_NotPendingCommunity() {
    	communityService.approve(community1);
    }
    
    @Test(expected = ServiceException.class)
    public void delete_withIllegalArgumentException() {
    	
    	expect(communityDAO.update(community1)).andThrow(new IllegalArgumentException());
    	replay(communityDAO);
       
        
        communityService.delete(community1);

    }
    
    @Test(expected = ServiceException.class)
    public void delete_withException() {
    	
    	expect(communityDAO.update(community1)).andThrow(new RuntimeException());
    	replay(communityDAO);
       
        
        communityService.delete(community1);
    }
    
    @Test(expected = ServiceException.class)
    public void update_withIllegalArgumentException() {
    	
    	expect(communityDAO.update(community1)).andThrow(new IllegalArgumentException());
    	replay(communityDAO);
       
        
        communityService.update(community1);

    }
    
    @Test(expected = ServiceException.class)
    public void update_withException() {
    	
    	expect(communityDAO.update(community1)).andThrow(new RuntimeException());
    	replay(communityDAO);
       
        
        communityService.update(community1);
    }
    
    @Test(expected = ServiceException.class)
    public void join_withIllegalArgumentException() {
    	Community badCommuntiy = EasyMock.createMock(Community.class);
    	badCommuntiy.addUsers(user1);
    	EasyMock.expectLastCall().andThrow(new IllegalArgumentException());
    	replay(badCommuntiy);
        communityService.join(badCommuntiy, new User("username2", "pwd"));
        
    }
    
    @Test(expected = ServiceException.class)
    public void leave_withIllegalArgumentException() {
    	Community badCommuntiy = EasyMock.createMock(Community.class);
    	badCommuntiy.removeUsers(user1);
    	EasyMock.expectLastCall().andThrow(new IllegalArgumentException());
    	replay(badCommuntiy);
    	
        communityService.leave(badCommuntiy, new User("username2", "pwd")); 
    }
    
    @Test(expected = ServiceException.class)
    public void leave_withException() {
    	
		Community badCommuntiy = EasyMock.createMock(Community.class);
    	badCommuntiy.removeUsers(user1);
    	EasyMock.expectLastCall().andThrow(new RuntimeException());
    	replay(badCommuntiy);
    	
        communityService.leave(badCommuntiy, new User("username2", "pwd")); 
    }
    
    @Test(expected = ServiceException.class)
    public void leave_withException_MissingUser() {
    	
        communityService.leave(community1, null); 
    }
    
    @Test(expected = ServiceException.class)
    public void join_withException() {
    	expect(communityDAO.update(community1)).andThrow(new RuntimeException());
    	replay(communityDAO);
       
        
        communityService.join(community1, new User("username2", "pwd"));
    }
    
    @Test(expected = ServiceException.class)
    public void refuse_Fail() {
        communityService.refuse(community3);
    }

    @Test(expected = ServiceException.class)
    public void uploadFile_throwException_MissingFilename() {
        UploadedFile uploadedFile = new NativeUploadedFile();

        communityService.uploadFile(new User(), uploadedFile);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void uploadFile_throwException_MissingUser() {
        UploadedFile uploadedFile = new NativeUploadedFile();

        communityService.uploadFile((User) null, uploadedFile);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void uploadFile_throwException_MissingCommunity() {
        UploadedFile uploadedFile = new NativeUploadedFile();

        communityService.uploadFile((Community) null, uploadedFile);
    }

    @Test(expected = IllegalArgumentException.class)
    public void uploadFile_throwException_MissingFile() {
        communityService.uploadFile(new User(), null);
    }

    @Test
    public void getFiles() {
        User user = new User();
        byte[] data = "testByte".getBytes();
        File file = new File(user, "test", data);
        List<File> files = Collections.singletonList(file);


        expect(fileDAO.findByUser(user)).andReturn(files);
        replay(fileDAO);

        List<File> filesFromUser = communityService.getFilesFromUser(user);
        assertThat(filesFromUser, hasItem(file));
    }
}
