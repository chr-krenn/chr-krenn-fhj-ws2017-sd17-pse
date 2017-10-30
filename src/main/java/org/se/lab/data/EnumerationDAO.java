package org.se.lab.data;

import java.util.List;

public interface EnumerationDAO {
	Enumeration insert(Enumeration enumeration);
	Enumeration update(Enumeration enumeration);
	void delete(Enumeration enumeration);
	
	List<Enumeration> read();
	Enumeration read(int id);
}
