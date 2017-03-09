package com.ourhappyhour.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.ourhappyhour.connection.DBConnection;
import com.ourhappyhour.entitys.User;
import com.ourhappyhour.utils.Json;

public class UserService {
	
	public ArrayList<User> getUsers() {
		Connection connection = DBConnection.createConnection();
		
		ArrayList<User> userList = new ArrayList<User>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT id, name, email, balance, disabled FROM public.user WHERE public.user.disabled IS FALSE");
			while (resultSet.next()) {
				User userFromDB = new User();
				userFromDB.setId(Integer.parseInt(resultSet.getString("id")));
				userFromDB.setName(resultSet.getString("name"));
				userFromDB.setEmail(resultSet.getString("email"));
				userFromDB.setBalance(resultSet.getFloat("balance"));
				userFromDB.setDisabled(resultSet.getBoolean("disabled"));
				userList.add(userFromDB);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			connection.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return userList;
	}
	
	public User getUserById(int id) {
		Connection connection = DBConnection.createConnection();

		User userFromDB = new User();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT id, name, email, balance, disabled FROM public.user WHERE public.user.id = " + id);
			if (resultSet.next()) {
				userFromDB.setId(Integer.parseInt(resultSet.getString("id")));
				userFromDB.setName(resultSet.getString("name"));
				userFromDB.setEmail(resultSet.getString("email"));
				userFromDB.setBalance(resultSet.getFloat("balance"));
				userFromDB.setDisabled(resultSet.getBoolean("disabled"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			connection.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return userFromDB;
	}
	
	public boolean postUser(String json) {
		boolean success = true;
		User user = new User();
		try {
			user = Json.fromJson(json, User.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Connection connection = DBConnection.createConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO public.user (name, email, balance, disabled) values (?, ?, ?, ?)");
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			if (user.getBalance() != null) {
				preparedStatement.setFloat(3, user.getBalance());
			} else {
				preparedStatement.setFloat(3, 0);
			}
			if (user.getDisabled() != null) {
				preparedStatement.setBoolean(4, user.getDisabled());
			} else {
				preparedStatement.setBoolean(4, false);
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
	
	public boolean putUser(String json) {
		boolean success = true;
		User user = new User();
		try {
			user = Json.fromJson(json, User.class);
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}

		Connection connection = DBConnection.createConnection();
		
		try {
			User oldVersion = getUserById(user.getId());
			PreparedStatement preparedStatement = connection.prepareStatement("UPDATE public.user SET name = ?, email = ?, balance = ?, disabled = ? WHERE public.user.id = ?");
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			if (user.getBalance() != null) {
				preparedStatement.setFloat(3, user.getBalance());
				if (oldVersion.getBalance() != user.getBalance()) {
					if (oldVersion.getBalance() == null) {
						oldVersion.setBalance((float) 0);
					}
					if(user.getBalance() == null) {
						user.setBalance((float) 0);
					}
					PreparedStatement preparedStatementUserTransactionHistoric = connection.prepareStatement("INSERT INTO public.user_transaction_historic (id_user, before_transaction, transaction_value, after_transaction, date) values (?, ?, ?, ?, ?)");
					preparedStatementUserTransactionHistoric.setInt(1, user.getId());
					preparedStatementUserTransactionHistoric.setFloat(2, oldVersion.getBalance());
					preparedStatementUserTransactionHistoric.setFloat(3, user.getBalance() - oldVersion.getBalance());
					preparedStatementUserTransactionHistoric.setFloat(4, user.getBalance());
					Date date = new Date();
					preparedStatementUserTransactionHistoric.setTimestamp(5, new Timestamp(date.getTime()));
					preparedStatementUserTransactionHistoric.execute();
				}
			} else {
				preparedStatement.setFloat(3, 0);
			}
			if (user.getDisabled() != null) {
				preparedStatement.setBoolean(4, user.getDisabled());
			} else {
				preparedStatement.setBoolean(4, false);
			}
			preparedStatement.setInt(5, user.getId());
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
	
	public boolean deleteById(int id) {
		boolean success = true;
		Connection connection = DBConnection.createConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, email, balance, disabled FROM public.user WHERE id = ?");
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				PreparedStatement preparedStatement2 = connection.prepareStatement("DELETE FROM public.user WHERE public.user.id = ?");
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
