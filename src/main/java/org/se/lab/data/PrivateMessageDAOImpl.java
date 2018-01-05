package org.se.lab.data;

import java.util.List;

import org.apache.log4j.Logger;
import org.se.lab.service.dao.PrivateMessageDAO;

public class PrivateMessageDAOImpl extends DAOImplTemplate<PrivateMessage> implements PrivateMessageDAO
{
	
	private final Logger LOG = Logger.getLogger(UserProfileDAOImpl.class);
    

	@Override
	public PrivateMessage insert(PrivateMessage privatemessage) {
		LOG.info("insert(" + privatemessage + ")");
        return super.insert(privatemessage);
	}

	@Override
	public void delete(PrivateMessage privatemessage) {
		LOG.info("delete(" + privatemessage + ")");
        super.delete(privatemessage);
	}

	@Override
	public List<PrivateMessage> findAll() {
		LOG.info("findAll()");
        return super.findAll();
	}

	@Override
	public PrivateMessage findById(int id) {
		LOG.info("findById(" + id + ")");
        return super.findById(id);
	}

	@Override
	protected Class<PrivateMessage> getEntityClass() {
		return PrivateMessage.class;
	}
}
