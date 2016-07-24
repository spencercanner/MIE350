<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fit Together - My Activity</title>
<!-- <link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css"> -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="script/activity.js"></script>
<!-- <link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css"> -->
	<link rel="stylesheet" type="text/css" href="style/theme.css" />
</head>
<body>
<div class="wrap">
		<div class="imageContainer">
			<div class="imageOverlay"></div>
			<img class="image"
				src="images/uoft_night.jpg">
		</div>
		<div class="topbar">
			<div class="toptitle">
				<a href="home.jsp" class="hometext"> FitTogether&nbsp; <img class="topbarheart"
					src="images/heartbeat.png" /> </a>
		
			</div>
			<ul class="toplist"> 
	        <li>
	            <a href="#" id="welcome">
	            		
	            </a>
	            <ul class="topdropdown">
	                <li><a class="topmenutext" href="account.jsp">
	                <div class="topmenutext">&nbspMy Account</div>
	                </a></li>
	                <li><a class="topmenutext" href="index.jsp">
	                <div class="topmenutext">&nbspSign Out</div>
	                </a></li>
	            </ul>
	        </li>
    	</ul>
		</div>
		<form method="POST" action="UserController" id="editactivityform">
				<div class="formcontainer" id="editactivity">
					<div class="formtop">Edit Activity 					
					</div>
					<div class="editactivity">
							
						<br> Type
						<br>
						<select class="dropdown" id="activity" name="activity">
							<option value="Running">Running</option>
							<option value="Gym">Gym</option>
							<option value="Cycling">Cycling</option>
							<option value="None">None</option>
						</select> <br> Experience <br> 
						<select class="dropdown" id="experience" name="experience">
							<option value="Novice">Novice</option>
							<option value="Intermediate">Intermediate</option>
							<option value="Expert">Expert</option>
						</select> 
						<br> Day
						<br>
						<select class="dropdown" id="day" name="day">
							<option value="Monday">Monday</option>
							<option value="Tuesday">Tuesday</option>
							<option value="Wednesday">Wednesday</option>
							<option value="Thursday">Thursday</option>
							<option value="Friday">Friday</option>
							<option value="Saturday">Saturday</option>
							<option value="Sunday">Sunday</option>
						</select>
						<br> Time
						<br>
						<select class="dropdown" id="time" name="time">
							<option value="6:00am - 9:00am">6:00am - 9:00am</option>
							<option value="9:00am - 12:00pm">9:00am - 12:00pm</option>
							<option value="12:00pm - 3:00pm">12:00pm - 3:00pm</option>
							<option value="3:00pm - 6:00pm">3:00pm - 6:00pm</option>
							<option value="6:00pm - 9:00pm">6:00pm - 9:00pm</option>
						</select>
						<br><br>
							<a
							class="signupbtn" id="submit"
							onclick="updateActivity()">Save Changes</a>
					</div>
					
				</div>

			</form>
	</div>
</body>
</html>