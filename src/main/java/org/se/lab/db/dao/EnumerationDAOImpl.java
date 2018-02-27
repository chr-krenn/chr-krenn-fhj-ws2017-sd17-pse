package org.se.lab.db.dao;

import org.apache.log4j.Logger;
import org.se.lab.db.data.*;

import java.util.List;

public class EnumerationDAOImpl extends DAOImplTemplate<Enumeration> implements EnumerationDAO {

    private final static Logger LOG = Logger.getLogger(EnumerationDAOImpl.class);

    @Override
    public Enumeration insert(Enumeration enumeration) {
        LOG.info("insert(" + enumeration + ")");
        return super.insert(enumeration);
    }

    @Override
    public Enumeration update(Enumeration enumeration) {
        LOG.info("update(" + enumeration + ")");
        return super.update(enumeration);
    }

    @Override
    public void delete(Enumeration enumeration) {
        LOG.info("delete(" + enumeration + ")");
        super.delete(enumeration);
    }

    @Override
    public Enumeration createEnumeration(int id) {

        String name;
        switch (id) {
            case 1:
                name = "PENDING";
                break;
            case 2:
                name = "APPROVED";
                break;
            case 3:
                name = "REFUSED";
                break;
            case 4:
                name = "ADMIN";
                break;
            case 5:
                name = "PORTALADMIN";
                break;
            case 6:
                name = "USER";
                break;
            case 7:
                name = "LIKE";
                break;
            case 8:
            	name = "ARCHIVED";
            	break;
            default:
                throw new IllegalArgumentException("unknown id for new Enumeration! id = " + id);
        }
        Enumeration e = new Enumeration(name);
        insert(e);
        return e;
    }

    @Override
    public Enumeration findById(int id) {
        LOG.info("findById(" + id + ")");
        return super.findById(id);
    }

    @Override
    public List<Enumeration> findAll() {
        LOG.info("findAll()");
        return super.findAll();
    }

    @Override
    public List<User> findUsersByEnumeration(int id) {
        return findById(id).getUser();
    }

    @Override
    public List<Community> findCommunitiesByEnumeration(int id) {
        return findById(id).getCom();
    }

    @Override
    public List<Post> findLikedPostsByEnumeration(int id) {
        return findById(id).getLikedPosts();
    }

    @Override
    public List<User> findLikedUsersByEnumeration(int id) {
        return findById(id).getLikedBy();
    }

    @Override
    protected Class<Enumeration> getEntityClass() {
        return Enumeration.class;
    }

}
