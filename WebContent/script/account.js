$(document).ready(function() {
	$("#welcome").html("<div class='topmenutext'>Welcome, " + window.localStorage.getItem("first") + "</div>");
	$("#firstname").val(localStorage.getItem("first"));
	$("#lastname").val(localStorage.getItem("last"));
	$("#email").val(localStorage.getItem("email"));
	$("#gender").val(localStorage.getItem("gender"));
	$("#password").val(localStorage.getItem("password"));
	$("#passconf").val(localStorage.getItem("password"));	
})

function updateUser (){
	if($("#password").val == $("#passconf").val){
		$.ajax({
	        url: 'UserController?action=update',
	        data: $("#editform").serialize(),
	        method:'get',
	        success: function(data){
	        	console.log(data);
	        	var user = data.split(",");
	        	var id = user[0];
	        	var first = user[1];
	        	var last = user[2];
	        	var gender = user[3];
	        	var email = user[4];
	        	var password = user[5];
	        	console.log(first, last, email, id);
	        	window.localStorage.setItem("first",first); 
	        	window.localStorage.setItem("last",last); 
	        	window.localStorage.setItem("email",email); 
	        	window.localStorage.setItem("gender", gender);
	        	window.localStorage.setItem("password", password);
	        	window.location = "home.jsp?nocache=" + (new Date()).getTime();
	        }
		
	   });
	}
	else {
		alert("Passwords do not match");
	}
}

function deleteUser() {
	$.ajax({
        url: 'UserController?action=delete',
        data: 'userid=' + window.localStorage.getItem("id"),
        method:'get',
        success: function(data){
        	window.localStorage.setItem("first", "");
        	localStorage.setItem("last", "");
        	localStorage.setItem("email", "");
        	localStorage.setItem("gender", "");
        	localStorage.setItem("password", "");	
        	window.location = "index.jsp?nocache=" + (new Date()).getTime();

         }
	
   });
}