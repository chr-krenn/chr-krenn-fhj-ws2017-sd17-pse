package org.se.lab.web;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.se.lab.db.data.Community;
import org.se.lab.db.data.File;
import org.se.lab.db.data.Post;
import org.se.lab.db.data.User;
import org.se.lab.service.*;
import org.se.lab.utils.ArgumentChecker;
import org.se.lab.web.helper.RedirectHelper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class CommunityDataBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private final static Logger LOG = Logger.getLogger(CommunityDataBean.class);
    private ExternalContext context;
    private String name;
    private boolean publicState;
    private String description;
    private String inputText;
    private String inputTextChild;
    private Community actualCommunity;
    private Map<String, Object> session;
    private User user;
    private List<Post> communityPosts;
	private String errorMsg = "";
    private List<File> files = new ArrayList<>();
    private String joinLeaveState;
    private boolean isPortalAdmin = false;

    @Inject
    private CommunityService communityService;

    @Inject
    private UserService userService;

    @Inject
    private ActivityStreamService activityStreamService;
    
    @Inject
    private PostService pservice;

    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance().getExternalContext();
        session = context.getSessionMap();
        int userId = (int) session.get("user");
        user = getUser(userId);
        setPortalAdmin();
        getFiles();
        
        getActualCommunityStream();
    }

    public User getUser(int id) {
        if (id != 0) {
            return userService.findById(id);
        }
        return null;
    }

    public Community getActualCommunity() {

        int communityId;

        try {


            if (session.size() != 0 && session.get("user") != null) {
                communityId = (int) session.get("communityId");
                actualCommunity = communityService.findById(communityId);
                LOG.info("Opening Communityprofile: " + actualCommunity.getName());
            }

        } catch (ServiceException e) {
            String msg = "Couldn't get current community (Processing Error) ";
            LOG.error(msg, e);
            setErrorMsg(msg);
        }

        if (isUserMember()) {
            joinLeaveState = "Leave";
        } else {
            joinLeaveState = "Join";
        }
        return actualCommunity;
    }

    public void joinOrLeaveCommunity() {

        try {
            if (!isUserMember()) {
                communityService.join(actualCommunity, user);
            } else {
                communityService.leave(actualCommunity, user);
            }
        } catch (ServiceException e) {
            String msg = "Join or Leave community failed.";
            LOG.error(msg, e);
            setErrorMsg(msg);
        }

    }

    private boolean isUserMember() {

        int userId = (int) session.get("user");

        user = userService.findById(userId);
        List<Community> listCommunity;

        listCommunity = userService.getAllCommunitiesForUser(user);

        return listCommunity.contains(actualCommunity);
    }

    public void getActualCommunityStream() {
        getActualCommunity();
        communityPosts = activityStreamService.getPostsForCommunity(actualCommunity);
    }
    
    public void deletePost(Post p) {
        ArgumentChecker.assertNotNull(p, "post");
        activityStreamService.delete(p, user);

        RedirectHelper.redirect("/pse/communityprofile.xhtml");

    }

    public boolean showDeleteButton(Post p) {
        return p != null && p.getCommunity() != null
                && p.getCommunity().getPortaladminId() == user.getId();
    }
    
    public void newPost(Post parentPost) {
        if (parentPost == null) {
            try {
                 pservice.createChildPost(null, actualCommunity, user, inputText, new Date());
            } catch (ServiceException e) {
                String msg = "Couln't create root post.";
                LOG.error(msg, e);
                setErrorMsg(msg);
            }
        } else {
            LOG.info("appending comment to post: " + inputTextChild);
            try {
                pservice.createChildPost(parentPost, actualCommunity, user, inputTextChild, new Date());
            } catch (ServiceException e) {
                String msg = "Couldn't create child post";
                LOG.error(msg, e);
                setErrorMsg(msg);
            }
        }

        RedirectHelper.redirect("/pse/communityprofile.xhtml");
    }

    public void deleteFile(File file) {
        LOG.info("deleteFile " + file);
        
        try {
            communityService.deleteFile(file);

            RedirectHelper.redirect("/pse/communityprofile.xhtml");

        } catch (ServiceException e) {
            errorMsg = "Can't delete file without errors! - pls contact the admin or try later";
            LOG.error(errorMsg);
            setErrorMsg(errorMsg);
        }
    }

    public void uploadFile(FileUploadEvent event) {
        UploadedFile uploadedFile = event.getFile();

        getActualCommunity();
        
        try {
        	if(hasUserPrivilege(User.ROLE.PORTALADMIN)) {
        		communityService.uploadFile(actualCommunity, uploadedFile);
        	}

            RedirectHelper.redirect("/pse/communityprofile.xhtml");

        } catch (ServiceException e) {
            errorMsg = "Can't upload file without errors! - pls contact the admin or try later";
            LOG.error(errorMsg);
            setErrorMsg(errorMsg);
        }
    }

    public List<File> getFiles() {
    	getActualCommunity();
        if (actualCommunity != null) {
            try {
                setFiles(communityService.getFilesFromCommunity(actualCommunity));
                return files;
            } catch (ServiceException e) {
                String msg = "Can't get files for community";
                LOG.error(msg, e);
                setErrorMsg(msg);
            }
        }
        return Collections.emptyList();
    }

    public StreamedContent getFile(int id) {

        List<File> files = this.files.stream().filter(file -> file.getId() == id).collect(Collectors.toList());

        if (files.size() != 1) {
            return null;
        }

        DefaultStreamedContent defaultStreamedContent = new DefaultStreamedContent(new ByteArrayInputStream(files.get(0).getData()));
        defaultStreamedContent.setName(files.get(0).getFilename());

        return defaultStreamedContent;
    }


    private boolean hasUserPrivilege(User.ROLE role) {
        try {
            return userService.hasUserTheRole(role, user);
        } catch (ServiceException e) {
            errorMsg = String.format("Can't check privilege of user: %s", user.getUsername());
            LOG.error(errorMsg);
            setErrorMsg(errorMsg);
            return false;
        }
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public boolean isPortalAdmin() {
        return isPortalAdmin;
    }

    public void setPortalAdmin() {
        if (user != null) {
            isPortalAdmin = hasUserPrivilege(User.ROLE.PORTALADMIN);
        }
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPublicState() {
        return publicState;
    }

    public void setPublicState(boolean publicState) {
        this.publicState = publicState;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJoinLeaveState() {
        return joinLeaveState;
    }

    public void setJoinLeaveState(String joinLeaveState) {
        this.joinLeaveState = joinLeaveState;
    }

    public String getInputText() {
		return inputText;
	}

	public void setInputText(String inputText) {
		this.inputText = inputText;
	}

	public String getInputTextChild() {
		return inputTextChild;
	}

	public void setInputTextChild(String inputTextChild) {
		this.inputTextChild = inputTextChild;
	}

    public List<Post> getCommunityPosts() {
		return communityPosts;
	}

	public void setCommunityPosts(List<Post> communityPosts) {
		this.communityPosts = communityPosts;
	}
	
	private void writeObject(ObjectOutputStream stream)
            throws IOException {
        stream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
    }
}
