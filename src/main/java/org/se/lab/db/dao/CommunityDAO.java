package org.se.lab.db.dao;

import org.se.lab.db.data.Community;
import org.se.lab.db.data.DatabaseException;

import java.util.List;

public interface CommunityDAO extends DAOTemplate<Community> {

	Community findByName(String name);
	List<Community> findPendingCommunities();
	List<Community> findApprovedCommunities();
	Community createCommunity(String name, String description, int portaladminId) throws DatabaseException;

}
