package org.se.lab.web;

import org.apache.log4j.Logger;
import org.se.lab.data.Community;
import org.se.lab.service.CommunityService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sattlerb on 31/10/2017.
 */
@Named
@RequestScoped
public class AdminDataBean {

	private final Logger LOG = Logger.getLogger(AdminDataBean.class);
	
    List<Community> requestedCommunityList;

    /*
     * Properties for Session
     */
    Flash flash;
    FacesContext context;
    private String id = "";
    private int userId = 0;

    @Inject
    private CommunityService service;

    @PostConstruct
    public void init()
    {
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


        String userProfId = (String) context.getExternalContext().getFlash().get("uid");


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

        //DummyData instead of findPendingCommunities()
        requestedCommunityList = service.getPending();
//        Community com1 = new Community("Billing", "Billing Community");
//        Community com2 = new Community("ProMana", "Projectmanagement Community");
//        requestedCommunityList.add(com1);
//        requestedCommunityList.add(com2);

    }

    private void declineRequestedCommunity()
    {
        LOG.info("Community declined");
        //throw new NotImplementedException();
    }

    private void approveRequestedCommunity()
    {
        LOG.info("Community approved");
        //throw new NotImplementedException();
    }

}
