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

	@Override
	public PrivateMessage insert(PrivateMessage privateMessage) {
		LOG.info("insert(" + privateMessage + ")");
        em.persist(privateMessage);
        return privateMessage;
	}


	@Override
	public void deleteMessage(PrivateMessage privateMessage) {
		LOG.info("delete(" + privateMessage + ")");
        em.remove(privateMessage);
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
