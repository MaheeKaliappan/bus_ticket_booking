package com.zoho.busticketbooking.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.zoho.busticketbooking.dao.BusManagementDAO;
import com.zoho.busticketbooking.model.Bus;

public class AdminController {
	BusManagementDAO busManagementDAO = new BusManagementDAO();

	public void adminView() throws ClassNotFoundException, SQLException {
		System.out.println("1.Add Bus\n2.Update Bus\n3.Delete Bus\n4.View Bus Revenue\n5.Exit\nWhich one you want? ");
		Scanner reader = new Scanner(System.in);
		int adminChoice = reader.nextInt();
		switch (adminChoice) {
		case 1:
			addBusDetails();
			break;
		case 2:
			updateBusDetails();
			break;
		case 3:
			deleteBus();
			break;
		case 4:
			viewBusRevenueDetails();
			break;
		case 5:
			LoginController loginController = new  LoginController();
			loginController.loginPage();
			break;

		default:
			System.out.println("Invalid Input please Give a Valid Input");
			adminView();
			break;

		}

	}

	private void viewBusRevenueDetails() throws ClassNotFoundException, SQLException {
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter Bus Id");
		int busId = reader.nextInt();
		List<Bus> revenueDetails = busManagementDAO.fetchBusRevenueDetails(busId);
		showRevenueDetails(revenueDetails);
	}

	private void showRevenueDetails(List<Bus> revenueDetails) throws ClassNotFoundException, SQLException {
		System.out.printf("%20s %20s %20s", "Ticket Number", "Ticket Status", "Bus Revenue");
		System.out.println();
		for (int index = 0; index < revenueDetails.size(); index++) {
			System.out.println(
					"************************************************************************");

			System.out.printf("%20s %20s %20s", revenueDetails.get(index).getBusTiming(),
					revenueDetails.get(index).getBusName(), revenueDetails.get(index).getBusSeatCount());
			System.out.println();
		}
		
		adminView();

	}

	private void deleteBus() throws ClassNotFoundException, SQLException {
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter Bus Id");
		int busId = reader.nextInt();
		int result = busManagementDAO.deleteTheBus(busId);
		if (result == 1) {
			System.out.println("Bus Deletedd Successfully");
			adminView();
		}

	}

	private void updateBusDetails() throws ClassNotFoundException, SQLException {
		Scanner reader = new Scanner(System.in);
		Bus bus = new Bus();
		System.out.println("Enter Bus Id");
		bus.setBusId(reader.nextInt());
		System.out.println("Enter a Bus Name");
		bus.setBusName(reader.next());
		System.out.println("Enter Bus Start From");
		bus.setBusStartFrom(reader.next());
		System.out.println("Bus Reach To");
		bus.setBusReachTo(reader.next());
		System.out.println("Seat Count");
		bus.setBusSeatCount(reader.nextInt());
		System.out.println("Ticket Rate");
		bus.setBusTicketRate(reader.nextInt());
		System.out.println("Bus Timing");
		bus.setBusTiming(reader.next());
		System.out.println("Bus Date");
		bus.setBusDate(reader.next());
		busManagementDAO.updateBusDetails(bus);
		System.out.println("Bus Updated  Successfully");
		adminView();


	}

	private void addBusDetails() throws ClassNotFoundException, SQLException {
		Scanner reader = new Scanner(System.in);
		Bus bus = new Bus();
		System.out.println("Enter a Bus Name");
		bus.setBusName(reader.next());
		System.out.println("Enter Bus Start From");
		bus.setBusStartFrom(reader.next());
		System.out.println("Bus Reach To");
		bus.setBusReachTo(reader.next());
		System.out.println("Seat Count");
		bus.setBusSeatCount(reader.nextInt());
		System.out.println("Ticket Rate");
		bus.setBusTicketRate(reader.nextInt());
		System.out.println("Bus Timing");
		bus.setBusTiming(reader.next());
		System.out.println("Bus Date");
		bus.setBusDate(reader.next());
		busManagementDAO.addBusDetails(bus);
		System.out.println("Bus Added Successfully");
		adminView();

	}
}
