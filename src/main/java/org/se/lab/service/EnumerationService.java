package org.se.lab.service;

import org.se.lab.db.data.Enumeration;

public interface EnumerationService {
    Enumeration findById(int id);

    Enumeration getPending();

    Enumeration getApproved();

    Enumeration getRefused();
}
