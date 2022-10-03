package com.zoho.busticketbooking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.zoho.busticketbooking.model.Bus;
import com.zoho.busticketbooking.model.User;

public class BusManagementDAO {
	static Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;

	private void getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/busmanagement?characterEncoding=latin1&useConfigs=maxPerformance", "root",
				"root");
	}

	public Integer fetchTheDetails(String visitorName, String visitorPassword)
			throws SQLException, ClassNotFoundException {
		getConnection();
		Integer userId = null;
		String sqlQuery = "SELECT ID FROM USER WHERE USER_NAME= ? AND USER_PASSWORD=?";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setString(1, visitorName);
		preparedStatement.setString(2, visitorPassword);

		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			userId = resultSet.getInt(1);

		}
		return userId;
	}

	public void insertUser(String visitorName, String visitorPassword) throws SQLException, ClassNotFoundException {
		getConnection();
		String sqlQuery = "INSERT INTO USER (USER_NAME,USER_PASSWORD) VALUES(?,?)";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setString(1, visitorName);
		preparedStatement.setString(2, visitorPassword);
		preparedStatement.executeUpdate();

	}

	public List<Bus> getBusdetails(String startFrom, String reachTo, String travelDate)
			throws ClassNotFoundException, SQLException {
		getConnection();
		List<Bus> listOfBus = new ArrayList<Bus>();
		String sqlQuery = "SELECT ID ,BUS_NAME,BUS_START_FROM,BUS_REACH_TO,BUS_SEAT_COUNT,BUS_TICKET_RATE,BUS_TIMING,BUS_DATE FROM BUS WHERE BUS_START_FROM = ? AND BUS_REACH_TO = ? AND BUS_DATE=? ";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setString(1, startFrom);
		preparedStatement.setString(2, reachTo);
		preparedStatement.setString(3, travelDate);

		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Bus bus = new Bus();
			bus.setBusId(resultSet.getInt(1));
			bus.setBusName(resultSet.getString(2));
			bus.setBusStartFrom(resultSet.getString(3));
			bus.setBusReachTo(resultSet.getString(4));
			bus.setBusSeatCount(resultSet.getInt(5));
			bus.setBusTicketRate(resultSet.getInt(6));
			bus.setBusTiming(resultSet.getString(7));
			bus.setBusDate(resultSet.getString(8));
			listOfBus.add(bus);
		}
		return listOfBus;

	}

	public void addBusDetails(Bus bus) throws ClassNotFoundException, SQLException {
		getConnection();
		String sqlQuery = "INSERT INTO BUS (bus_name,bus_start_from,bus_reach_to,bus_seat_count,bus_ticket_rate,bus_timing,bus_date )VALUES (?,?,?,?,?,?,?)";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setString(1, bus.getBusName());
		preparedStatement.setString(2, bus.getBusStartFrom());
		preparedStatement.setString(3, bus.getBusReachTo());
		preparedStatement.setInt(4, bus.getBusSeatCount());
		preparedStatement.setInt(5, bus.getBusTicketRate());
		preparedStatement.setString(6, bus.getBusTiming());
		preparedStatement.setString(7, bus.getBusDate());

		preparedStatement.executeUpdate();

	}

	public Boolean ticketBooking(int userId, Integer ticketCount, int busNumber)
			throws ClassNotFoundException, SQLException {
		getConnection();
		String sqlQuery = "INSERT INTO TICKET_BOOKING (USER_ID,BUS_ID,TICKET_COUNT)VALUES(?,?,?)";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setInt(1, userId);
		preparedStatement.setInt(2, busNumber);
		preparedStatement.setInt(3, ticketCount);
		int result = preparedStatement.executeUpdate();
		if (result == 1) {
			return true;
		}
		return false;

	}

	public Bus fetchTicketDetailsForShowMyTicket(int id, int busId) throws ClassNotFoundException, SQLException {
		getConnection();
		Bus bus = new Bus();
		String sqlQuery = "SELECT TICKET_BOOKING.TICKET_COUNT,BUS.BUS_NAME,BUS.BUS_TIMING,BUS.BUS_DATE,BUS.bus_start_from,BUS.bus_reach_to ,BUS_REVENUE.TICKET_STATUS FROM TICKET_BOOKING INNER JOIN BUS ON BUS.ID = BUS_ID INNER JOIN USER ON BUS.ID=? AND USER.ID =? inner join BUS_REVENUE on  BUS.id = BUS_REVENUE.bus_id";

		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setInt(1, busId);
		preparedStatement.setInt(2, id);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {

			bus.setBusSeatCount(resultSet.getInt(1));
			bus.setBusName(resultSet.getString(2));
			bus.setBusTiming(resultSet.getString(3));
			bus.setBusDate(resultSet.getString(4));
			bus.setBusStartFrom(resultSet.getString(5));
			bus.setBusReachTo(resultSet.getString(6));
			bus.setBusTicketStatus(resultSet.getString(7));
			return bus;

		}
		return bus;

	}

	public void busRevenue(int userId, int busNumber, String userTicketBookingNumber, Integer userTicketAmount)
			throws ClassNotFoundException, SQLException {
		getConnection();
		String sqlQuery = "INSERT INTO BUS_REVENUE (USER_ID,BUS_ID,TICKET_NUMBER,TICKET_STATUS,REVENUE)VALUES(?,?,?,?,?)";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setInt(1, userId);
		preparedStatement.setInt(2, busNumber);
		preparedStatement.setString(3, userTicketBookingNumber);
		preparedStatement.setString(4, "BOOKED");
		preparedStatement.setInt(5, userTicketAmount);
		preparedStatement.executeUpdate();

	}

	public int deleteTheBus(int busId) throws ClassNotFoundException, SQLException {
		getConnection();
		String sqlQuery = "DELETE FROM BUS WHERE ID = ?";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setInt(1, busId);
		int result = preparedStatement.executeUpdate();
		return result;

	}

	public List<Bus> fetchBusRevenueDetails(int busId) throws ClassNotFoundException, SQLException {
		getConnection();
		String sqlQuery = "SELECT TICKET_NUMBER,TICKET_STATUS,REVENUE FROM BUS_REVENUE WHERE BUS_ID =? ";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setInt(1, busId);
		resultSet = preparedStatement.executeQuery();
		List<Bus> busRevenueDetails = new ArrayList<Bus>();
		while (resultSet.next()) {
			Bus bus = new Bus();
			bus.setBusTiming(resultSet.getString(1));
			bus.setBusName(resultSet.getString(2));
			bus.setBusSeatCount(resultSet.getInt(3));
			busRevenueDetails.add(bus);

		}
		return busRevenueDetails;

	}

	public void updateBusDetails(Bus bus) throws ClassNotFoundException, SQLException {
		getConnection();

		String sqlQuery = "UPDATE BUS SET BUS_NAME =?,BUS_START_FROM =?,BUS_REACH_TO = ?,BUS_SEAT_COUNT =?,BUS_TICKET_RATE=?,BUS_TIMING=?,BUS_DATE=? WHERE ID=?";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setString(1, bus.getBusName());
		preparedStatement.setString(2, bus.getBusStartFrom());
		preparedStatement.setString(3, bus.getBusReachTo());
		preparedStatement.setInt(4, bus.getBusSeatCount());
		preparedStatement.setInt(5, bus.getBusTicketRate());
		preparedStatement.setString(6, bus.getBusTiming());
		preparedStatement.setString(7, bus.getBusDate());
		preparedStatement.setInt(8, bus.getBusId());

		preparedStatement.executeUpdate();

	}

	public void cancelTheTicket(int userId, int busId, int ticketCount) throws ClassNotFoundException, SQLException {
		getConnection();
		String sqlQuery = "UPDATE BUS_REVENUE SET TICKET_STATUS = ?,REVENUE =? WHERE USER_ID = ? AND BUS_ID =?";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setString(1, "CANCELLED");
		preparedStatement.setInt(2, ticketCount * 100);
		preparedStatement.setInt(3, userId);
		preparedStatement.setInt(4, busId);
		preparedStatement.executeUpdate();

	}

	public void decreaseTheSeatCount(int userTicketCount, int busNumber, Integer seatCount)
			throws ClassNotFoundException, SQLException {
		getConnection();
		String sqlQuery = "UPDATE BUS SET BUS_SEAT_COUNT = ? WHERE ID =?";
		preparedStatement = connection.prepareStatement(sqlQuery);
		int currentSeatCount = seatCount - userTicketCount;
		preparedStatement.setInt(1, currentSeatCount);
		preparedStatement.setInt(2, busNumber);

		preparedStatement.executeUpdate();
	}

	public int fetchCurrentBusSeatCount(int busId) throws ClassNotFoundException, SQLException {
		getConnection();
		int seatCount = 0;
		String sqlQuery = "SELECT BUS_SEAT_COUNT FROM BUS WHERE ID = ?";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setInt(1, busId);
		resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			seatCount = resultSet.getInt(1);

		}
		return seatCount;
	}

	public void increaseTheTicketCount(int busId, int seatCount, int ticketCount)
			throws ClassNotFoundException, SQLException {
		getConnection();
		String sqlQuery = "UPDATE BUS SET BUS_SEAT_COUNT = ? WHERE ID =?";
		preparedStatement = connection.prepareStatement(sqlQuery);
		int currentSeatCount = seatCount + ticketCount;
		preparedStatement.setInt(1, currentSeatCount);
		preparedStatement.setInt(2, busId);

		preparedStatement.executeUpdate();

	}

}
