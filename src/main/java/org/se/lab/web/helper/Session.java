package org.se.lab.web.helper;

import java.io.IOException;
import java.util.Map;

import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.se.lab.db.data.User;
import org.se.lab.service.UserService;

@Stateless
public class Session {
	public final static String USER = "user";

	private final Logger LOG = Logger.getLogger(Session.class);

	@Inject
	private UserService service;

	public User getUser() {
		final ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		final Map<String, Object> session = context.getSessionMap();
		final Object user = session.get(USER);

		if (user != null) {
			try {
				int userId = (int) user;
				return service.findById(userId);
			} catch (ClassCastException e) {
				LOG.error("User in session not well formatted.");
				redirectLogin(context);
			}
		}else{
			context.invalidateSession();
			redirectLogin(context);
		}
		
		return null;
	}

	public void setUser() {

	}
	
	private void redirectLogin(ExternalContext context) {
		try {
			context.redirect("/pse/login.xhtml");
		} catch (IOException e) {
			LOG.error("Can't redirect to /pse/login.xhtml");
		}
	}
}
