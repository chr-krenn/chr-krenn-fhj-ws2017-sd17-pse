package org.se.lab.service;

import org.apache.log4j.Logger;
import org.se.lab.data.Community;
import org.se.lab.data.CommunityDAO;
import org.se.lab.data.Enumeration;
import org.se.lab.data.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class CommunityService {
    public static final Enumeration PENDING = new Enumeration(1);
    public static final Enumeration APPROVE = new Enumeration(2);
    private final Logger LOG = Logger.getLogger(CommunityService.class);

    @Inject
    private CommunityDAO dao;

    public List<Community> findAll() {
        try {
            return dao.findAll();
        } catch (Exception e) {
            LOG.error("Error during findAll Communities", e);
            throw new ServiceException("Error during findAll Communities");
        }
    }

    public List<Community> getApproved() {
        LOG.debug("getApproved Communities ");

        try {
            return dao.findApprovedCommunities();
        } catch (Exception e) {
            LOG.error("Can't findApprovedCommunities", e);
            throw new ServiceException("Can't findApprovedCommunities");
        }
    }

    public List<Community> getPending() {
        LOG.debug("getPending Communities");

        try {
            return dao.findPendingCommunities();
        } catch (Exception e) {
            LOG.error("Can't findPendingCommunities", e);
            throw new ServiceException("Can't findPendingCommunities");
        }
    }

    public void delete(Community community) {
        LOG.debug("delete " + community);

        try {
            dao.delete(community);
        } catch (Exception e) {
            LOG.error("Can't delete community " + community, e);
            throw new ServiceException("Can't delete community " + community);
        }
    }

    public void update(Community community) {
        LOG.debug("update " + community);

        try {
            dao.update(community);
        } catch (Exception e) {
            LOG.error("Can't update community " + community, e);
            throw new ServiceException("Can't update community " + community);
        }
    }

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

    public void request(Community community) {
        LOG.debug("request " + community);
        community.setState(PENDING);

        try {
            dao.insert(community);
        } catch (Exception e) {
            LOG.error("Can't insert community " + community, e);
            throw new ServiceException("Can't insert community " + community);
        }
    }

    public void approve(Community community) {
        LOG.debug("approve " + community);
        community.setState(APPROVE);

        update(community);
    }

    public Community findById(int id) {
        LOG.debug("findById " + id);

        try {
            return dao.findById(id);
        } catch (Exception e) {
            LOG.error("Can`t find Id " + id, e);
            throw new ServiceException("Can`t find Id " + id);
        }


    }
}
