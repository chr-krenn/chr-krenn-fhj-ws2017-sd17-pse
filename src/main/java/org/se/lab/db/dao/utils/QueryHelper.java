package org.se.lab.db.dao.utils;

import javax.persistence.TypedQuery;
import java.util.List;

public class QueryHelper {

    public static <T> T getSingleResultOrNull(TypedQuery<T> query) {
        query.setMaxResults(1);
        List<T> list = query.getResultList();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
