package org.se.lab.service.dao;

import org.se.lab.data.Community;
import org.se.lab.data.DatabaseException;

import java.util.List;

public interface CommunityDAO extends DAOTemplate<Community> {
	
	/**
	 * findByName Method to find an specific community by the name. 
	 * Hibernate CriteriaBuilder is used to get a single result.
	 * @param name the name of the community which you try to find
	 * @return returns the community if found else it returns null
	 */
	Community findByName(String name);
	
	/**
	 * findPendigCommunities Method to find all communities are not approved 
	 * consequently private and can only be seen by (Portal-)admin.
	 * @return retuns a list of communities which are in state 'pending'
	 */
	List<Community> findPendingCommunities();

	/**
	 * findApprovedCommunities find all approved and published communities.
	 * @return returns a list of communities which are already published
	 */
	List<Community> findApprovedCommunities();

	/**
	 * create a new Community. Only with this method there is a guarantee that the 
	 * community is correctly created and stored in database.
	 * @param name the name of the new community
	 * @param description a small text what about the community is
	 * @return returned the new created and also stored community for further useage
	 * @throws DatabaseException 
	 */
	Community createCommunity(String name, String description, int portaladminId) throws DatabaseException;

}
