package org.se.lab.service;

import org.se.lab.data.Enumeration;

public interface EnumerationService {
	Enumeration findById(int id);

	Enumeration getPending();
	Enumeration getApproved();
	Enumeration getRefused();
}
