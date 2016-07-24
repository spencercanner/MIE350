package com.mie.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mie.model.Activity;
import com.mie.model.Match;
import com.mie.model.Time;
import com.mie.model.User;
import com.mie.util.DbUtil;

public class MatchDao {

	private Connection connection;

	public MatchDao() {
		connection = DbUtil.getConnection();
	}

	//add matches to the database by Activity and Time 
	public void addMatchesByActivity(Activity activity, Time time) {
		List<Match> matches = new ArrayList<Match>();
		try {
			//get list of activities from activities table who have the same activity, experience, day and time as the Activity and Time passed in, but do not have the same user 
			PreparedStatement preparedStatement = connection
					.prepareStatement("select * from activities as A, times as T where A.timeid=T.timeid and A.activity=? and T.day=? and T.hours=? and A.experience=? and A.user<>?");
			preparedStatement.setString(1, activity.getActivity());
			preparedStatement.setString(2, time.getDay());
			preparedStatement.setString(3, time.getHours());
			preparedStatement.setString(4, activity.getExperience());
			preparedStatement.setInt(5, activity.getUser());
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				//for each activity found create a new match for that activity
				Match match = new Match();
				match.setActivityid(activity.getActivityid());
				match.setMatcheduser(rs.getInt("user"));
				match.setPrimaryuser(activity.getUser());
				//check if a match already exists
				boolean success = checkMatch(match);
				if (!success) {
					//if the match doesn't exist add it to the database
					String matchid = addMatch(match);
					match.setMatchid(Integer.parseInt(matchid));
					matches.add(match);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	//insert a Match to the database and pass back id of the inserted match
	public String addMatch(Match match) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO matches(activityid,primaryuser,matcheduser,status) VALUES (?,?,?,0)");
			// Parameters start with 1
			preparedStatement.setInt(1, match.getActivityid());
			preparedStatement.setInt(2, match.getPrimaryuser());
			preparedStatement.setInt(3, match.getMatcheduser());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return getLastInsertedMatch();
	}

	//delete all matches that contain a specific activityid (int referencing the activities table)
	public void deleteMatchesByActivity(int activityid) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("delete from matches where activityid=? and status<>-1");
			// Parameters start with 1
			preparedStatement.setInt(1, activityid);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteCorrespondingMatchesByActivity(Activity activity, Time time){
		try {
			PreparedStatement preparedStatement = 
					connection.prepareStatement("select * from matches,activities,times " +
			"where matches.activityid=activities.activityid and times.timeid=activities.timeid and activities.activity=? " +
			"and times.day=? and times.hours=? and activities.experience=? " +
			"and matches.matcheduser=? and matches.status<>-1");
			preparedStatement.setString(1, activity.getActivity());
			preparedStatement.setString(2, time.getDay());
			preparedStatement.setString(3, time.getHours());
			preparedStatement.setString(4, activity.getExperience());
			preparedStatement.setInt(5, activity.getUser());
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				deleteMatchById(rs.getInt("matchid"));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
	public void deleteMatchById(int matchid){
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("delete from matches where matchid=? and status<>-1");
			// Parameters start with 1
			preparedStatement.setInt(1, matchid);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//check if a Match exists with the same information as the match passed in
	public boolean checkMatch(Match match) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("select * from matches where activityid=? and primaryuser=? and matcheduser=?");
			preparedStatement.setInt(1, match.getActivityid());
			preparedStatement.setInt(2, match.getPrimaryuser());
			preparedStatement.setInt(3, match.getMatcheduser());
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	//get all matches where the primaryuser is equal to the userid passed in
	//matches with status 0 mean they aren't pending, accepted, or rejected
	public List<Match> getMatchesByUser(int userid) {
		List<Match> matches = new ArrayList<Match>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement
					.executeQuery("select * from matches where status=0 and primaryuser="
							+ userid);
			while (rs.next()) {
				Match match = new Match();
				match.setMatchid(rs.getInt("matchid"));
				match.setMatcheduser(rs.getInt("matcheduser"));
				match.setPrimaryuser(rs.getInt("primaryuser"));
				match.setActivityid(rs.getInt("activityid"));
				matches.add(match);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return matches;
	}

	//get all pending matches where the primaryuser is equal to the userid passedin
	//match with status 1 means that they are pending (have been accepted by the primary user, but not the matcheduser)
	public List<Match> getPendingByUser(int userid) {
		List<Match> matches = new ArrayList<Match>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement
					.executeQuery("select * from matches where status=1 and primaryuser="
							+ userid);
			while (rs.next()) {
				Match match = new Match();
				match.setMatchid(rs.getInt("matchid"));
				match.setMatcheduser(rs.getInt("matcheduser"));
				match.setPrimaryuser(rs.getInt("primaryuser"));
				match.setActivityid(rs.getInt("activityid"));
				matches.add(match);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return matches;
	}


	//set the status of a match equal to one where the matchid matches the one passed in
	//occurs when the primaryuser accepts the match
	public void confirmMatch(int matchid) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("update matches set status=1 where matchid=?");
			// Parameters start with 1
			preparedStatement.setInt(1, matchid);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//set the status of matches back to zero that involve the user1 and user2 passed in
	public void restoreMatch(int user1, int user2) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("update matches set status=0 where (primaryuser=? and matcheduser=?) or (primaryuser=? and matcheduser=?)");
			// Parameters start with 1
			preparedStatement.setInt(1, user1);
			preparedStatement.setInt(2, user2);
			preparedStatement.setInt(3, user2);
			preparedStatement.setInt(4, user1);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//set the status of a match to -1, meaning deleted, where the matchid matches the one passed in
	public void deleteMatch(int matchid) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("update matches set status=-1 where matchid=?");
			// Parameters start with 1
			preparedStatement.setInt(1, matchid);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//check if the corresponding match to the one passed in matches
	//check that all appropriate Activity and Time attributes match
	//check that the primaryuser of the passed in match is the matcheduser of the corresponding match
	//check that the matcheduser of the passed in match is the primary user of the corresponding match
	//check that the corresponding match is also accepted (status is 1)
	public int checkCorrespondingMatch(Match match, Activity activity, Time time) {
		try {
			PreparedStatement preparedStatement = 
					connection.prepareStatement("select * from matches,activities,times " +
			"where matches.activityid=activities.activityid and times.timeid=activities.timeid and activities.activity=? " +
			"and times.day=? and times.hours=? and activities.experience=? " +
			"and matches.primaryuser=? and matches.matcheduser=? and matches.status=1");
			preparedStatement.setString(1, activity.getActivity());
			preparedStatement.setString(2, time.getDay());
			preparedStatement.setString(3, time.getHours());
			preparedStatement.setString(4, activity.getExperience());
			preparedStatement.setInt(5, match.getMatcheduser());
			preparedStatement.setInt(6, match.getPrimaryuser());
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				//if the match exists, return the matchid
				return rs.getInt("matchid");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		//if no corresponding match exists, return -1
		return -1;
	}
	
	//similar to above function but it does not check that the corresponding match is accepted 
	public int checkMatch(Match match, Activity activity, Time time) {
		try {
			PreparedStatement preparedStatement = 
					connection.prepareStatement("select * from matches,activities,times " +
			"where matches.activityid=activities.activityid and times.timeid=activities.timeid and activities.activity=? " +
			"and times.day=? and times.hours=? and activities.experience=? " +
			"and matches.primaryuser=? and matches.matcheduser=?");
			
			preparedStatement.setString(1, activity.getActivity());
			preparedStatement.setString(2, time.getDay());
			preparedStatement.setString(3, time.getHours());
			preparedStatement.setString(4, activity.getExperience());
			preparedStatement.setInt(5, match.getMatcheduser());
			preparedStatement.setInt(6, match.getPrimaryuser());
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				return rs.getInt("matchid");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	//get match from matches table where the matchid is the same as the one passed in
	public Match getMatchById (int matchid){
		Match match = new Match();
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("select * from matches where matchid=?");
			preparedStatement.setInt(1, matchid);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				match.setMatchid(rs.getInt("matchid"));
				match.setPrimaryuser(rs.getInt("primaryuser"));
				match.setMatcheduser(rs.getInt("matcheduser"));
				match.setActivityid(rs.getInt("activityid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return match;
	}

	//set a match as accepted (status is 2) where the matchid is the same as the one passed in
	public void setMatchAccepted(int matchid) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("update matches set status=2 where matchid=?");
			// Parameters start with 1
			preparedStatement.setInt(1, matchid);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//set the match back to open (status as 0) where the matchid matches the on passed in
	public void cancelRequest(int matchid) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("update matches set status=0 where matchid=?");
			// Parameters start with 1
			preparedStatement.setInt(1, matchid);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//get the id of the last inserted match
	//necessary when returning information about an inserted match to the client
	public String getLastInsertedMatch() {
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement
					.executeQuery("SELECT * FROM matches WHERE matchid = (SELECT MAX(matchid) FROM matches);");
			if (rs.next()) {
				return rs.getString("matchid");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
}