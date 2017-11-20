package org.se.lab.service.impl;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.se.lab.data.Enumeration;
import org.se.lab.service.EnumerationService;
import org.se.lab.service.ServiceException;
import org.se.lab.service.dao.EnumerationDAO;

public class EnumerationServiceImpl implements EnumerationService {
    private final Logger LOG = Logger.getLogger(EnumerationServiceImpl.class);
    
    private final int PENDING = 1;
    private final int APPROVED = 2;
    private final int REFUSED = 3;
    
    @Inject
    private EnumerationDAO enumerationDAO;
    
	@Override
	public Enumeration findById(int id) {
        try {
            return enumerationDAO.findById(id);
        } catch (Exception e) {
            LOG.error("Can`t find Id " + id, e);
            throw new ServiceException("Can`t find Id " + id);
        }
	}

	@Override
	public Enumeration getPending() {
		return findById(PENDING);
	}

	@Override
	public Enumeration getApproved() {
		return findById(APPROVED); 
	}

	@Override
	public Enumeration getRefused() {
		return findById(REFUSED);
	}
}
