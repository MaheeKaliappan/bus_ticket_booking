package com.zoho.busticketbooking.controller;

import java.sql.SQLException;
import java.util.Formatter;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.zoho.busticketbooking.dao.BusManagementDAO;
import com.zoho.busticketbooking.model.Bus;
import com.zoho.busticketbooking.model.User;
import com.zoho.busticketbooking.validation.Validation;

public class UserController {

	int userId;
	BusManagementDAO busManagementDAO = new BusManagementDAO();

	public void userView(Integer visitorId) throws ClassNotFoundException, SQLException {
		userId = visitorId;
		
		System.out.println("1.Ticket Booking\n2.Cancel Ticket\n3.View My Ticket\n4.Exit");
		Scanner reader = new Scanner(System.in);
		int userChoice = reader.nextInt();
		switch (userChoice) {
		case 1:
			getBusDetails();
			break;
		case 2:
			cancelTicket();
			break;
		case 3:
			fetchUserAndBusDetatilsForShowMyTicket();
			break;
		case 4:
			LoginController loginController = new  LoginController();
			loginController.loginPage();
			break;

		default:
			break;
		}

		getBusDetails();

	}

	private void cancelTicket() throws ClassNotFoundException, SQLException {
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter BusId");
		int busId = reader.nextInt();
		System.out.println("Ticket Count");
		int ticketCount = reader.nextInt();
		busManagementDAO.cancelTheTicket(userId,busId,ticketCount);
		int seatCount = busManagementDAO.fetchCurrentBusSeatCount(busId);
		busManagementDAO.increaseTheTicketCount(busId,seatCount,ticketCount);
		System.out.println("Ticket Cancelled Successfully");
		userView(userId);
		
	}

	private void fetchUserAndBusDetatilsForShowMyTicket() throws ClassNotFoundException, SQLException {
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter BusId");
		int busId = reader.nextInt();
		Bus ticketBookingDetails = busManagementDAO.fetchTicketDetailsForShowMyTicket(userId, busId);
		showTicketDetails(ticketBookingDetails);

	}

	private void showTicketDetails(Bus ticketBookingDetails) throws ClassNotFoundException, SQLException {
		System.out.println("Ticket Deatils");
		System.out.println("Travels Name              : " + ticketBookingDetails.getBusName()); 
		System.out.println("Booked Seat Count         : " + ticketBookingDetails.getBusSeatCount());
		System.out.println("Date                      : " + ticketBookingDetails.getBusDate());
		System.out.println("Timing                    : " + ticketBookingDetails.getBusTiming());
		System.out.println("Start From                : " + ticketBookingDetails.getBusStartFrom());
		System.out.println("Reach To                  : " + ticketBookingDetails.getBusReachTo());
		System.out.println("Ticket Status             : " + ticketBookingDetails.getBusTicketStatus());
		userView(userId);

	}

	private void getBusDetails() throws ClassNotFoundException, SQLException {
		Scanner reader = new Scanner(System.in);
		System.out.println("From");
		String startFrom = reader.next();
		System.out.println("To");
		String reachTo = reader.next();
		System.out.println("Date");
		String travelDate = reader.next();

		List<Bus> listOfBus = busManagementDAO.getBusdetails(startFrom, reachTo, travelDate);
		System.out.printf("%20s %20s %20s %20s %20s %20s %20s %20s", "Bus Number","Name", "Start From", "Reach To",
				"Travel Date", "Travelling Time", "Avail Seat", "Ticket Price");
		System.out.println();
		for (int index = 0; index < listOfBus.size(); index++) {
			System.out.println(
					"***********************************************************************************************************************************************************************************************");
			System.out.printf("%20s %20s %20s %20s %20s %20s %20s %20s", listOfBus.get(index).getBusId(),
					listOfBus.get(index).getBusName(), listOfBus.get(index).getBusStartFrom(),
					listOfBus.get(index).getBusReachTo(), listOfBus.get(index).getBusDate(),
					listOfBus.get(index).getBusTiming(), listOfBus.get(index).getBusSeatCount(),
					listOfBus.get(index).getBusTicketRate());
		}
		System.out.println();
		if(listOfBus.size() == 0)
		{
			System.out.println("Bus Not Avail");
			userView(userId);
		}
		else
		{
			System.out.println("Select the Bus Number");
			int busNumber = reader.nextInt();
			ticketBooking(busNumber, listOfBus);
		}
		

	}

