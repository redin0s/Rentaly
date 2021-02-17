package com.folders.rentaly.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("dbSource")
public class DBSource {
	private String url;
	private String username;
	private String password;
	
	public DBSource(
	@Value("${datasource.url}") final String url,
	@Value("${datasource.username}") final String username,
	@Value("${datasource.password}") final String password) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;

	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
}
