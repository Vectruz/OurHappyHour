package com.ourhappyhour.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ourhappyhour.connection.DBConnection;
import com.ourhappyhour.entitys.Event;
import com.ourhappyhour.entitys.EventUser;
import com.ourhappyhour.entitys.User;
import com.ourhappyhour.utils.Json;

public class EventService {
	
	private UserService userService = new UserService();
	
	public ArrayList<Event> getAll() {
		Connection connection = DBConnection.createConnection();
		
		ArrayList<Event> eventList = new ArrayList<Event>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, description, location, time, disabled FROM public.event WHERE public.event.disabled IS FALSE");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Event eventFromDB = new Event();
				eventFromDB.setId(Integer.parseInt(resultSet.getString("id")));
				eventFromDB.setName(resultSet.getString("name"));
				eventFromDB.setDescription(resultSet.getString("description"));
				eventFromDB.setLocation(resultSet.getString("location"));
				eventFromDB.setTime(resultSet.getTimestamp("time"));
				eventFromDB.setDisabled(resultSet.getBoolean("disabled"));
				eventList.add(eventFromDB);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			connection.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return eventList;
	}
	
	public Event getById(int id) {
		Connection connection = DBConnection.createConnection();
		
		Event eventFromDB = new Event();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, description, location, time, disabled, total_value FROM public.event WHERE public.event.id = ?");
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				eventFromDB.setId(Integer.parseInt(resultSet.getString("id")));
				eventFromDB.setName(resultSet.getString("name"));
				eventFromDB.setDescription(resultSet.getString("description"));
				eventFromDB.setLocation(resultSet.getString("location"));
				eventFromDB.setTime(resultSet.getTimestamp("time"));
				eventFromDB.setDisabled(resultSet.getBoolean("disabled"));
				eventFromDB.setTotalValue(resultSet.getFloat("total_value"));
			}
			PreparedStatement preparedStatementSearchEventUsers = connection.prepareStatement("SELECT id, id_event, id_user, confirmed, custom_payment, custom_payment_value, paid FROM public.event_user WHERE public.event_user.id_event = ?");
			preparedStatementSearchEventUsers.setInt(1, id);
			ResultSet resultSetEventUser = preparedStatementSearchEventUsers.executeQuery();
			ArrayList<EventUser> listEventUser = new ArrayList<EventUser>();
			while (resultSetEventUser.next()) {
				EventUser tempEventUser = new EventUser();
				tempEventUser.setUser(userService.getUserById(resultSetEventUser.getInt("id_user")));
				tempEventUser.setConfirmed(resultSetEventUser.getBoolean("confirmed"));
				tempEventUser.setCustomPayment(resultSetEventUser.getBoolean("custom_payment"));
				tempEventUser.setCustomPaymentValue(resultSetEventUser.getFloat("custom_payment_value"));
				tempEventUser.setPaid(resultSetEventUser.getFloat("paid"));
				listEventUser.add(tempEventUser);
			}
			eventFromDB.setEventUsers(listEventUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			connection.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return eventFromDB;
	}
	
