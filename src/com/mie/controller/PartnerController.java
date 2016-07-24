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

public class PartnerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userdao;
	private ActivityDao activitydao;
	private MatchDao matchdao;
	private PartnerDao partnerdao;
	private TimeDao timedao;

	public PartnerController() {
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
	    //get list of partners by user id 
		if (action.equalsIgnoreCase("userpartners")) {
			List<Partner> partners = partnerdao.getPartnersByUser(Integer.parseInt(request.getParameter("userid")));
			String data = "";
			//for each partner, append partner info to string to be sent to client
			for (int i = 0; i < partners.size()-1; i++){
				int partnerid = partners.get(i).getPartnerid();
				int user2 = partners.get(i).getUser2();
				User user = userdao.getUserById(user2);
				data += user.toString() + ",,";
			}
			//for last partner append without ,,
			if(partners.size() > 0){
				int user2 = partners.get(partners.size()-1).getUser2();
				data += userdao.getUserById(user2).toString();
			}
			//send string of partners back to client
			response.getWriter().write(data);
		} 
		
		//delete a a set of partners by user ids
		else if (action.equalsIgnoreCase("delete")){
			partnerdao.deletePartners(Integer.parseInt(request.getParameter("user1")), Integer.parseInt(request.getParameter("user2")));
			//set match of the partners as open
			matchdao.restoreMatch(Integer.parseInt(request.getParameter("user1")), Integer.parseInt(request.getParameter("user2")));
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		
	}
}