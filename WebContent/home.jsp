<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fit Together - Home</title>
<!-- <link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css"> -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="script/home.js"></script>
<!-- <link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css"> -->
	<link rel="stylesheet" type="text/css" href="style/theme.css" />
</head>
<body>
 <div id="wrap">
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
	<div class="homecontent">
		<div class="homesection" id="mymatches">
			<div class="formtop">My Matches</div> 
			<div class="matches">
			<ul id="matchlist" class="matchlist"></ul>
				
			</div>			
		</div>
		<div class="homesection" id="myactivities">
			<div class="formtop">My Activities</div> 
			<div class="activities">
			<ul id="activitylist" class="activitylist"></ul>
				
			</div>			
		</div>
		<div class="homesection" id="mypartners">
			<div class="formtop">My Partners</div> 
			<div class="partners">
			<ul id="partnerlist" class="partnerlist"></ul>
				
			</div>			
		</div>
	</div>
	
 </div>
</body>
</html>