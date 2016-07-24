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
import com.mie.model.Time;
import com.mie.model.User;

public class ActivityController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userdao;
	private ActivityDao activitydao;
	private MatchDao matchdao;
	private PartnerDao partnerdao;
	private TimeDao timedao;

	public ActivityController() {
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
	    //get a list of the activities by user id
		if (action.equalsIgnoreCase("useractivities")) {
			List<Activity> activities = activitydao.getActivitiesByUser(Integer.parseInt(request.getParameter("userid")));
			System.out.println(activities.size());
			String data = "";
			//append string to be sent back with the details of each activity and time
			for (int i = 0; i < activities.size()-1; i++){
				int timeid = activities.get(i).getTimeid();
				Time time = timedao.getTimeById(timeid);
				data += activities.get(i).toFullString(time) + ",,";
			}
			//for the last activity append it to the string without ,,
			if(activities.size() > 0) {
				int lasttimeid = activities.get(activities.size()-1).getTimeid();
				Time lasttime = timedao.getTimeById(lasttimeid);
				data += activities.get(activities.size()-1).toFullString(lasttime);
			}
			//send full string back to the client 
			response.getWriter().write(data);
		} 
		//get details for a specific activity by activity id
		else if (action.equalsIgnoreCase("fetch")) {
			Activity activity = activitydao.getActivityById(Integer.parseInt(request.getParameter("activityid")));
			int timeid = activity.getTimeid();
			Time time = timedao.getTimeById(timeid);
			//send activity details back to client
			response.getWriter().write(activity.toFullString(time));
		}
		//update activity details by activity 
		else if (action.equalsIgnoreCase("update")) {
			Activity activity = new Activity();
			Time time = new Time();
			Activity updateactivity = activitydao.getActivityById(Integer.parseInt(request.getParameter("activityid")));
			Time updatetime = timedao.getTimeById(updateactivity.getTimeid());
			time.setTimeid(updateactivity.getTimeid());
			activity.setActivity(request.getParameter("activity"));
			activity.setActivityid(Integer.parseInt(request.getParameter("activityid")));
			time.setHours(request.getParameter("time"));
			time.setDay(request.getParameter("day"));
			activity.setTimeid(updateactivity.getTimeid());
			activity.setExperience(request.getParameter("experience"));
			activity.setUser(Integer.parseInt(request.getParameter("user")));
			//update activity and time details 
			activitydao.updateActivity(activity);
			timedao.updateTime(time);
			//delete all matches that were related to that activity
			matchdao.deleteCorrespondingMatchesByActivity(updateactivity, updatetime);
			matchdao.deleteMatchesByActivity(activity.getActivityid());
			//add matches based on the new activity
			matchdao.addMatchesByActivity(activity, time);
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
	}
}