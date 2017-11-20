package org.se.lab.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.se.lab.service.dao.EnumerationDAO;

public class EnumerationDAOImpl implements EnumerationDAO {

	private final Logger LOG = Logger.getLogger(EnumerationDAOImpl.class);

	@PersistenceContext
	private EntityManager em;
	
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public Enumeration insert(Enumeration enumeration) {
		em.persist(enumeration);
		return enumeration;
	}

	@Override
	public Enumeration update(Enumeration enumeration) {
		return em.merge(enumeration);
	}

	@Override
	public void delete(Enumeration enumeration) {
		em.remove(enumeration);
	}
	
	@Override
	public Enumeration createEnumeration(int id) {
		Enumeration e = new Enumeration();
		
		switch (id) {
			case 1:
				e.setName("PENDING");
				break;
			case 2:
				e.setName("APPROVED");
				break;
			case 3:
				e.setName("REFUSED");
				break;
			case 4:
				e.setName("ADMIN");
				break;
			case 5:
				e.setName("PORTALADMIN");
				break;
			case 6:
				e.setName("USER");
				break;
			case 7: 
				e.setName("LIKE");
				break;
			default:
				throw new IllegalArgumentException();
		}
		
		insert(e);
		
		return e;
	}
	
	@Override
	public Enumeration findById(int id) {
		LOG.info("findById(" + id + ")");       
        return em.find(Enumeration.class, id);
	}

	@Override
	public List<Enumeration> findAll() {
		LOG.info("findAll()");
        final String hql = "SELECT e FROM " + Enumeration.class.getName() + " AS e";
        return em.createQuery(hql, Enumeration.class).getResultList();		
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
}
