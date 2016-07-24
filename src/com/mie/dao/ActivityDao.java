package com.mie.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mie.model.Activity;
import com.mie.model.User;
import com.mie.util.DbUtil;

public class ActivityDao {

	private Connection connection;

	public ActivityDao() {
		connection = DbUtil.getConnection();
	}

	//insert activity into activities with user(int referencing userid in users table), activity(string), timeid(int referencing timeid in times table), experience(string)
	public void addActivity(Activity activity) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO activities(user,activity,timeid,experience) VALUES (?,?,?,?)");
			System.out.println(activity.getUser());
			System.out.println(activity.getActivity());
			System.out.println(activity.getTimeid());
			System.out.println(activity.getExperience());

			preparedStatement.setInt(1, activity.getUser());
			preparedStatement.setString(2, activity.getActivity());
			preparedStatement.setInt(3, activity.getTimeid());
			preparedStatement.setString(4, activity.getExperience());
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//get the id of the last inserted activity
	//necessary to pass back id to client after an activity has been inserted
	public String getLastInsertedActivity() {
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement
					.executeQuery("SELECT * FROM activities WHERE activityid = (SELECT MAX(activityid) FROM activities);");
			if (rs.next()) {
				return rs.getString("activityid");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	//delete an activity from activities table with activityid(int)
	public void deleteActivity(int activityid) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("delete from activities where activityid=?");
			// Parameters start with 1
			preparedStatement.setInt(1, activityid);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//update activity with values activity(string), timeid(int referencing timeid in times table), experience(string) in activities table by activityid(int)
	public void updateActivity(Activity activity) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("update activities set activity=?, experience=?"
							+ " where activityid=?");
			preparedStatement.setString(1, activity.getActivity());
			preparedStatement.setString(2, activity.getExperience());
			preparedStatement.setInt(3, activity.getActivityid());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//get list of activities by user(int referencing the userid in users table)
	public List<Activity> getActivitiesByUser(int user) {
		List<Activity> activities = new ArrayList<Activity>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from activities where user=" + user);
			while (rs.next()) {
				Activity activity = new Activity();
				activity.setActivityid(rs.getInt("activityid"));
				activity.setUser(rs.getInt("user"));
				activity.setActivity(rs.getString("activity"));
				activity.setTimeid(rs.getInt("timeid"));
				activity.setExperience(rs.getString("experience"));
				activities.add(activity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return activities;
	}
	

	//get an activity from activities table by activityid
	public Activity getActivityById(int activityid) {
		Activity activity = new Activity();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from activities where activityid=" + activityid);
			while (rs.next()) {
				activity.setActivityid(rs.getInt("activityid"));
				activity.setUser(rs.getInt("user"));
				activity.setActivity(rs.getString("activity"));
				activity.setTimeid(rs.getInt("timeid"));
				activity.setExperience(rs.getString("experience"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return activity;
	}
}