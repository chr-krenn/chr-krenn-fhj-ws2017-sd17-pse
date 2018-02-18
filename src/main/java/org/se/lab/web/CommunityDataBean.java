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
import org.se.lab.service.ActivityStreamService;
import org.se.lab.service.CommunityService;
import org.se.lab.service.ServiceException;
import org.se.lab.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
    private Community dummyCommunity;
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

    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance().getExternalContext();
        session = context.getSessionMap();
        int userId = (int) session.get("user");
        user = getUser(userId);
        setPortalAdmin();
        getFiles();
    }

    public User getUser(int id) {
        if (id != 0) {
            return userService.findById(id);
        }
        return null;
    }

    public Community getActualCommunity() {

        int communityId = 0;

        if (session.size() != 0 && session.get("user") != null) {
            communityId = (int) session.get("communityId");
            actualCommunity = communityService.findById(communityId);
            LOG.info("Opening Communityprofile: " + actualCommunity.getName());
        }

        if (actualCommunity == null) {
            LOG.error("no community with this id: " + communityId);
            dummyCommunity = communityService.request("Dummy Community", "A dummy community, needed for prototype", 0);
            return dummyCommunity;
        }

        if (isUserMember()) {
            joinLeaveState = "Leave";
        } else {
            joinLeaveState = "Join";
        }
        return actualCommunity;
    }

    public void joinOrLeaveCommunity() {

        if (!isUserMember()) {
            //TODO: getting exception here. Any ideas?
            communityService.join(actualCommunity, user);
        } else {
            //TODO: missing method to to leave a community e.g. communityService.leave(community, user);
        }

    }

    private boolean isUserMember() {

        int userId = (int) session.get("user");

        user = userService.findById(userId);
        List<Community> listCommunity;

        listCommunity = userService.getAllCommunitiesForUser(user);

        return listCommunity.contains(actualCommunity);
    }

    public List<Post> getActualCommunityStream() {
        getActualCommunity();
        communityPosts = activityStreamService.getPostsForCommunity(actualCommunity);
        return communityPosts;

    }

    public void deleteFile(File file) {
        LOG.info("deleteFile " + file);
        communityService.deleteFile(file);
    }

    public void uploadFile(FileUploadEvent event) {
        UploadedFile uploadedFile = event.getFile();

        try {
            communityService.uploadFile(user, uploadedFile);
        } catch (ServiceException e) {
            errorMsg = "Can't upload file without errors! - pls contact the admin or try later";
            LOG.error(errorMsg);
            setErrorMsg(errorMsg);
        }
    }

    public List<File> getFiles() {

        if (user != null && hasUserPrivilege(User.ROLE.PORTALADMIN)) {
            setFiles(communityService.getFilesFromUser(user));
            return files;
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

    private void writeObject(ObjectOutputStream stream)
            throws IOException {
        stream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
    }
}
