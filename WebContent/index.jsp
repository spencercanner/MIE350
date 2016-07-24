<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<link rel="stylesheet" type="text/css" href="style/theme.css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<script src="script/script.js"></script>


<title>Fit Together</title>

</head>
<body>

	<div class="wrap">
		<div class="imageContainer">
			<div class="textContainer">
				Fit Together<br> <img class="heart" src="images/heartbeat.png" /><br>
				<a class="aboutus" href="about.jsp">About Us</a><br>
				<button class="home" id="login" onclick="showLogin()">Log In</button>&nbsp;&nbsp;&nbsp;
				<button class="home" id="signup" onclick="showSignup()">Sign Up</button>
			</div>
			<div class="imageOverlay"></div>
			<img class="image"
				src="images/uoft_night.jpg">
		</div>
		<form method="POST" action="UserController" id="signupform">
				<div class="formcontainer" id="create">
					<div class="formtop">Create Account 
					<img onclick="closeSignup()" class="close" src="http://uxrepo.com/static/icon-sets/elegant/png32/24/000000/icon_close_alt-24-000000.png">
					</div>
					<div class="create">
					<div class="formsection">Personal</div> <br>
						First Name <br>
						<input type="text" name="firstname"	id="firstname"> <br> 
							Last Name <br>
							<input type="text" name="lastname" id="lastname"> <br> 
							Gender <br>
						<select class="dropdown" id="gender" name="gender">
							<option value="Male">Male</option>
							<option value="Female">Female</option>
							<option value="Other">Other</option>
						</select> <br>
						Email <br>
						<input type="text" name="email" id="email" onkeyup="checkEmail()"
							onkeypress="checkEmail()" onchange="checkEmail()"> <br>
						Password <br>
						 <input
							type="password" name="password" id="password"> <br> Confirm
						Password <br> <input type="password" name="passconf"
							id="passconf"> <br> <br>
							<div class="formsection">Activity 1</div>
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
						<div class="formsection">Activity 2</div>
						<br> Type <br>
						<select class="dropdown" id="activity2" name="activity2">
							<option value="Running">Running</option>
							<option value="Gym">Gym</option>
							<option value="Cycling">Cycling</option>
							<option value="None">None</option>
						</select> <br> Experience <br>
						<select class="dropdown" id="experience2" name="experience2">
							<option value="Novice">Novice</option>
							<option value="Intermediate">Intermediate</option>
							<option value="Expert">Expert</option>
						</select> 
						<br> Day
						<br>
						<select class="dropdown" id="day2" name="day2">
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
						<select class="dropdown" id="time2" name="time2">
							<option value="6:00am - 9:00am">6:00am - 9:00am</option>
							<option value="6:00am - 9:00am">9:00am - 12:00pm</option>
							<option value="12:00pm - 3:00pm">12:00pm - 3:00pm</option>
							<option value="3:00pm - 6:00pm">3:00pm - 6:00pm</option>
							<option value="6:00pm - 9:00pm">6:00pm - 9:00pm</option>
						</select> <br><br><br>
							<a
							class="signupbtn" id="submit"
							onclick="signUp()">Sign Up</a>
					</div>
					
				</div>

			</form>
			
			<form method="POST" action="UserController" id="loginform">
				<div class="formcontainer" id="check">
					<div class="formtop">Log In
					<img onclick="closeLogin()" class="close" src="http://uxrepo.com/static/icon-sets/elegant/png32/24/000000/icon_close_alt-24-000000.png">
					</div>
					<div class="check">
						Email<br>
						<input type="text" name="email" id="email" onkeyup="checkEmail()"
							onkeypress="checkEmail()" onchange="checkEmail()"> <br>
						Password <br>
						 <input
							type="password" name="password" id="password"> <br> <br> <br>
							<a
							class="signupbtn" value="Submit" id="submit"
							onclick="logIn()">Log In</a>
							
					</div>
					
					
				</div>

			</form>
	</div>





</body>
</html>