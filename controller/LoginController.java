package com.zoho.busticketbooking.controller;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.zoho.busticketbooking.dao.BusManagementDAO;
import com.zoho.busticketbooking.validation.Validation;

public class LoginController {
	BusManagementDAO busManagementDAO = new BusManagementDAO();

	public void loginPage() throws ClassNotFoundException, SQLException {
		Scanner reader = new Scanner(System.in);
		System.out.println("Bus Ticket Booking");
		System.out.println("1.LogIn\n2.SignUp\n3.Exit");
		int userChoice = 0;
		try {
			userChoice = reader.nextInt();
		} catch (InputMismatchException inputMismatchException) {
			System.out.println("InValid Input please enter valid Input");
			loginPage();
		}

		if (userChoice == 1) {
			userLoginPage();
		} else if (userChoice == 2) {
			userSignUpPage();
		}else if (userChoice == 3) {
			System.out.println("Thanks For Using Our Service...Visit Again  :)");
		} else {
			System.out.println("Invalid Input");
			loginPage();
		}

	}

	private void userLoginPage() throws ClassNotFoundException, SQLException {
		
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter Ur Name");
		String visterName = reader.next();
		System.out.println("Enter Ur Password");
		String visitorPassword = reader.next();
		
		Integer visitorId = loginValidation(visterName, visitorPassword);
		if(visitorId == 1)
		{
			AdminController adminController = new AdminController();
			adminController.adminView();
		}
		else
		{
			UserController userController = new UserController();
			userController.userView(visitorId);
		}

	}

	private void userSignUpPage() throws ClassNotFoundException, SQLException {
		Validation validation = new Validation();
		Scanner reader = new Scanner(System.in);
		System.out.println("\nCharcters Allowed only a Alphabet &  Length Should be a 6 to 15");
		System.out.println("\nEnter Ur Name");
		String visterName = reader.next();

		String userMessage = "nameValidation";
		boolean nameValidationResult = validation.checkUserDetails(userMessage, visterName);
		if (nameValidationResult) {
			while (nameValidationResult) {
				System.out.println("\nPlease Note");
				System.out.println(
						"\nPassword must Contain\nDigit,\nLower case alphabet,\nUpper case alphabet,\nSpecial character,\nWhite spaces don’t allowed length should be at least 8 characters and at most 20 characters.");
				System.out.println("\nEnter Ur Password");
				String visitorPassword = reader.next();
				userMessage = "passwordValidation";
				boolean passwordValidationResult = validation.checkUserDetails(userMessage, visitorPassword);
				if (passwordValidationResult) {
					busManagementDAO.insertUser(visterName, visitorPassword);
					//nameValidationResult = false;
					Integer result = busManagementDAO.fetchTheDetails(visterName,visitorPassword);
					UserController userController = new UserController();
					userController.userView(result);
					
				}
				System.out.println("Invalid Password please given a valid password");

			}

		} else {
			System.out.println("Invalid UserName please given a valid UserName");

			userSignUpPage();
		}

	}

	private Integer loginValidation(String visterName, String visitorPassword)
			throws ClassNotFoundException, SQLException {

		Integer result = busManagementDAO.fetchTheDetails(visterName, visitorPassword);
		if (result == null) {
			System.out.println("Invalid User Name & Password Please Enter Valid Input");
			userLoginPage();
		}
		
		return result;

	}

}
