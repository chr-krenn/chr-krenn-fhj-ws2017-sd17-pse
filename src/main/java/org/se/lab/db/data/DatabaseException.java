package org.se.lab.db.data;

import java.sql.SQLException;

public class DatabaseException extends SQLException {
	private static final long serialVersionUID = 1L;

	public DatabaseException(String message) {
		super(message);
	}

	public DatabaseException(String message, Throwable e) {
		super(message, e);
	}
}
