package org.se.lab.web;

import org.apache.log4j.Logger;
import org.se.lab.db.data.Community;
import org.se.lab.service.CommunityService;
import org.se.lab.web.helper.RedirectHelper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class CommunityOverviewBean {

    private final static Logger LOG = Logger.getLogger(CommunityOverviewBean.class);

    @Inject
    private CommunityService service;

    private List<Community> communities;
    private Community selectedCommunity;

    private String newCommunityName;
    private String newCommunityDescription;
    private String visability;

    private int userId = 0;
    private ExternalContext context;

    private String errorMsg = "";

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> session = context.getSessionMap();
        userId = (int) session.get("user");

        communities = service.getApproved();
    }

    public void reset() {
        setNewCommunityDescription(null);
        setNewCommunityName(null);
        setSelectedCommunity(null);
    }

    public void createNewCommunity() {
    	LOG.info("Visability: "+ getVisability());
        Community newCommunity;
        if (!newCommunityName.isEmpty()) {
            FacesContext context = FacesContext.getCurrentInstance();

            if (newCommunityDescription.isEmpty()) {
                newCommunityDescription = "<Edit me ...>";
            }
            
        	String visability = getVisability();            
            boolean isPrivate = visability != null && visability.equals("private");
            
            try {
                newCommunity = service.request(newCommunityName, newCommunityDescription, userId, isPrivate);
                context.getExternalContext().getSessionMap().put("communityId", newCommunity.getId());
                LOG.info(newCommunity.getName() + " community created with the id: " + newCommunity.getId() + ".");
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Community requested."));
            } catch (Exception e) {	// only catching ServiceException would miss EJBException
            	LOG.info("Exception in createNewCommunity()", e);
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fail!", "Unable to request community."));
            }
        }
        reset();
    }

    public String getVisability() {
		return visability;
	}

	public void setVisability(String visability) {
		this.visability = visability;
	}

	public void gotoCom() {
        LOG.info("In Method gotoCom");
        if (selectedCommunity != null) {
            LOG.info("Selected Community: " + selectedCommunity.getId() + " " + selectedCommunity.getDescription());

            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("communityId", selectedCommunity.getId());

            RedirectHelper.redirect("/pse/communityprofile.xhtml");

        }
    }

    public void deleteCom() {
        LOG.info("In Method deleteCom");

        if (selectedCommunity != null) {

            LOG.info("Selected Community: " + selectedCommunity.getId() + " " + selectedCommunity.getDescription());

            try {
                service.delete(selectedCommunity);
            } catch (IllegalArgumentException e) {
                String msg = "Illegal Argument Error on deleting community " + selectedCommunity;
                LOG.error(e);
                setErrorMsg(msg);
            } catch (Exception e) {
                String msg = "Error while deleting community " + selectedCommunity;
                LOG.error(e);
                setErrorMsg(msg);
            }
            RedirectHelper.redirect("/pse/communityoverview.xhtml");
        }
    }

    public List<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(List<Community> communities) {
        this.communities = communities;
    }

    public Community getSelectedCommunity() {
        return selectedCommunity;
    }

    public void setSelectedCommunity(Community selectedCommunity) {
        this.selectedCommunity = selectedCommunity;
    }

    public String getNewCommunityName() {
        return newCommunityName;
    }

    public void setNewCommunityName(String newCommunityName) {
        this.newCommunityName = newCommunityName;
    }

    public String getNewCommunityDescription() {
        return newCommunityDescription;
    }

    public void setNewCommunityDescription(String newCommunityDescription) {
        this.newCommunityDescription = newCommunityDescription;
    }
}
