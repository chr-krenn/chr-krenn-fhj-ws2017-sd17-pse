package org.se.lab.service;

import org.apache.log4j.Logger;
import org.se.lab.data.Community;
import org.se.lab.data.CommunityDAO;
import org.se.lab.data.Enumeration;
import org.se.lab.data.EnumerationDAO;
import org.se.lab.data.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class CommunityServiceImpl implements CommunityService {
    private final Logger LOG = Logger.getLogger(CommunityServiceImpl.class);

    private final int PENDING = 1;
    private final int APPROVED = 2;
    private final int REFUSED = 3;
    
    @Inject
    private CommunityDAO communityDAO;
    
    @Inject
    private EnumerationDAO enumerationDAO;

    /* (non-Javadoc)
	 * @see org.se.lab.service.CommunityService#findAll()
	 */
    @Override
	public List<Community> findAll() {
        try {
            return communityDAO.findAll();
        } catch (Exception e) {
            LOG.error("Error during findAll Communities", e);
            throw new ServiceException("Error during findAll Communities");
        }
    }

    /* (non-Javadoc)
	 * @see org.se.lab.service.CommunityService#getApproved()
	 */
    @Override
	public List<Community> getApproved() {
        LOG.debug("getApproved Communities ");

        try {
            return communityDAO.findApprovedCommunities();
        } catch (Exception e) {
            LOG.error("Can't findApprovedCommunities", e);
            throw new ServiceException("Can't findApprovedCommunities");
        }
    }

    /* (non-Javadoc)
	 * @see org.se.lab.service.CommunityService#getPending()
	 */
    @Override
	public List<Community> getPending() {
        LOG.debug("getPending Communities");

        try {
            return communityDAO.findPendingCommunities();
        } catch (Exception e) {
            LOG.error("Can't findPendingCommunities", e);
            throw new ServiceException("Can't findPendingCommunities");
        }
    }

    /* (non-Javadoc)
	 * @see org.se.lab.service.CommunityService#delete(org.se.lab.data.Community)
	 */
    @Override
	public void delete(Community community) {
        LOG.debug("delete " + community);

        try {
            communityDAO.delete(community);
        } catch (Exception e) {
            LOG.error("Can't delete community " + community, e);
            throw new ServiceException("Can't delete community " + community);
        }
    }

    /* (non-Javadoc)
	 * @see org.se.lab.service.CommunityService#update(org.se.lab.data.Community)
	 */
    @Override
	public void update(Community community) {
        LOG.debug("update " + community);

        try {
            communityDAO.update(community);
        } catch (Exception e) {
            LOG.error("Can't update community " + community, e);
            throw new ServiceException("Can't update community " + community);
        }
    }

    /* (non-Javadoc)
	 * @see org.se.lab.service.CommunityService#join(org.se.lab.data.Community, org.se.lab.data.User)
	 */
    @Override
	public void join(Community community, User user) {
        LOG.debug("adding " + user + " to " + community);

        if (community != null && user != null) {
            community.addUsers(user);
            update(community);
        } else {
            LOG.error("Can't join user " + user + " to community " + community);
            throw new ServiceException("Can't join user " + user + " to community " + community);
        }
    }

    /* (non-Javadoc)
	 * @see org.se.lab.service.CommunityService#request(org.se.lab.data.Community)
	 */
    @Override
	public void request(Community community) {
        LOG.debug("request " + community);
        community.setState(getStateForID(PENDING));

        try {
            communityDAO.insert(community);
        } catch (Exception e) {
            LOG.error("Can't insert community " + community, e);
            throw new ServiceException("Can't insert community " + community);
        }
    }

    /* (non-Javadoc)
	 * @see org.se.lab.service.CommunityService#approve(org.se.lab.data.Community)
	 */
    @Override
	public void approve(Community community) {
        LOG.debug("approve " + community);
        community.setState(getStateForID(APPROVED));

        update(community);
    }

    /* (non-Javadoc)
	 * @see org.se.lab.service.CommunityService#findById(int)
	 */
    @Override
	public Community findById(int id) {
        LOG.debug("findById " + id);

        try {
            return communityDAO.findById(id);
        } catch (Exception e) {
            LOG.error("Can`t find Id " + id, e);
            throw new ServiceException("Can`t find Id " + id);
        }
    }

    /* (non-Javadoc)
	 * @see org.se.lab.service.CommunityService#refuse(org.se.lab.data.Community)
	 */
    @Override
	public void refuse(Community community){
        LOG.debug("refuse " + community);
        if(community.getState().equals(getStateForID(PENDING))){
            community.setState(getStateForID(REFUSED));
            update(community);
        }else {
            LOG.error("Can`t refuse community " + community.getName());
            throw new ServiceException("Can`t refuse community " + community.getName());
        }
    }
    
    private Enumeration getStateForID(int id) {
        try {
            return enumerationDAO.findById(id);
        } catch (Exception e) {
            LOG.error("Can`t find Id " + id, e);
            throw new ServiceException("Can`t find Id " + id);
        }
    }
}
