package dao;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import exceptions.InvalidCredentialsException;
import exceptions.UserDoesNotExistException;
import exceptions.UserNameAlreadyExistsException;
import logging.Logging;
//import logging.Logging;
import models.User;


public class UserDao {
	
	private static Connection connect() {
		Connection conn = null;

	    try {
	      String url = "jdbc:postgresql://database-1.c9tltg42c7kv.us-east-2.rds.amazonaws.com:5432/postgres?user=postgres&password=password";
	     Logging.logger.info("Connecting to Database...");
	      conn = DriverManager.getConnection(url);
	      //Logging.logger.info("Remote Connection Successful");
	      return conn;

	    } catch (SQLException e) {
	        throw new Error("Problem", e);
	      }
	    

	}
	
	public static User signUp(String firstName, String lastName, String password) {
		
		User u = new User(firstName, lastName, password);
		
		try {
			  Connection c = connect();
		    String query = "insert into users(first_name, last_name, username, password) values(?,?,?,?)";
		    PreparedStatement stmt = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			  stmt.setString(1,u.getFirstName());
			  stmt.setString(2,u.getLastName());
			  stmt.setString(3,u.getUsername());
			  stmt.setString(4,u.getPassword());

			  int id = stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return u;
	}
	
	public static User login(String username, String password){

		try {
			Connection c = connect();
			String query = "select * from users where username = ? and password = ?";

			PreparedStatement statement = c.prepareStatement(query);
			statement.setString(1, username);
			statement.setString(2, password);

			ResultSet rs = statement.executeQuery();

			if(!rs.isBeforeFirst()){
				Logging.logger.info("Login Failed: wrong username or password");
				throw new InvalidCredentialsException();
			}
			rs.next();

			return new User(rs.getString("first_name"),rs.getString("last_name"),rs.getString("username"),rs.getString("password"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int getBalance(User user) {
		try {
			Connection c = connect();
			String query = "select * from users where username = ? and password = ?";

			PreparedStatement statement = c.prepareStatement(query);
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());

			ResultSet rs = statement.executeQuery();

			if(!rs.isBeforeFirst()){
				Logging.logger.info("Get Balance Error");
				throw new Error("User data corruption");
			}
			rs.next();

			return rs.getInt("balance");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static void add(int val, User user) {
		int initialBal = getBalance(user);
		if(initialBal >=0) {
			int total = initialBal + val;

			if (total < 0 )
				throw new Error("Can't withdraw more than you have in the account");

			try {
				Connection c = connect();
				String query = "update users set balance="+ total +" where username = ? and password = ? ";

				PreparedStatement statement = c.prepareStatement(query);
				statement.setString(1, user.getUsername());
				statement.setString(2, user.getPassword());

				int rs = statement.executeUpdate();

				if(rs == 0){
					Logging.logger.info("Update Balance Error");
					throw new Error("Update Balance Failed");
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		} else {//negative dollar exception
			System.out.println("Cannot have egative dollar amount!");
			}
		}
	public static void subtract(int val, User user) {
		int initialBal = getBalance(user);
		if(initialBal >=0) {
			int total = initialBal - val;

			if (total < 0 )
				throw new Error("Can't withdraw more than you have in the account");

			try {
				Connection c = connect();
				String query = "update users set balance="+ total +" where username = ? and password = ? ";

				PreparedStatement statement = c.prepareStatement(query);
				statement.setString(1, user.getUsername());
				statement.setString(2, user.getPassword());

				int rs = statement.executeUpdate();

				if(rs == 0){
					Logging.logger.info("Update Balance Error");
					throw new Error("Update Balance Failed");
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		} else {//negative dollar exception
			System.out.println("Cannot have negative dollar amount!");
			}
		}
		
	
};
