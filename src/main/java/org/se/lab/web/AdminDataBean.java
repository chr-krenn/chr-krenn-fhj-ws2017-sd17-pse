package org.se.lab.web;

import org.apache.log4j.Logger;
import org.se.lab.db.data.Community;
import org.se.lab.service.CommunityService;
import org.se.lab.service.ServiceException;
import org.se.lab.web.helper.RedirectHelper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sattlerb on 31/10/2017.
 */
@Named
@RequestScoped
public class AdminDataBean  implements Serializable {

    private static final long serialVersionUID = 1L;

    private final static Logger LOG = Logger.getLogger(AdminDataBean.class);
    private List<Community> requestedCommunityList;
    private List<Community> approvedCommunityList;

    private Flash flash;
    private ExternalContext context;
    private List<Community> selectedCommunities;
    private Community selectedCommunity;
    private String id = "";
    private String userProfId;
    private int userId = 0;
    private String reactionOnPendingRequest = null;

    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    
    @Inject
    private CommunityService service;

    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance().getExternalContext();

        flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        //Get UserId of user, who owns session
        Map<String, Object> session = context.getSessionMap();

        id = context.getRequestParameterMap().get("userid");

        flash.put("uid", id);

        userProfId = String.valueOf(context.getFlash().get("uid"));

        LOG.info("userProfId: " + userProfId);

        if (session.size() != 0 && session.get("user") != null) {

            userId = (int) session.get("user");
            LOG.info("SESSIOn UID: " + userId);
        } else {

            RedirectHelper.redirect("/pse/index.xhtml");
        }
        requestedCommunityList = new ArrayList<>();
        approvedCommunityList = new ArrayList<>();

        try {
            requestedCommunityList = service.getPending();
            LOG.info("Size of Requested Comm: " + requestedCommunityList.size());

            approvedCommunityList = service.getApproved();
            LOG.info("Size of Requested Comm: " + approvedCommunityList.size());
        } catch (ServiceException e) {
            String msg = "Couldn't load pending and approved communites.";
            LOG.error(msg, e);
            setErrorMsg(msg);
        }
    }

    public void declineRequestedCommunity(Community community) {
        LOG.info("Community declined >" + community);

        try {
            service.refuse(community);
            reactionOnPendingRequest = "Sucessfully declined";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(reactionOnPendingRequest));
            refreshPage();
        } catch (ServiceException e) {
            reactionOnPendingRequest = "Decline failed";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, reactionOnPendingRequest, "Please retry"));
            setErrorMsg(reactionOnPendingRequest + " - pls retry");
        }
    }

    public void approveRequestedCommunity(Community community) {
        LOG.info("Community approved > " + community);

        try {
            service.approve(community);
            refreshPage();
        } catch (ServiceException e) {
            reactionOnPendingRequest = "Approval failed";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, reactionOnPendingRequest, "Please retry"));
            setErrorMsg(reactionOnPendingRequest + " - pls retry");
        }

    }

    private void refreshPage() {

        RedirectHelper.redirect("/pse/adminPortal.xhtml");

    }

    public void goToCommunity() {
        LOG.info("In Method goToCommunity");

        if (selectedCommunity != null) {

            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("communityId", selectedCommunity.getId());

            RedirectHelper.redirect("/pse/communityprofile.xhtml");

        }
    }

    private void writeObject(ObjectOutputStream stream)
            throws IOException {
        stream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
    }

    public String getReactionOnPendingRequest() {
        return reactionOnPendingRequest;
    }

    public List<Community> getRequestedCommunityList() {
        return requestedCommunityList;
    }

    public void setRequestedCommunityList(List<Community> requestedCommunityList) {
        this.requestedCommunityList = requestedCommunityList;
    }

    public List<Community> getApprovedCommunityList() {
        return approvedCommunityList;
    }

    public void setApprovedCommunityList(List<Community> approvedCommunityList) {
        this.approvedCommunityList = approvedCommunityList;
    }

    public Community getSelectedCommunity() {
        return selectedCommunity;
    }

    public void setSelectedCommunity(Community selectedCommunity) {
        this.selectedCommunity = selectedCommunity;
    }

    public List<Community> getSelectedCommunities() {
        return selectedCommunities;
    }

    public void setSelectedCommunities(List<Community> selectedCommunities) {
        this.selectedCommunities = selectedCommunities;
    }
}
