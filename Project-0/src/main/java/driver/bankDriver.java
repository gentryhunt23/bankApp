package driver;

import java.util.List;
import java.util.Scanner;

import dao.UserDao;
import exceptions.InvalidCredentialsException;
import models.Transactions;
import models.User;

public class bankDriver {
	
	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		
		//This will be used to control our loop
		boolean done = false;
		UserDao dao = new UserDao();
		
		User u = null;
		
		while(!done) {
			if(u == null) {
				System.out.println("Welcome to The Extremely Small Bank of America! \nLogin or Signup? Press 1 to Login, Press 2 to Signup");
				int choice = Integer.parseInt(in.nextLine());
				if(choice == 1) {
					System.out.print("Please enter your username: ");
					String username = in.nextLine();
					System.out.print("Please enter your password: ");
					String password = in.nextLine();
					u = UserDao.login(username, password);
					if(u == null) {
							System.out.println("Username or password was incorrect. Goodbye");
							done = true;
					}
					else {
						System.out.println("Welcome " + u.getFirstName());
					}
				} else {
					System.out.print("Please enter you first name: ");
					String first = in.nextLine();
					System.out.println("Please enter your last name: ");
					String last = in.nextLine();
					System.out.println("Please enter a password: ");
					String password = in.nextLine();


					try {
						u = UserDao.signUp(first, last, password);
						System.out.println("You may now log in with the username: " + u.getUsername());
					} catch (Exception e) {
						System.out.println("Sorry, we could not process your request");
						System.out.println("Please try again later");
						done = true;
					}
				}
		}
			else {
				//if (u.admin == true) {
					System.out.println("To view balance press 1, to deposit(+) or withdraw(-) an amount press 2");

				//} else {
					//System.out.println("To view posts press 1, to create a post press 2");
					int choice = Integer.parseInt(in.nextLine());
					//If the user chooses 1, we will show them the list of posts
					if (choice == 1) {
						System.out.println("$ " + UserDao.getBalance(u));
						System.out.println("Are you finished? Press 1 for yes, press 2 for no");
						choice = Integer.parseInt(in.nextLine());
						done = (choice == 1) ? true : false;
					} else {
						System.out.println("Please enter the amount you would like to add to your account");
						String content = in.nextLine();
						UserDao.add(Integer.parseInt(content), u);
						System.out.println("Balance updated! Would you like to make another transaction? Press 1 for no, press 2 for yes");
						choice = Integer.parseInt(in.nextLine());
						done = (choice == 1) ? true : false;
				//	}
				}
			}
		}
		
		System.out.println("Goodbye :)");
		in.close();
	}
	
};
