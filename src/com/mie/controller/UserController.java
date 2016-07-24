package com.mie.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mie.dao.ActivityDao;
import com.mie.dao.MatchDao;
import com.mie.dao.TimeDao;
import com.mie.dao.UserDao;
import com.mie.model.Activity;
import com.mie.model.Time;
import com.mie.model.User;

public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userdao;
	private ActivityDao activitydao;
	private MatchDao matchdao;
	private TimeDao timedao;

	public UserController() {
		super();
		userdao = new UserDao();
		activitydao = new ActivityDao();
		matchdao = new MatchDao();
		timedao = new TimeDao();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println(action);
		response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
	    //called when the sign up form is submitted 
		if (action.equalsIgnoreCase("signup")) {
			User user = new User();
			Activity activity = new Activity();
			Activity activity2 = new Activity();
			Time time = new Time();
			Time time2 = new Time();
			user.setFirstName(request.getParameter("firstname"));
			user.setLastName(request.getParameter("lastname"));
			user.setEmail(request.getParameter("email"));
			user.setGender(request.getParameter("gender"));
			user.setPassword(request.getParameter("password"));
			//attempt add user to the database
			boolean success = userdao.addUser(user);
			if (success) {
				//if no user exists with that email, success is true
				//add subsequent account details to database such as activities and times
				String userid = userdao.getLastInsertedUser();
				user.setUserid(Integer.parseInt(userid));
				time.setDay(request.getParameter("day"));
				time.setHours(request.getParameter("time"));
				timedao.addTime(time);
				String timeid = timedao.getLastInsertedTime();
				time.setTimeid(Integer.parseInt(timeid));
				activity.setActivity(request.getParameter("activity"));
				activity.setTimeid(Integer.parseInt(timeid));
				activity.setExperience(request.getParameter("experience"));
				activity.setUser(Integer.parseInt(userid));
				time2.setDay(request.getParameter("day2"));
				time2.setHours(request.getParameter("time2"));
				timedao.addTime(time2);
				String timeid2 = timedao.getLastInsertedTime();
				time2.setTimeid(Integer.parseInt(timeid2));
				activity2.setActivity(request.getParameter("activity2"));
				activity2.setTimeid(Integer.parseInt(timeid2));
				activity2.setExperience(request.getParameter("experience2"));
				activity2.setUser(Integer.parseInt(userid));
				activitydao.addActivity(activity);
				String activityid = activitydao.getLastInsertedActivity();
				activity.setActivityid(Integer.parseInt(activityid));
				matchdao.addMatchesByActivity(activity, time);		
				//add all matches for activity 1
				activitydao.addActivity(activity2);
				String activity2id = activitydao.getLastInsertedActivity();
				activity2.setActivityid(Integer.parseInt(activity2id));
				//add all matches for activity 2
				matchdao.addMatchesByActivity(activity2, time2);
				//send user details back to client
			    response.getWriter().write(user.toString());

			} 
			else {
				//if the email already exists in the database send false back 
			    response.getWriter().write("false");
			}
		} 
		//called when the user submits the login form
		else if (action.equalsIgnoreCase("login")) {
			response.setContentType("text/plain");
		    response.setCharacterEncoding("UTF-8");
		    //check if a user with the inputed email and password exists
			boolean checked = userdao.checkUser(request.getParameter("email"),
					request.getParameter("password"));
			if (checked) {
				//if the user exists check for matches for each of their activities
				User user = userdao.getUserByEmail(request.getParameter("email"));
				List<Activity> activities = activitydao.getActivitiesByUser(user.getUserid());
				for (int i = 0; i < activities.size(); i++){
					Time time = timedao.getTimeById(activities.get(i).getTimeid());
					matchdao.addMatchesByActivity(activities.get(i), time);
				}
				//send the user details back to the client
			    response.getWriter().write(user.toString());
			} else { 
				//if a user does not exist with that email and password, return false
			    response.getWriter().write("false");
			}
		} 
		//called when user submits the edit account form
		else if (action.equalsIgnoreCase("update")) {
			response.setContentType("text/plain");
		    response.setCharacterEncoding("UTF-8");
		    //create new user with updated values
		    User user =  new User();
		    user.setFirstName(request.getParameter("firstname"));
			user.setLastName(request.getParameter("lastname"));
			user.setEmail(request.getParameter("email"));
			user.setGender(request.getParameter("gender"));
			user.setPassword(request.getParameter("password"));
			//update user in database and send user information back to the client
			userdao.updateUser(user);
		    System.out.println(user.toString());
		    response.getWriter().write(user.toString());
			
		}
		//called when user chooses to delete account
		else if (action.equalsIgnoreCase("delete")) {
		 int userid = Integer.parseInt(request.getParameter("userid"));
		 userdao.deleteUser(userid);
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		
	}
}