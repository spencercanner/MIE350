$(document).ready(function() {
	$("#signup").on("click", function() {
		console.log("Changed");
	});
	var form = $('#signupform');
	  
})


function signUp(){
	var first = $("#firstname").val();
	var patt = /@mail.utoronto.ca/;
	var last = $("#lastname").val();
	var email = $("#email").val();
	var gender = $("#gender").val();
	var pass = $("#password").val();
	var passconf = $("#passconf").val();
	console.log(first, last);
	if(first == "" || last == "" || first == " " || last == " " ){
		alert("Please enter a non-empty name")
	}
	else if (!patt.test(email)) {
		alert("Please enter a valid University of Toronto email address");
	}
	else if(pass == ""){
		alert("Please enter a non-empty password");
	}
	else if(pass != passconf){
		alert("Passwords do not match")
	}
	else{
		$.ajax({
		        url: 'UserController?action=signup',
		        data: $('#signupform').serialize(),
		        method:'get',
		        success: function(data){
		        	
		            if(data == "false"){
		            	alert("An account already exists with that email address");
		            }
		            else {
		            	var user = data.split(",");
		            	var id = user[0];
		            	var first = user[1];
		            	var last = user[2];
		            	var gender = user[3]
		            	var email = user[4];
		            	var password = user[5];
		            	console.log(first, last, email, id);
		            	window.localStorage.setItem("first",first); 
		            	window.localStorage.setItem("last",last); 
		            	window.localStorage.setItem("email",email); 
		            	window.localStorage.setItem("id", id); 
		            	window.localStorage.setItem("gender", gender);
		            	window.localStorage.setItem("password", password);
		            	window.location = "home.jsp";
		            }
		        }
		   });
	}
}

function logIn(){
    $.ajax({
        url: 'UserController?action=login',
        data: $('#loginform').serialize(),
        method:'get',
        success: function(data){
        	console.log(data);
            if(data == "false"){
            	alert("Incorrect email and/or password");
            }
            else {
            	var user = data.split(",");
            	var id = user[0];
            	var first = user[1];
            	var last = user[2];
            	var gender = user[3]
            	var email = user[4];
            	var password = user[5];
            	console.log(first, last, email, id);
            	window.localStorage.setItem("first",first); 
            	window.localStorage.setItem("last",last); 
            	window.localStorage.setItem("email",email); 
            	window.localStorage.setItem("id", id); 
            	window.localStorage.setItem("gender", gender);
            	window.localStorage.setItem("password", password);
            	window.location = "home.jsp";
            }
        }
   });
}

function showSignup () {
	$("#create").animate({
		left: "25%",
		display: "block"
	}, 500);
	$("#create").show();
	
}

function showLogin () {
	$("#check").animate({
		left: "25%"
	}, 500);
	$("#check").show();
}

function closeSignup() {
	$("#create").animate({
		left: "100%"
	}, 500, function(){
		$("#create").css("display", "none");
	});
}

function closeLogin () {
	$("#check").animate({
		left: "-100%",
		display: "none"
	}, 500, function(){
		$("#check").css("display", "none")
	});
}

function checkEmail() {
	var value = $("#email").val();
	var patt = /@mail.utoronto.ca/;
	if (patt.test(value)) {
		$("#email").css("border", "3px solid green");
	} else {
		$("#email").css("border", "3px solid red");
	}
}

function personalClick() {
	console.log("personal click");
	var first = $("#first").val();
	var patt = /@mail.utoronto.ca/;
	var last = $("#last").val();
	var email = $("#email").val();
	var gender = $("#gender").val();
	var pass = $("#pass").val();
	var passconf = $("#passconf").val();

	if (first == "" || last == "" 
			|| !patt.test(email) || pass != passconf || pass == "" || passconf == "") {
		var l = 5;
		for (var i = 0; i < 4; i++) {
			$(".personalform").animate({
				'margin-left' : "+=" + (l = -l) + 'px',
				'margin-right' : "-=" + l + 'px'
			}, 50);
			$(".personalform").animate({
				left : "20%",
				'margin-left': "0",
				'margin-right': "0"
			}, 50);
		}
		$(".personalform").animate({
			left : "20%",
			'margin-left': "0",
			'margin-right': "0"
		}, 50);
	} else {
		$(".personalform").animate({
			left : "-70%",
			'margin-left': "0",
			'margin-right': "0"
		}, 500);
		$(".activityform").animate({
			left : "20%"
		}, 500);
	}
}
