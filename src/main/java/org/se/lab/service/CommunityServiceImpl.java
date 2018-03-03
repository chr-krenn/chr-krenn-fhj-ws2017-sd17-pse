package org.se.lab.service;

import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;
import org.se.lab.db.data.*;
import org.se.lab.utils.ArgumentChecker;
import org.se.lab.db.dao.CommunityDAO;
import org.se.lab.db.dao.FileDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class CommunityServiceImpl implements CommunityService {
    private final static Logger LOG = Logger.getLogger(CommunityServiceImpl.class);

    @Inject
    private EnumerationService enumerationService;

    @Inject
    private CommunityDAO communityDAO;

    @Inject
    private FileDAO fileDAO;

    @Inject
    private PrivateMessageService pmService;

    @Inject
    private UserService userServcie;

    @Override
    public List<Community> findAll() {
        try {
            return communityDAO.findAll();
        } catch (IllegalArgumentException e) {
            String msg = "Can't find all coms (illegal Argument)";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't find all communities";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
    }

    @Override
    public List<Community> getApproved() {
        LOG.debug("getApproved Communities ");
        try {
            return communityDAO.findCommunitiesByState(Enumeration.State.APPROVED);
        } catch (IllegalArgumentException e) {
            String msg = "Can't find ApprovedCommunities(llegal Argument)";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't find approved communites";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
    }

    @Override
    public List<Community> getPending() {
        LOG.debug("getPending Communities");
        try {
            return communityDAO.findCommunitiesByState(Enumeration.State.PENDING);
        } catch (IllegalArgumentException e) {
            String msg = "Can't find PendingCommunities(illegal Argument)";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't find PendingCommunities";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
    }

    @Override
    public void delete(Community community) {
        LOG.debug("delete " + community);

        try {
        	Enumeration state = enumerationService.findById(8);
        	community.setState(state);
        	communityDAO.update(community);
        } catch (IllegalArgumentException e) {
            String msg = "Can't delete Post - illegal Argument";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't delete community " + community;
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
    }

    @Override
    public void update(Community community) {
        LOG.debug("update " + community);
        try {
            communityDAO.update(community);
        } catch (IllegalArgumentException e) {
            String msg = "Can't update Community(illegal Argument)";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't update community " + community;
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
    }

    @Override
    public void join(Community community, User user) {
        LOG.debug("adding " + user + " to " + community);

        if (community != null && user != null) {
            try {
                community.addUsers(user);
                update(community);
            } catch (IllegalArgumentException e) {
                String msg = "Can't join User (illegal Agrument)";
                LOG.error(msg, e);
                throw new ServiceException(msg);
            } catch (Exception e) {
                String msg = "Can't join user " + user + " to community " + community;
                LOG.error(msg, e);
                throw new ServiceException(msg);
            }

        } else {
            LOG.error("Can't join user " + user + " to community " + community);
            throw new ServiceException("Can't join user " + user + " to community " + community);
        }
    }
    
    @Override
    public void leave(Community community, User user) {
        LOG.debug("adding " + user + " to " + community);

        if (community != null && user != null) {
            try {
                community.removeUsers(user);
                update(community);
            } catch (IllegalArgumentException e) {
                String msg = "Can't leave User (illegal Agrument)";
                LOG.error(msg, e);
                throw new ServiceException(msg);
            } catch (Exception e) {
                String msg = "Can't leave user " + user + " from community " + community;
                LOG.error(msg, e);
                throw new ServiceException(msg);
            }

        } else {
            LOG.error("Can't join user " + user + " to community " + community);
            throw new ServiceException("Can't join user " + user + " to community " + community);
        }
    }
    
    @Override
    public Community request(String name, String description, int portalAdmin) {
    	return request(name, description, portalAdmin, false);
    }
    
    @Override
    public Community request(String name, String description, int portalAdmin, boolean isPrivate) {
        LOG.debug("request community with name: " + name + " and description: " + description);
        Community com;
        try {
            com = communityDAO.createCommunity(name, description, portalAdmin, isPrivate);

            ArgumentChecker.assertNotNull(com, "com");

            notifyAdmins(com);
        } catch (ServiceException e) {
            String msg = "Unable to notify admins";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (IllegalArgumentException e) {
            String msg = "Can't insert community(illegal Argument)";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't insert community " + name;
            LOG.error(msg, e);
            throw new ServiceException(msg);
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
            } catch (IllegalArgumentException e) {
                String msg = "Can't approve Community(illegal Argument)";
                LOG.error(msg, e);
                throw new ServiceException(msg);
            } catch (Exception e) {
                String msg = "Can't approve community " + community.getName() + "; State: " + community.getState();
                LOG.error(msg, e);
                throw new ServiceException(msg);
            }
        } else {
            String msg = "Can't approve community " + community.getName() + "; State: " + community.getState();
            LOG.error(msg);
            throw new ServiceException(msg);
        }
    }

    @Override
    public Community findById(int id) {
        LOG.debug("findById " + id);

        try {
            return communityDAO.findById(id);
        } catch (IllegalArgumentException e) {
            String msg = "Can't find Community(illegal Argument)";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't find id " + id;
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
    }

    @Override
    public void refuse(Community community) {
        LOG.debug("refuse " + community);
        if (community.getState().equals(enumerationService.getPending())) {
            try {
                community.setState(enumerationService.getRefused());
                update(community);
            } catch (IllegalArgumentException e) {
                String msg = "Can't refuse Community(illegal Argument)";
                LOG.error(msg, e);
                throw new ServiceException(msg);
            } catch (Exception e) {
                String msg = "Can't refuse community " + community.getName() + "; State: " + community.getState();
                LOG.error(msg, e);
                throw new ServiceException(msg);
            }
        } else {
            String msg = "Can't refuse community " + community.getName() + "; State: " + community.getState();
            LOG.error(msg);
            throw new ServiceException(msg);
        }
    }

    public Community findByName(String name) {
        Community com;
        try {
            com = communityDAO.findByName(name);
        } catch (IllegalArgumentException e) {
            String msg = "Can't get Community by Name (illegal Argument)";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't find community " + name;
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
        return com;

    }

    private void notifyAdmins(Community com) {

        try {
            User u = userServcie.findById(com.getPortaladminId());

            for (User user : userServcie.getAdmins()) {
                PrivateMessage message = new PrivateMessage(u.getUsername() + " created new community '"+com.getName()+"'", user, user);
                pmService.sendMessage(message);
            }
        } catch (IllegalArgumentException e) {
            String msg = "Coulnd't find user(wrong argument)";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Unable to retrieve user by id " + com.getPortaladminId();
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
    }

	@Override
	public void uploadFile(Community community, UploadedFile uploadedFile) {
		
        ArgumentChecker.assertNotNull(community, "community");
        ArgumentChecker.assertNotNull(uploadedFile, "uploadedFile");
        
        try {
            fileDAO.insert(new File(community, uploadedFile.getFileName(), uploadedFile.getContents()));
            LOG.info(String.format("File %s stored in Database", uploadedFile.getFileName()));
        } catch (IllegalArgumentException e) {
            String msg = "Can't upload file (illegal Argument)";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't upload file " + uploadedFile.getFileName();
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
	}

	@Override
	public List<File> getFilesFromCommunity(Community community) {
        List<File> files = new ArrayList<File>();

        if (community != null) {
            try {
                files = fileDAO.findByCommunity(community);
            } catch (IllegalArgumentException e) {
                String msg = "Can't get files for Community (illegal Argument)";
                LOG.error(msg, e);
                throw new ServiceException(msg);
            } catch (Exception e) {
                String msg = "Can't get file from Community " + community.getId();
                LOG.error(msg, e);
                throw new ServiceException(msg);
            }
        }
        
        return files;
	}
	
    @Override
    public void uploadFile(User user, UploadedFile uploadedFile) {

        ArgumentChecker.assertNotNull(user, "user");
        ArgumentChecker.assertNotNull(uploadedFile, "uploadedFile");

        try {
            fileDAO.insert(new File(user, uploadedFile.getFileName(), uploadedFile.getContents()));
            LOG.info(String.format("File %s stored in Database", uploadedFile.getFileName()));
        } catch (IllegalArgumentException e) {
            String msg = "Can't upload file (illegal Argument)";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't upload file " + uploadedFile.getFileName();
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
    }

    @Override
    public List<File> getFilesFromUser(User user) {
        List<File> files = new ArrayList<File>();

        if (user != null) {
            try {
                files = fileDAO.findByUser(user);
            } catch (IllegalArgumentException e) {
                String msg = "Can't get files for User(illegal Argument)";
                LOG.error(msg, e);
                throw new ServiceException(msg);
            } catch (Exception e) {
                String msg = "Can't get file from User " + user.getId();
                LOG.error(msg, e);
                throw new ServiceException(msg);
            }
        }
        
        return files;
    }

    @Override
    public void deleteFile(File file) {

        ArgumentChecker.assertNotNull(file, "file");

        try {
            fileDAO.delete(file);
        } catch (IllegalArgumentException e) {
            String msg = "Can't delete file - illegal Argument";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't delete file " + file.getFilename();
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
    }
    
 

}