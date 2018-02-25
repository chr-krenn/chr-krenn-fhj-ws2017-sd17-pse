package org.se.lab.web;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.se.lab.db.data.Community;
import org.se.lab.db.data.User;
import org.se.lab.db.data.UserProfile;
import org.se.lab.service.ServiceException;
import org.se.lab.service.UserService;
import org.se.lab.web.helper.RedirectHelper;
import org.se.lab.web.helper.Session;
import org.se.lab.web.helper.SessionHelper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named
@SessionScoped
public class UserProfileImageBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private final static Logger LOG = Logger.getLogger(UserProfileImageBean.class);

    private User user;
    private String errorMsg = "";

    @Inject
    private UserService service;

    @Inject
    private Session session;

    @PostConstruct
    public void init() {
        user = session.getUser();

    }


    public StreamedContent getImage() {
        DefaultStreamedContent content = null;
        try {
            content = new DefaultStreamedContent(new ByteArrayInputStream(user.getUserProfile().getPicture()));
        } catch (Exception e) {
            LOG.error(String.format("Exception during picture processing"), e);
        }
        return content;
    }

    public void uploadPicture(FileUploadEvent event) {

        UploadedFile uploadedFile = event.getFile();
        UserProfile userProfile = user.getUserProfile();
        userProfile.setPicture(uploadedFile.getContents());
        try {
            service.addPictureToProfile(userProfile);
            RedirectHelper.redirect("/pse/profile.xhtml");
        } catch (ServiceException e) {
            errorMsg = "Fehler beim Hochladen eines Bildes";
            LOG.error(errorMsg);
            setErrorMsg(errorMsg);
        }
    }

    public boolean isImageExists() {
        return user != null && user.getUserProfile().getPicture() != null;
    }

    private void writeObject(ObjectOutputStream stream)
            throws IOException {
        stream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}