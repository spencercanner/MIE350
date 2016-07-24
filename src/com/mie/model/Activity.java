package com.mie.model;

public class Activity {
		private int activityid;
		private int user;
		private String activity;
		private int timeid;
		private String experience;
		
		public int getActivityid() {
			return activityid;
		}
		public void setActivityid(int activityid) {
			this.activityid = activityid;
		}
		public int getUser() {
			return user;
		}
		public void setUser(int user) {
			this.user = user;
		}
		public String getActivity() {
			return activity;
		}
		public void setActivity(String activity) {
			this.activity = activity;
		}
		public int getTimeid() {
			return timeid;
		}
		public void setTimeid(int timeid) {
			this.timeid = timeid;
		}
		public String getExperience() {
			return experience;
		}
		public void setExperience(String experience) {
			this.experience = experience;
		}
		
		//get a full string of the activity and time, relating to an activity
		public String toFullString(Time time) {
			return activityid + "," + user
					+ "," + activity + "," + time.getDay() + ","
					+ time.getHours() + "," + experience;
		}
		
}
