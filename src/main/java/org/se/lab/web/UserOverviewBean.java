
package org.se.lab.web;

import org.apache.log4j.Logger;
import org.se.lab.data.User;
import org.se.lab.data.UserProfile;
import org.se.lab.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class UserOverviewBean {

	private final Logger LOG = Logger.getLogger(UserOverviewBean.class);

	@Inject
	UserService service;

	private List<UserProfile> profiles;
	private UserProfile selectedProfile;

	@PostConstruct
	public void init() {

		// DummyData
		profiles = new ArrayList<UserProfile>();
		User user1 = new User(1, "User1", "***");
		User user2 = new User(2, "User2", "***");
		UserProfile p1 = new UserProfile( "Max", "Muster", "max@muster.at", "0316-555", "0664/1234567", "ExistingUser");
		UserProfile p2 = new UserProfile( "Heinz", "Fischer", "heinz@fischer.at", "0316-555", "0664/1234567", "NewUser");		
		user1.setUserProfile(profiles.get(0));
		user2.setUserProfile(profiles.get(1));
		profiles.add(p1);
		profiles.add(p2);

		// Activate if DAO works
		// profiles = service.getAllUserProfiles();
		
		
	}

	public List<UserProfile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<UserProfile> profiles) {
		this.profiles = profiles;
	}

	public UserProfile getSelectedProfile() {
		return selectedProfile;
	}

	public void setSelectedProfile(UserProfile selectedProfile) {
		this.selectedProfile = selectedProfile;
	}

}
