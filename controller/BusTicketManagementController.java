package com.zoho.busticketbooking.controller;

import java.sql.SQLException;

public class BusTicketManagementController {

	public static void main(String[] args) {
		LoginController loginController = new  LoginController();
		try {
			
			loginController.loginPage();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
