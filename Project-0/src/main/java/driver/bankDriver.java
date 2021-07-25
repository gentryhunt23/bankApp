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

		// This will be used to control our loop
		boolean done = false;
		UserDao dao = new UserDao();

		User u = null;

		System.out.println(
				"Welcome to The Extremely Small Bank of America! \nLogin or Signup? Press 1 to Login, Press 2 to Signup");
		int choice = Integer.parseInt(in.nextLine());
		while (!done) {
			switch (choice) {
			case 1:
				// sign-in functionality
				System.out.print("Please enter your username: ");
				String username = in.nextLine();
				System.out.print("Please enter your password: ");
				String password = in.nextLine();
				u = UserDao.login(username, password);
				if (u == null) {
					System.out.println("Username or password was incorrect. Goodbye");
					// done = true;
				} else {
					System.out.println("Welcome " + u.getFirstName());
					try {

						do {
							System.out.println("What would you like to do next (select the corresponding number)?\n"
									+ "1. View Balance \n" + "2. Deposit \n" + "3. Withdraw \n" + "4. Transfer Funds \n"
									+ "5. Logout");
							choice = Integer.parseInt(in.nextLine());

							switch (choice) {
							case 1:
								System.out.println("$ " + UserDao.getBalance(u));
								System.out.println("Are you done?\n" + " 1. YES \n 2. NO");
								if (Integer.parseInt(in.nextLine()) == 1) {
									done = true;
									System.out.println("Thank you have a great day!");
									break;
								} else {
									done = false;
									break;
								}
							case 2:
								System.out.println("Please enter the amount you would like to add to your account");
								String content = in.nextLine();
								UserDao.add(Integer.parseInt(content), u);
								System.out.println("Balance Updated! \n Are you done?\n" + " 1. YES \n 2. NO");
								if (Integer.parseInt(in.nextLine()) == 1) {
									done = true;
									System.out.println("Thank you have a great day!");
									break;
								} else {
									done = false;
									break;
								}
							case 3:
								System.out.println("Please enter the amount you would like withdraw from your account");
								content = in.nextLine();
								UserDao.subtract(Integer.parseInt(content), u);
								System.out.println("Balance Updated! \n Are you done?\n" + " 1. YES \n 2. NO");
								if (Integer.parseInt(in.nextLine()) == 1) {
									done = true;
									System.out.println("Thank you have a great day!");
									break;
								} else {
									done = false;
									break;
								}
							case 4:
							case 5:
								System.out.println("Thank you have a nice day!");

							}
						} while (!done && choice != 5);

					} catch (Exception e) {
						System.out.println("Sorry, we could not process your request");
						System.out.println("Please try again later");
						done = true;
						break;
					}

				}
				break;
			case 2:
				// sign-up functionality
				System.out.print("Please enter you first name: ");
				String first = in.nextLine();
				System.out.println("Please enter your last name: ");
				String last = in.nextLine();
				System.out.println("Please create a password: ");
				password = in.nextLine();

				try {
					u = UserDao.signUp(first, last, password);
					System.out.println("You may now log in with the username: " + u.getUsername());
					UserDao.login(u.getUsername(), password);
					System.out.println("You are now logged in as " + u.getUsername() + ".\n\n");

					do {
						System.out.println("What would you like to do next (select the corresponding number)?\n"
								+ "1. View Balance \n" + "2. Deposit \n" + "3. Withdraw \n" + "4. Wire Transfer \n"
								+ "5. Logout");
						choice = Integer.parseInt(in.nextLine());

						switch (choice) {
						case 1:
							System.out.println("$ " + UserDao.getBalance(u));
							System.out.println("Are you done?\n" + " 1. YES \n 2. NO");
							if (Integer.parseInt(in.nextLine()) == 1) {
								done = true;
								System.out.println("Thank you have a great day!");
								break;
							} else {
								done = false;
								break;
							}
						case 2:
							System.out.println("Please enter the amount you would like to add to your account");
							String content = in.nextLine();
							UserDao.add(Integer.parseInt(content), u);
							System.out.println("Are you done?\n" + " 1. YES \n 2. NO");
							if (Integer.parseInt(in.nextLine()) == 1) {
								done = true;
								System.out.println("Thank you have a great day!");
								break;
							} else {
								done = false;
								break;
							}
						case 3:
							System.out.println("Please enter the amount you would like withdraw from your account");
							content = in.nextLine();
							UserDao.subtract(Integer.parseInt(content), u);
							System.out.println("Balance Updated! \n Are you done?\n" + " 1. YES \n 2. NO");
							if (Integer.parseInt(in.nextLine()) == 1) {
								done = true;
								System.out.println("Thank you have a great day!");
								break;
							} else {
								done = false;
								break;
							}
						case 4:
							System.out.println("How much money would you like to transfer?");
							String value = in.nextLine();
							System.out.println("Enter the username of the recipient");
							username = in.nextLine();
							UserDao.transfer(Integer.parseInt(value), username, u);
							System.out.println("$ " + UserDao.getBalance(u));
							System.out.println("Are you done?\n" + " 1. YES \n 2. NO");
			
							if (Integer.parseInt(in.nextLine()) == 1) {
								done = true;
								System.out.println("Thank you have a great day!");
								break;
							} else {
								done = false;
								break;
							}
							

						case 5:
							System.out.println("Thank you have a nice day!");
							done = true;
						}
					} while (!done && choice != 5);

				} catch (Exception e) {
					System.out.println("Sorry, we could not process your request");
					System.out.println("Please try again later");
					done = true;
					break;
				}
			}
			;

		}
	}
}
