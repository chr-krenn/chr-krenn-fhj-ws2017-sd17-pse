package org.se.lab.service;

import org.apache.log4j.Logger;
import org.se.lab.data.Community;
import org.se.lab.data.CommunityDAO;
import org.se.lab.data.EnumerationItem;
import org.se.lab.data.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class CommunityService {
    public static final EnumerationItem PENDING = new EnumerationItem(1);
    public static final EnumerationItem APPROVE = new EnumerationItem(2);
    private final Logger LOG = Logger.getLogger(CommunityService.class);

    @Inject
    private CommunityDAO dao;

    public List<Community> findAll() {
        try {
            return dao.findAll();
        } catch (Exception e) {
            throw new ServiceException("Error during findAll Communities", e);
        }
    }

    public List<Community> getApproved() {
        return findAll().stream().filter(line -> APPROVE.equals(line.getState())).collect(Collectors.toList());
    }

    public List<Community> getPending() {
        return findAll().stream().filter(line -> PENDING.equals(line.getState())).collect(Collectors.toList());
    }

    public void delete(Community community) {
        LOG.debug("delete " + community);

        try {
            dao.delete(community);
        } catch (Exception e) {
            LOG.error("Can't delete community " + community);
            throw new ServiceException("Can't delete community " + community, e);
        }
    }

    public void update(Community community) {
        LOG.debug("update " + community);

        try {
            dao.update(community);
        } catch (Exception e) {
            LOG.error("Can't update community " + community);
            throw new ServiceException("Can't update community " + community, e);
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
            LOG.error("Can't insert community " + community);
            throw new ServiceException("Can't insert community " + community, e);
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
            LOG.error("Can`t find Id " + id);
            throw new ServiceException("Can`t find Id " + id, e);
        }


    }
}
