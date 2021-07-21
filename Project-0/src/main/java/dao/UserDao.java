package dao;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exceptions.InvalidCredentialsException;
import exceptions.UserDoesNotExistException;
import exceptions.UserNameAlreadyExistsException;
import logging.Logging;
import models.User;


public class UserDao {
	
	private static Connection connect() {
		Connection conn = null;

	    try {
	      String url = "database-1.c9tltg42c7kv.us-east-2.rds.amazonaws.com";
	      conn = DriverManager.getConnection(url);
	      return conn;

	    } catch (SQLException e) {
	        throw new Error("Problem", e);
	      }
	    

	}
	
	public static User signUp(String firstName, String lastName, String password) {
		
		User u = new User(firstName, lastName, password);
		
		try {
			
			Connection c = connect();
		    String query = "insert into users(first_name, last_name, username, password) values"
		    		+ "(" + u.getFirstName() + "," + u.getLastName() + "," + u.getUsername() + "," + u.getPassword()+")";
		    Statement stmt = null;
		    stmt = c.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return u;
	}
	
	public static User login(String username, String password){		

		try {
			Connection c = connect();
		    String query = "select username from users where password = " + password;
		    Statement stmt = null;
		    stmt = c.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
			Logging.logger.info("User: " + username + "was logged in");
	        
			//What if the user does not exist?
			Logging.logger.warn("User tried logging in that does not exist");
			throw new UserDoesNotExistException();
			
			//What if the user has invalid credentials
			//Logging.logger.warn("User tried logging in with invalid credentials");
			//throw new InvalidCredentialsException();

		}
//		} catch (FileNotFoundException e) {
//		} 
			catch(Exception e) {
			e.printStackTrace();
		}
		return null;

	}
};
