package org.se.lab.service;

import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;
import org.se.lab.db.data.Community;
import org.se.lab.db.data.File;
import org.se.lab.db.data.PrivateMessage;
import org.se.lab.db.data.User;
import org.se.lab.utils.ArgumentChecker;
import org.se.lab.db.dao.CommunityDAO;
import org.se.lab.db.dao.FileDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class CommunityServiceImpl implements CommunityService {
    private final Logger LOG = Logger.getLogger(CommunityServiceImpl.class);

    @Inject
    private EnumerationService enumerationService;

    @Inject
    private CommunityDAO communityDAO;

    @Inject
    private FileDao fileDao;

    @Inject
    private PrivateMessageService pmService;

    @Inject
    private UserService userServcie;

    @Override
    public List<Community> findAll() {
        try {
            return communityDAO.findAll();
        } catch (Exception e) {
            LOG.error("Error during findAll Communities", e);
            throw new ServiceException("Error during findAll Communities");
        }
    }

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

    @Override
    public void join(Community community, User user) {
        LOG.debug("adding " + user + " to " + community);

        if (community != null && user != null) {
            try {
                community.addUsers(user);
                update(community);
            } catch (Exception e) {
                LOG.error("Can't join user " + user + " to community " + community, e);
                throw new ServiceException("Can't join user " + user + " to community " + community, e);
            }

        } else {
            LOG.error("Can't join user " + user + " to community " + community);
            throw new ServiceException("Can't join user " + user + " to community " + community);
        }
    }

    @Override
    public Community request(String name, String description, int portalAdmin) {
        LOG.debug("request community with name: " + name + " and description: " + description);
        Community com;
        try {
            com = communityDAO.createCommunity(name, description, portalAdmin);
            
            ArgumentChecker.assertNotNull(com, "com");

            notifyAdmins(com);

        } catch (Exception e) {
            LOG.error("Can't insert community " + name, e);
            throw new ServiceException("Can't insert community " + name);
        }
        return com;
    }

    @Override
    public void approve(Community community) {
        LOG.debug("approve " + community);

        if (community.getState().equals(enumerationService.getPending())) {
            try {
                community.setState(enumerationService.getApproved());
                update(community);
            } catch (Exception e) {
                LOG.warn("Can`t approve community " + community.getName() + "; Community is in State: " +
                    community.getState(), e);
                throw new ServiceException("Can`t approve community " + community.getName() +
                    "; Community is in State: " + community.getState());
            }
        } else {
            LOG.warn("Can`t approve community " + community.getName() + "; Community is in State: " +
                community.getState());
            throw new ServiceException("Can`t approve community " + community.getName() + "; Community is in State: " +
                community.getState());
        }
    }

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

    @Override
    public void refuse(Community community) {
        LOG.debug("refuse " + community);
        if (community.getState().equals(enumerationService.getPending())) {
            try {
                community.setState(enumerationService.getRefused());
                update(community);
            } catch (Exception e) {
                LOG.warn("Can`t refuse community " + community.getName() + "; Community is in State: " +
                    community.getState() + ": " + e.toString(), e);
                throw new ServiceException("Can`t refuse community " + community.getName() + "; Community is in State: " +
                    community.getState());
            }
        } else {
            LOG.warn("Can`t refuse community " + community.getName() + "; Community is in State: " +
                community.getState());
            throw new ServiceException("Can`t refuse community " + community.getName() + "; Community is in State: " +
                community.getState());
        }
    }

    public Community findByName(String name) {
        Community com;
        try {
            com = communityDAO.findByName(name);
        } catch (Exception e) {
            LOG.error("Can`t find community " + name, e);
            throw new ServiceException("Can`t find community " + name);
        }
        return com;

    }

    private void notifyAdmins(Community com) {

        User u = userServcie.findById(com.getPortaladminId());

        for (User user: userServcie.getAdmins()) {
            PrivateMessage message = new PrivateMessage(u + " created new community", user, user);
            pmService.sendMessage(message);
        }
    }

    @Override
    public void uploadFile(User user, UploadedFile uploadedFile) {


        ArgumentChecker.assertNotNull(user, "user");

        ArgumentChecker.assertNotNull(uploadedFile, "uploadedFile");

        LOG.info(String.format("File %s stored in Database", uploadedFile.getFileName()));
        try {
            fileDao.insert(new File(user, uploadedFile.getFileName(), uploadedFile.getContents()));
        } catch (Exception e) {
            LOG.error("Can`t upload file " + uploadedFile.getFileName(), e);
            throw new ServiceException("Can`t upload file  " + uploadedFile.getFileName());
        }

    }

    @Override
    public List<File> getFilesFromUser(User user) {
        List<File> files = new ArrayList<File>();

        if (user != null) {
            try {
                files = fileDao.findByUser(user);
            } catch (Exception e) {
                LOG.error("Can`t get file from user" + user.getId(), e);
                throw new ServiceException("Can`t get file from user" + user.getId());
            }


        }
        return files;
    }

    @Override
    public void deleteFile(File file) {
    
        ArgumentChecker.assertNotNull(file, "file");
        
        try {
            fileDao.delete(file);
        } catch (Exception e) {
            LOG.error("Can`t delete file " + file.getFilename(), e);
            throw new ServiceException("Can`t delete file " + file.getFilename());
        }
    }
}