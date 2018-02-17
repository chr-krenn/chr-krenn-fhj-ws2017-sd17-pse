package org.se.lab.service;

import org.apache.log4j.Logger;
import org.se.lab.db.dao.EnumerationDAO;
import org.se.lab.db.data.Enumeration;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class EnumerationServiceImpl implements EnumerationService {
    private final static Logger LOG = Logger.getLogger(EnumerationServiceImpl.class);

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
        return findById(Enumeration.State.PENDING.getValue());
    }

    @Override
    public Enumeration getApproved() {
        return findById(Enumeration.State.APPROVED.getValue());
    }

    @Override
    public Enumeration getRefused() {
        return findById(Enumeration.State.REFUSED.getValue());
    }
}
