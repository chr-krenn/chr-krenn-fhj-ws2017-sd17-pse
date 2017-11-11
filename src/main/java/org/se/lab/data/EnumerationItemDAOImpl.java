package org.se.lab.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

public class EnumerationItemDAOImpl implements EnumerationItemDAO {

	private final Logger LOG = Logger.getLogger(EnumerationItemDAOImpl.class);

	@PersistenceContext
	private EntityManager em;
	
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public EnumerationItem insert(EnumerationItem enumerationItem) {
		em.persist(enumerationItem);
		return enumerationItem;
	}

	@Override
	public EnumerationItem update(EnumerationItem enumerationItem) {
		return em.merge(enumerationItem);
	}

	@Override
	public void delete(EnumerationItem enumerationItem) {
		em.remove(enumerationItem);
	}

	@Override
	public List<Enumeration> findEnumerationByUser(User user) {
		final String hql = "SELECT e FROM " + EnumerationItem.class.getName() + " AS e WHERE e.user_id = " + user.getId();
		
		LOG.info(hql);
		
        List<EnumerationItem> enumerationItems = em.createQuery(hql, EnumerationItem.class).getResultList();	
        return getEnumerations(enumerationItems);
	}

	@Override
	public List<Enumeration> findEnumerationByPost(Post post) {
		final String hql = "SELECT e FROM " + EnumerationItem.class.getName() + " AS e WHERE e.post_id = " + post.getId();
		
		LOG.info(hql);
		
        List<EnumerationItem> enumerationItems = em.createQuery(hql, EnumerationItem.class).getResultList();	
        return getEnumerations(enumerationItems);
	}

	@Override
	public List<Enumeration> findEnumerationByCommunity(Community community) {
		final String hql = "SELECT e FROM " + EnumerationItem.class.getName() + " AS e WHERE e.community_id = " + community.getId();
		
		LOG.info(hql);
		
        List<EnumerationItem> enumerationItems = em.createQuery(hql, EnumerationItem.class).getResultList();	
        return getEnumerations(enumerationItems);
	}
	
	private List<Enumeration> getEnumerations(List<EnumerationItem> enumerationItems) {
		List<Enumeration> enumerations = new ArrayList<Enumeration>();
		
		for (Iterator<EnumerationItem> i = enumerationItems.iterator(); i.hasNext();) {
			EnumerationItem enumerationItem = i.next();
			
			if(enumerationItem.getEnumeration() == null)
				continue;
			
			enumerations.add(enumerationItem.getEnumeration());
		}
		
		return enumerations;
	}

}
