package com.mie.model;

public class Time {

	private int timeid;
	private String day;
	private String hours;
	
	public int getTimeid() {
		return timeid;
	}
	public void setTimeid(int timeid) {
		this.timeid = timeid;
	}
	
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	
	@Override
	public String toString() {
		return timeid + "," + day + "," + hours;
	}
}