	public boolean postEvent(String json) {
		boolean success = true;
		Event event = new Event();
		try {
			event = Json.fromJson(json, Event.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Connection connection = DBConnection.createConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO public.event (name, description, location, time, disabled, total_value) values (?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1, event.getName());
			if (event.getDescription() != null) {
				preparedStatement.setString(2, event.getDescription());
			} else {
				preparedStatement.setString(2, null);
			}
			preparedStatement.setString(3, event.getLocation());
			preparedStatement.setTimestamp(4, event.getTime());
			if (event.getDisabled() != null) {
				preparedStatement.setBoolean(5, event.getDisabled());
			} else {
				preparedStatement.setBoolean(5, false);
			}
			if (event.getTotalValue() != null) {
				preparedStatement.setFloat(6, event.getTotalValue());
			} else {
				preparedStatement.setFloat(6, 0);
			}
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		
		try {
			connection.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	public boolean putEvent(String json) {
		boolean success = true;
		Event event = new Event();
		try {
			event = Json.fromJson(json, Event.class);
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}

		Connection connection = DBConnection.createConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("UPDATE public.event SET name = ?, description = ?, location = ?, time = ?, disabled = ?, total_value = ? WHERE public.event.id = ?");
			preparedStatement.setString(1, event.getName());
			if (event.getDescription() != null) {
				preparedStatement.setString(2, event.getDescription());
			} else {
				preparedStatement.setString(2, null);
			}
			preparedStatement.setString(3, event.getLocation());
			preparedStatement.setTimestamp(4, event.getTime());
			if (event.getDisabled() != null) {
				preparedStatement.setBoolean(5, event.getDisabled());
			} else {
				preparedStatement.setBoolean(5, false);
			}
			if (event.getTotalValue() != null) {
				preparedStatement.setFloat(6, event.getTotalValue());
			} else {
				preparedStatement.setFloat(6, 0);
			}
			preparedStatement.setInt(7, event.getId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		
		if (event.getEventUsers() != null) {
			for (int i = 0; i < event.getEventUsers().size(); i++) {
				userService.putUser(Json.toJson(event.getEventUsers().get(i).getUser()));
				try {
					PreparedStatement preparedStatementCleanEventUserTable = connection.prepareStatement("DELETE FROM public.event_user WHERE public.event_user.id_event = ? AND public.event_user.id_user = ?");
					preparedStatementCleanEventUserTable.setInt(1, event.getId());
					preparedStatementCleanEventUserTable.setInt(2, event.getEventUsers().get(i).getUser().getId());
					preparedStatementCleanEventUserTable.executeUpdate();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		try {
			PreparedStatement preparedStatementSearchEventUserFromEvent = connection.prepareStatement("SELECT id_user, id_event, paid FROM public.event_user WHERE public.event_user.id_event = ?");
			preparedStatementSearchEventUserFromEvent.setInt(1, event.getId());
			ResultSet remainingEventUsers = preparedStatementSearchEventUserFromEvent.executeQuery();
			while (remainingEventUsers.next()) {
				if (remainingEventUsers.getFloat("paid") != 0) {
					User tempUser = userService.getUserById(remainingEventUsers.getInt("id_user"));
					tempUser.setBalance(tempUser.getBalance() + remainingEventUsers.getFloat("paid"));
					userService.putUser(Json.toJson(tempUser));
				}
			}
			PreparedStatement preparedStatementDeleteAllEventUsersFromThisEvent = connection.prepareStatement("DELETE FROM public.event_user WHERE public.event_user.id_event = ?");
			preparedStatementDeleteAllEventUsersFromThisEvent.setInt(1, event.getId());
			preparedStatementDeleteAllEventUsersFromThisEvent.executeUpdate();
			if (event.getEventUsers() != null) {
				for (int i = 0; i < event.getEventUsers().size(); i++) {
					PreparedStatement preparedStatementCreateEventUsers = connection.prepareStatement("INSERT INTO public.event_user (id_event, id_user, confirmed, custom_payment, custom_payment_value, paid) values (?, ?, ?, ?, ?, ?)");
					preparedStatementCreateEventUsers.setInt(1, event.getId());
					preparedStatementCreateEventUsers.setInt(2, event.getEventUsers().get(i).getUser().getId());
					if (event.getEventUsers().get(i).getConfirmed() != null) {
						preparedStatementCreateEventUsers.setBoolean(3, event.getEventUsers().get(i).getConfirmed());
					} else {
						preparedStatementCreateEventUsers.setBoolean(3, false);
					}
					if (event.getEventUsers().get(i).getCustomPayment() != null) {
						preparedStatementCreateEventUsers.setBoolean(4, event.getEventUsers().get(i).getCustomPayment());
					} else {
						preparedStatementCreateEventUsers.setBoolean(4, false);
					}
					if (event.getEventUsers().get(i).getCustomPaymentValue() != null) {
						preparedStatementCreateEventUsers.setFloat(5, event.getEventUsers().get(i).getCustomPaymentValue());
					} else {
						preparedStatementCreateEventUsers.setFloat(5, 0);
					}
					if (event.getEventUsers().get(i).getPaid() != null) {
						preparedStatementCreateEventUsers.setFloat(6, event.getEventUsers().get(i).getPaid());
					} else {
						preparedStatementCreateEventUsers.setFloat(6, 0);
					}
					preparedStatementCreateEventUsers.executeUpdate();
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		try {
			connection.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	public boolean deleteById(int id) {
		boolean success = true;
		Connection connection = DBConnection.createConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, description, location, time, disabled FROM public.event WHERE public.event.id = ?");
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				PreparedStatement preparedStatement2 = connection.prepareStatement("DELETE FROM public.event WHERE public.event.id = ?");
				preparedStatement2.setInt(1, id);
				preparedStatement2.executeUpdate();
				preparedStatement2.close();
			} else {
				success = false;
			}
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		
		try {
			connection.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	}

}
