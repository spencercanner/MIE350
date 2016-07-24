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
<script src="script/account.js"></script>


<title>Fit Together - My Account</title>

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
		<form method="POST" action="UserController" id="editform">
				<div class="formcontainer" id="edit">
					<div class="formtop">Edit Account 
					</div>
					<div class="edit">
						First Name <br>
						<input type="text" name="firstname"	id="firstname"> <br> 
							Last Name <br>
							<input type="text" name="lastname" id="lastname"> <br> 
							Gender
						<br> 
						<select class="dropdown" id="gender" name="gender">
							<option value="Male">Male</option>
							<option value="Female">Female</option>
							<option value="Other">Other</option>
						</select> <br>
						Email (Not Editable) <br>
						<input readonly type="text" name="email" id="email"> <br>
						Password <br>
						 <input
							type="password" name="password" id="password"> <br> Confirm
						 Password <br>
						 <input type="password" name="passconf"
							id="passconf">
						<br><br>
							<a
							type="submit" class="signupbtn" id="submit"
							onclick="updateUser()">Save Changes</a>
							<br><br><br>
							<a
							type="submit" class="signupbtn red" id="delete"
							onclick="deleteUser()">Delete Profile</a>
					</div>
					
				</div>

			</form>
	</div>





</body>
</html>