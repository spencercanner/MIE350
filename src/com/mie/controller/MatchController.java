package com.mie.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mie.dao.ActivityDao;
import com.mie.dao.MatchDao;
import com.mie.dao.PartnerDao;
import com.mie.dao.TimeDao;
import com.mie.dao.UserDao;
import com.mie.model.Activity;
import com.mie.model.Match;
import com.mie.model.Partner;
import com.mie.model.Time;
import com.mie.model.User;

public class MatchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userdao;
	private ActivityDao activitydao;
	private MatchDao matchdao;
	private PartnerDao partnerdao;
	private TimeDao timedao;

	public MatchController() {
		super();
		userdao = new UserDao();
		activitydao = new ActivityDao();
		matchdao = new MatchDao();
		partnerdao = new PartnerDao();
		timedao = new TimeDao();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println(action);
		response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
	    //get list of matches by user id
		if (action.equalsIgnoreCase("usermatches")) {
			List<Match> matches = matchdao.getMatchesByUser(Integer.parseInt(request.getParameter("userid")));
			String data = "";
			//append string to be sent back with the activity, time and user details of the match
			for (int i = 0; i < matches.size()-1; i++){
				int activityid = matches.get(i).getActivityid();
				int matcheduser = matches.get(i).getMatcheduser();
				Activity activity = activitydao.getActivityById(activityid);
				User user = userdao.getUserById(matcheduser);
				Time time = timedao.getTimeById(activity.getTimeid());
				data += matches.get(i).toFullString(activity, user, time) + ",,";
			}
			//append the same info for the last without ,, 
			if(matches.size() > 0){
				int lastactivityid = matches.get(matches.size()-1).getActivityid();
				int lastmatcheduser = matches.get(matches.size()-1).getMatcheduser();
				data += matches.get(matches.size()-1).toFullString(activitydao.getActivityById(lastactivityid), userdao.getUserById(lastmatcheduser), timedao.getTimeById(activitydao.getActivityById(lastactivityid).getTimeid()));
			}
			//send string back to client
			response.getWriter().write(data);
		} 
		//get list of pending matches by user id
		else if (action.equalsIgnoreCase("userpending")) {
			List<Match> matches = matchdao.getPendingByUser(Integer.parseInt(request.getParameter("userid")));
			String data = "";
			//for each pending match, append string with activity, time and user info
			for (int i = 0; i < matches.size()-1; i++){
				int activityid = matches.get(i).getActivityid();
				int matcheduser = matches.get(i).getMatcheduser();
				Activity activity = activitydao.getActivityById(activityid);
				User user = userdao.getUserById(matcheduser);
				Time time = timedao.getTimeById(activity.getTimeid());
				data += matches.get(i).toFullString(activity, user, time) + ",,";
			}
			//do the same for the last pending, without the ,,
			if(matches.size() > 0){
				int lastactivityid = matches.get(matches.size()-1).getActivityid();
				int lastmatcheduser = matches.get(matches.size()-1).getMatcheduser();
				data += matches.get(matches.size()-1).toFullString(activitydao.getActivityById(lastactivityid), userdao.getUserById(lastmatcheduser), timedao.getTimeById(activitydao.getActivityById(lastactivityid).getTimeid()));
			}
			//send string back to client
			response.getWriter().write(data);
		} 
		//mark a match as confirmed by match id
		else if (action.equalsIgnoreCase("add")) {
			//set primary users match as confirmed
			matchdao.confirmMatch(Integer.parseInt(request.getParameter("matchid")));
			Match match = matchdao.getMatchById(Integer.parseInt(request.getParameter("matchid")));
			Activity activity = activitydao.getActivityById(match.getActivityid());
			Time time = timedao.getTimeById(activity.getTimeid());
			//check if corresponding match (one where current primary user is the matched user) is also confirmed  
			int correspondingMatch = matchdao.checkCorrespondingMatch(match, activity, time);
			if(correspondingMatch > 0){
				//if corresponding match is confirmed, users are allowed to be partners
				//set both matches as accepted 
				matchdao.setMatchAccepted(correspondingMatch);
				matchdao.setMatchAccepted(Integer.parseInt(request.getParameter("matchid")));
				//add two partners to the table each with opposite users
				Partner partner1 = new Partner();
				partner1.setUser1(match.getPrimaryuser());
				partner1.setUser2(match.getMatcheduser());
				partnerdao.addPartner(partner1);
				Partner partner2 = new Partner();
				partner2.setUser1(match.getMatcheduser());
				partner2.setUser2(match.getPrimaryuser());
				partnerdao.addPartner(partner2);
			}	
		}
		//set match as deleted by match id
		else if (action.equalsIgnoreCase("delete")){
			Match match = matchdao.getMatchById(Integer.parseInt(request.getParameter("matchid")));
			Activity activity = activitydao.getActivityById(match.getActivityid());
			Time time = timedao.getTimeById(activity.getTimeid());
			//get id of corresponding match
			int correspondingid = matchdao.checkMatch(match, activity, time);
			System.out.println(correspondingid + "correspondingid" + match.getMatcheduser() + match.getPrimaryuser() + activity.toFullString(time));
			if (correspondingid > 0){
				//set corresponding match as deleted 
				matchdao.deleteMatch(correspondingid);
			}
			//set original match as deleted 
			matchdao.deleteMatch(Integer.parseInt(request.getParameter("matchid")));
		}
		//cancel a match that has been set as confirmed by match id
		//change the match from pending to open
		else if (action.equalsIgnoreCase("cancel")){
			matchdao.cancelRequest(Integer.parseInt(request.getParameter("matchid")));
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
	}
}