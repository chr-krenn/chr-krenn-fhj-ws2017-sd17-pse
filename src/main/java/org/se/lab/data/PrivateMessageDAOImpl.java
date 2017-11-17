package org.se.lab.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

public class PrivateMessageDAOImpl implements PrivateMessageDAO
{
	
	private final Logger LOG = Logger.getLogger(UserProfileDAOImpl.class);

    @PersistenceContext
    private EntityManager em;
    
    public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public PrivateMessage insert(PrivateMessage privatemessage) {
		LOG.info("insert(" + privatemessage + ")");
        em.persist(privatemessage);
        return privatemessage;
	}

	@Override
	public void delete(PrivateMessage privatemessage) {
		LOG.info("delete(" + privatemessage + ")");
        em.remove(privatemessage);
	}

	@Override
	public List<PrivateMessage> findAll() {
		LOG.info("findAll()");
        final String hql = "SELECT pm FROM " + PrivateMessage.class.getName() + " AS pm";
        return em.createQuery(hql).getResultList();
	}

	@Override
	public PrivateMessage findById(int id) {
		LOG.info("findById(" + id + ")");
        return em.find(PrivateMessage.class, id);
	}
}
