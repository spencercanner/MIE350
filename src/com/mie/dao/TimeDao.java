package com.mie.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mie.model.Activity;
import com.mie.model.Time;
import com.mie.model.User;
import com.mie.util.DbUtil;

public class TimeDao {

	private Connection connection;

	public TimeDao() {
		connection = DbUtil.getConnection();
	}

	//insert time into the times table 
	public void addTime(Time time) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO times(day,hours) VALUES (?,?)");
			// Parameters start with 1

			preparedStatement.setString(1, time.getDay());
			preparedStatement.setString(2, time.getHours());
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//get the id of the last inserted time
	//required to insert activities into the activities table which reference the timeid recently inserted 
	public String getLastInsertedTime() {
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement
					.executeQuery("SELECT * FROM times WHERE timeid = (SELECT MAX(timeid) FROM times);");
			if (rs.next()) {
				return rs.getString("timeid");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	//get Time which has a timeid matching the one passed in
	public Time getTimeById(int timeid){
		Time time = new Time();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from times where timeid=" + timeid);
			while (rs.next()) {
				time.setTimeid(rs.getInt("timeid"));
				time.setDay(rs.getString("day"));
				time.setHours(rs.getString("hours"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return time;
	}
	
	//update time to with the values of the attributes of the passed in Time
	public void updateTime(Time time) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("update times set day=?, hours=?"
							+ " where timeid=?");
			// Parameters start with 1
			preparedStatement.setString(1, time.getDay());
			preparedStatement.setString(2, time.getHours());
			preparedStatement.setInt(3, time.getTimeid());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
}