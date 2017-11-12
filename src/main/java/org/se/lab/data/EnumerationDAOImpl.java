package org.se.lab.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

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
	public List<Enumeration> findAll() {
		LOG.info("findAll()");
        final String hql = "SELECT e FROM " + Enumeration.class.getName() + " AS e";
        return em.createQuery(hql, Enumeration.class).getResultList();		
	}

	@Override
	public Enumeration findById(int id) {
		LOG.info("findById(" + id + ")");       
        return em.find(Enumeration.class, id);
	}
	
	@Override
	public Enumeration createEnumeration(int id) {
		Enumeration e = new Enumeration();
		/*
		 * pending
		 * approved
		 * refused
		 * admin
		 * portaladmin
		 * user
		 * like
		 */
		switch (id) {
		case 1:
			e.setId(id);
			e.setName("OPEN");
			break;
		case 2:
			e.setId(id);
			e.setName("CLOSED");
			break;
		case 3:
			e.setId(id);
			e.setName("BLOCKED");
			break;
		case 4:
			e.setId(id);
			e.setName("INSPECTION");
			break;
		case 5:
			e.setId(id);
			e.setName("VERIFICATION");
			break;
		case 6:
			e.setId(id);
			e.setName("ADMIN");
			break;
		case 7: 
			e.setId(id);
			e.setName("PORTALADMIN");
			break;
		case 8:
			e.setId(id);
			e.setName("USER");
			break;
		case 9:
			e.setId(id);
			e.setName("LIKE");
			break;
		default:
			throw new IllegalArgumentException();
		}
		insert(e);
		return e;
	}
}
