package org.se.lab.db.dao;

import org.se.lab.db.data.Community;
import org.se.lab.db.data.Enumeration;

import java.util.List;

public interface CommunityDAO extends DAOTemplate<Community> {

    Community findByName(String name);

    List<Community> findCommunitiesByState(Enumeration.State state);

    Community createCommunity(String name, String description, int portaladminId);
    Community createCommunity(String name, String description, int portaladminId, boolean isPrivate);
}