	private void ticketBooking(int busNumber, List<Bus> listOfBus) throws ClassNotFoundException, SQLException {
		Scanner reader = new Scanner(System.in);

		Validation validation = new Validation();
		User user = new User();
		System.out.println("Enter User Name");
		String name = reader.next();
		String userMessage = "nameValidation";
		boolean userNameValidation = validation.checkUserDetails(userMessage, name);
		if (userNameValidation) {
			user.setUserName(name);
			while (userNameValidation) {
				System.out.println("Enter Your Contact Number");
				String mobileNumber = reader.next();
				userMessage = "mobileValidation";
				boolean userMobileNumberValidation = validation.checkUserDetails(userMessage, mobileNumber);
				if (userMobileNumberValidation) {
					userNameValidation = false;
					user.setUserContactNumber(mobileNumber);
					while (userMobileNumberValidation) {
						System.out.println("How Many Ticket You want Book Now??");
						user.setUserTicketCount(reader.nextInt());
						Bus fetchBusDetails = fetchBusDetails(busNumber, listOfBus);
						if (fetchBusDetails.getBusSeatCount() >= user.getUserTicketCount()) {
							user.setUserTicketAmount(fetchBusDetails.getBusTicketRate() * user.getUserTicketCount());
							user.setUserTicketBookingDate(fetchBusDetails.getBusDate());
							Random random = new Random();
							String ticketNumber = "VK";
							for (int randomNumber = 0; randomNumber < 5; randomNumber++) {
								int number = random.nextInt(9);
								ticketNumber += number;
							}

							user.setUserTicketBookingNumber(ticketNumber);
							Boolean ticketStatus = busManagementDAO.ticketBooking(userId, user.getUserTicketCount(),
									busNumber);
							if (ticketStatus) {
								busManagementDAO.decreaseTheSeatCount(user.getUserTicketCount(),busNumber,fetchBusDetails.getBusSeatCount());
								busManagementDAO.busRevenue(userId,busNumber,user.getUserTicketBookingNumber(),user.getUserTicketAmount());
								System.out.println("Ticket Booked Successfully");
								showMyTicket(user, fetchBusDetails);
							}
						} else {
							System.out.println("Avail Seat Count " + fetchBusDetails.getBusSeatCount() + " only");
						}
					}

				} else {
					System.out.println("Invalid MobileNumber Please a Valid one");
				}
			}

		}

		else {
			System.out.println("invalid name please give a valid input");
			ticketBooking(busNumber, listOfBus);
		}

	}

	private void showMyTicket(User user, Bus fetchBusDetails) throws ClassNotFoundException, SQLException {
		System.out.println("Dear " + user.getUserName());
		System.out.println("\tYour Bus Booking Start From " + fetchBusDetails.getBusStartFrom());
		System.out.println(
				"to " + fetchBusDetails.getBusReachTo() + " is confirmed with " + fetchBusDetails.getBusName());
		System.out.println("for " + fetchBusDetails.getBusDate() + " at " + fetchBusDetails.getBusTiming());
		System.out.println("Ticket No : " + user.getUserTicketBookingNumber());
		System.out.println("Passenger : " + user.getUserName());
		System.out.println("Happiieeee Journeyy :)\n ");
	
		userView(userId);
	}

	private Bus fetchBusDetails(int busNumber, List<Bus> listOfBus) {
		Bus bus = new Bus();
		for (int busNumberIndex = 0; busNumberIndex < busNumber; busNumberIndex++) {
			if (busNumber == listOfBus.get(busNumberIndex).getBusId()) {
				bus.setBusName(listOfBus.get(busNumberIndex).getBusName());
				bus.setBusDate(listOfBus.get(busNumberIndex).getBusDate());
				bus.setBusReachTo(listOfBus.get(busNumberIndex).getBusReachTo());
				bus.setBusSeatCount(listOfBus.get(busNumberIndex).getBusSeatCount());
				bus.setBusStartFrom(listOfBus.get(busNumberIndex).getBusStartFrom());
				bus.setBusTicketRate(listOfBus.get(busNumberIndex).getBusTicketRate());
				bus.setBusTiming(listOfBus.get(busNumberIndex).getBusTiming());
				return bus;
			}

		}
		return bus;

	}

}
