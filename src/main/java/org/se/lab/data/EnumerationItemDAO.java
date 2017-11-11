package org.se.lab.data;

import java.util.List;

public interface EnumerationItemDAO {
	EnumerationItem insert(EnumerationItem enumerationItem);
	EnumerationItem update(EnumerationItem enumerationItem);
	void delete(EnumerationItem enumerationItem);
	
	List<Enumeration> findEnumerationByUser(User user);
	List<Enumeration> findEnumerationByPost(Post post);
	List<Enumeration> findEnumerationByCommunity(Community community);
}
