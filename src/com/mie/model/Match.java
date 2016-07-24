package com.mie.model;

public class Match {
		private int matchid;
		private int activityid;
		private int primaryuser;
		private int matcheduser;
	
		public int getMatchid() {
			return matchid;
		}


		public void setMatchid(int matchid) {
			this.matchid = matchid;
		}


		public int getActivityid() {
			return activityid;
		}


		public void setActivityid(int activityid) {
			this.activityid = activityid;
		}


		public int getPrimaryuser() {
			return primaryuser;
		}


		public void setPrimaryuser(int primaryuser) {
			this.primaryuser = primaryuser;
		}


		public int getMatcheduser() {
			return matcheduser;
		}


		public void setMatcheduser(int matcheduser) {
			this.matcheduser = matcheduser;
		}
		
		//get a comma separated string with the activity, user and time information of the match
		public String toFullString(Activity activity, User user, Time time) {
			return matchid + "," + activity.getActivityid()
					+ "," + activity.getActivity() + "," + time.getDay()
					 + "," +  time.getHours()  + "," + activity.getExperience()
					  + "," + user.getFirstName() + "," + user.getLastName();
		}
		
}
