package org.se.lab.web;

import org.apache.log4j.Logger;
import org.se.lab.data.Community;
import org.se.lab.service.CommunityService;
import org.se.lab.service.ServiceException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sattlerb on 31/10/2017.
 */
@Named
@RequestScoped
@ViewScoped
@ManagedBean(name = "AdminDataBean")
public class AdminDataBean implements Serializable {

    private final Logger LOG = Logger.getLogger(AdminDataBean.class);
    List<Community> requestedCommunityList;
    List<Community> approvedCommunityList;
    /*
     * Properties for Session
     */
    Flash flash;
    FacesContext context;
    List<Community> selectedCommunities;
    Community selectedCommunity;
    private String id = "";
    private int userId = 0;
    private String reactionOnPendingRequest = null;
    @Inject
    private CommunityService service;

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

    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance();

		/*
         * FG Info Flash: We need flash to make the param survive one redirect request
		 * otherwise param will be null
		 */
        flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();

		/*
         * Holen der UserId vom User welcher aktuell eingeloggt ist(Session)
		 *
		 */

        Map<String, Object> session = context.getExternalContext().getSessionMap();


        id = context.getExternalContext().getRequestParameterMap().get("userid");

        flash.put("uid", id);


        String userProfId = String.valueOf(context.getExternalContext().getFlash().get("uid"));


        LOG.info("userProfId: " + userProfId);

        if (session.size() != 0 && session.get("user") != null) {

            userId = (int) session.get("user");
            LOG.info("SESSIOn UID: " + userId);
        } else {
            /*
             * If session is null - redirect to login page!
			 *
			 */
            try {
                context.getExternalContext().redirect("/pse/login.xhtml");
            } catch (IOException e) {
                LOG.error("Can't redirect to /pse/login.xhtml");
                //e.printStackTrace();
            }
        }
        requestedCommunityList = new ArrayList<>();
        approvedCommunityList = new ArrayList<>();

        requestedCommunityList = service.getPending();
        LOG.info("Size of Requested Comm: " + requestedCommunityList.size());

        approvedCommunityList = service.getApproved();
        LOG.info("Size of Requested Comm: " + approvedCommunityList.size());
    }

    public void declineRequestedCommunity(Community community) {
        LOG.info("Community declined >" + community);

        //TODO evaluate why catch block isn't entered
        try {
            service.refuse(community);
            reactionOnPendingRequest = "Sucessfully declined";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(reactionOnPendingRequest));
            refreshPage();
        } catch (ServiceException e) {
            reactionOnPendingRequest = "Decline failed";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, reactionOnPendingRequest, "Please retry"));

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

        }

    }

    private void refreshPage() {
        try {
            context.getExternalContext().redirect("/pse/adminPortal.xhtml");
        } catch (IOException e) {
            LOG.error("Can't redirect to /pse/adminPortal.xhtml");
            //e.printStackTrace();
        }
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

    public void goToCommunity() {
        LOG.info("In Method goToCommunity");

        if (selectedCommunity != null) {

            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("communityId", selectedCommunity.getId());

            try {
                context.getExternalContext().redirect("/pse/communityprofile.xhtml");
            } catch (IOException e) {
                LOG.error("Can't redirect to /pse/communityprofile.xhtml");
            }
        }
    }

}
