package org.se.lab.db.dao;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.se.lab.db.data.Community;
import org.se.lab.db.data.Enumeration;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CommunityDAOImpl extends DAOImplTemplate<Community> implements CommunityDAO {

    private final static Logger LOG = Logger.getLogger(CommunityDAOImpl.class);

    @Override
    public Community findByName(String name) {
        LOG.info("findByName(name = " + name + ")");
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Community> criteria = builder.createQuery(Community.class);
        Root<Community> community = criteria.from(Community.class);
        criteria.where(builder.equal(community.get("name"), name));
        TypedQuery<Community> query = em.createQuery(criteria);

        return query.getSingleResult();
    }

    @Override
    public List<Community> findCommunitiesByState(Enumeration.State state) {
        LOG.info(String.format("findCommunites with State %s", state.getName()));
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Community> criteria = builder.createQuery(Community.class);
        Root<Community> community = criteria.from(Community.class);
        criteria.where(builder.equal(community.get("state"), new Enumeration(state.getValue())));
        TypedQuery<Community> query = em.createQuery(criteria);

        List<Community> coms = query.getResultList();
        for (Community c : coms) {
            initializeCom(c);
        }
        return coms;

    }

    @Override
    public Community createCommunity(String name, String description, int portaladminId) {
        return createCommunity(name, description, portaladminId, false);
    }
    
    @Override
    public Community createCommunity(String name, String description, int portaladminId, boolean isPrivate) {
        LOG.info("createCommunity(name = " + name + ", description = " + description + ")");
        Community c = new Community(name, description, portaladminId, isPrivate);
        Enumeration e = getValidEnumeration(em.find(Enumeration.class, 1));
        c.setState(e);
        return insert(c);
    }

    private Enumeration getValidEnumeration(Enumeration find) {
        if (find != null)
            return find;
        EnumerationDAOImpl edao = new EnumerationDAOImpl();
        edao.setEntityManager(em);
        return edao.insert(edao.createEnumeration(1));
    }

    private Community initializeCom(Community c) {
        if (c == null) return c;
        Hibernate.initialize(c.getState());
        Hibernate.initialize(c.getUsers());
        return c;
    }


    @Override
    public Community insert(Community entity) {
        LOG.debug("insert(" + entity + ")");
        return super.insert(entity);
    }

    @Override
    public Community update(Community entity) {
        LOG.debug("update(" + entity + ")");
        return super.update(entity);
    }

    @Override
    public void delete(Community entity) {
        LOG.debug("delete(" + entity + ")");
        super.delete(entity);
    }

    @Override
    protected Class<Community> getEntityClass() {
        return Community.class;
    }

    @Override
    public Community findById(int id) {
        LOG.info("findById(int " + id + ")");
        return super.findById(id);
    }

    @Override
    public List<Community> findAll() {
        LOG.info("findAll()");
        return super.findAll();
    }
}
