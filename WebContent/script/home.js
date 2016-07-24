$(document).ready(function() {
	$("#welcome").html("<div class='topmenutext'>Welcome, " + window.localStorage.getItem("first") + "</div>");	
	
	getUserActivities();
	getUserMatches();
	getUserPartners();
})

function getUserActivities() {
	
	$.ajax({
        url: 'ActivityController?action=useractivities',
        data: 'userid=' + window.localStorage.getItem("id"),
        method:'get',
        success: function(data){
           var activities = data.split(",,");
           for(var i = 0; i < activities.length; i++){
        	   console.log(activities[i]);
        	   var row = activities[i].split(",");
        	   var li = document.createElement("li");
        	   li.setAttribute("class", "activityli");
        	   li.setAttribute("id", "activity" + row[0]);
        	   li.innerHTML = '&nbsp&nbsp' + row[5] + ' level ' + row[2] + ' on ' + row[3] + ' from ' + row[4]
        	   + '<img onclick="editActivity(this)" class="editimage" src="images/pencil.png">';
        	   document.getElementById("activitylist").appendChild(li);
           }
        }
   });
}

function getUserMatches(){

	$.ajax({
        url: 'MatchController?action=usermatches',
        data: 'userid=' + window.localStorage.getItem("id"),
        method:'get',
        success: function(data){
        	console.log(data);
           var matches = data.split(",,");
           console.log(matches);
           for(var i = 0; i < matches.length; i++){
        	   if(matches[i] != ""){
        		   if(i==0)
        			   $("#matchlist").html("");
	        	   console.log(matches[i]);
	        	   var row = matches[i].split(",");
	        	   var li = document.createElement("li");
	        	   li.setAttribute("class", "matchli");
	        	   li.setAttribute("id", "match" + row[0]);
	        	   li.innerHTML = '&nbsp&nbsp User: ' + row[6] + " " + row[7] + "<br>" +
	        	   "&nbsp&nbsp Activity: " + row[5] + ' level ' + row[2] + ' on ' + row[3] + ' from ' + row[4] +
	        	   "<div class='imagecontainer'><img onclick='deleteMatch(this)' class='activityimage' src='images/cross.png'>" +
	        	   "<img onclick='acceptMatch(this)' class='activityimage' src='images/accept.png'></div>";
	        	   document.getElementById("matchlist").appendChild(li);
        	   }
        	  
           }
        
 
			 $.ajax({
			        url: 'MatchController?action=userpending',
			        data: 'userid=' + window.localStorage.getItem("id"),
			        method:'get',
			        success: function(data){
			        	console.log(data);
			           var matches = data.split(",,");
			           console.log(matches);
			           for(var i = 0; i < matches.length; i++){
			        	   if(matches[i] != ""){
				        	   console.log(matches[i]);
				        	   var row = matches[i].split(",");
				        	   var li = document.createElement("li");
				        	   li.setAttribute("class", "matchli");
				        	   li.setAttribute("id", "match" + row[0]);
				        	   li.innerHTML = '&nbsp&nbsp User: ' + row[6] + " " + row[7] + "<br>" +
				        	   "&nbsp&nbsp Activity: " + row[5] + ' level ' + row[2] + ' on ' + row[3] + ' from ' + row[4] +
				        	   "<div class='imagecontainer'>Pending&nbsp<img onclick='cancelPending(this)' class='activityimage' src='images/cross.png'></div>";
				        	   document.getElementById("matchlist").appendChild(li);
			        	   }
			        	  
			           }
			           if (document.getElementById("matchlist").innerHTML == "")
			        	   document.getElementById("matchlist").innerHTML = "<div class='topmenutext'>No Matches Found, Please Check Back Soon or Try a Different Activity</div>"
			        }
			   });
        }
   });
}

function getUserPartners(){
	$.ajax({
        url: 'PartnerController?action=userpartners',
        data: 'userid=' + window.localStorage.getItem("id"),
        method:'get',
        success: function(data){
           var partners = data.split(",,");
           for(var i = 0; i < partners.length; i++){
        	   if(partners[i] != ""){        		  
	        	   var user = partners[i].split(",");
	        	   var id = user[0];
	        	   var first = user[1];
	        	   var last = user[2];
	        	   var gender = user[3]
	        	   var email = user[4];
	        	   var li = document.createElement("li");
	        	   li.setAttribute("class", "matchli");
	        	   li.setAttribute("id", "user" + id);
	        	   li.innerHTML = '&nbsp&nbsp' + first + " " + last + '<br>' +
	        	   '&nbsp&nbspEmail: ' + email + '&nbsp&nbsp&nbspGender: ' + gender + '<img onclick="deletePartner(this)" class="editimage" src="images/trash.png">';
	        	   document.getElementById("partnerlist").appendChild(li);
        	   }
           }
           if (document.getElementById("partnerlist").innerHTML == "")
        	   document.getElementById("partnerlist").innerHTML = "<div class='topmenutext'>No Partners Found, Accept a Match to Find One</div>";
       
        }
   });
}

function editActivity(element){
	window.localStorage.setItem("activityid", element.parentNode.id.replace("activity", ""));
	window.location = "activity.jsp";
}

function acceptMatch(element){
	console.log(element.parentNode.parentNode)
	$.ajax({
        url: 'MatchController?action=add',
        data: 'matchid=' + element.parentNode.parentNode.id.replace("match", ""),
        method:'get',
        success: function(data){
           document.getElementById("matchlist").innerHTML = "";
           getUserMatches();
           document.getElementById("partnerlist").innerHTML = "";
           getUserPartners();
        }
   });
}

function deleteMatch(element) {
	console.log(element.parentNode.parentNode.id.replace("match", ""));
	$.ajax({
        url: 'MatchController?action=delete',
        data: 'matchid=' + element.parentNode.parentNode.id.replace("match", ""),
        method:'get',
        success: function(data){
        	document.getElementById("matchlist").innerHTML = "";
            getUserMatches();
            document.getElementById("partnerlist").innerHTML = "";
            getUserPartners();
        }
   });
}

function cancelPending(element) {
	$.ajax({
        url: 'MatchController?action=cancel',
        data: 'matchid=' + element.parentNode.parentNode.id.replace("match", ""),
        method:'get',
        success: function(data){
        	document.getElementById("matchlist").innerHTML = "";
            getUserMatches();
            document.getElementById("partnerlist").innerHTML = "";
            getUserPartners();
        }
   });
}

function deletePartner(element){
	console.log(element.parentNode.id.replace("user", ""));
	$.ajax({
        url: 'PartnerController?action=delete',
        data: 'user1=' + element.parentNode.id.replace("user", "") + '&user2='+window.localStorage.getItem("id"),
        method:'get',
        success: function(data){
        	document.getElementById("matchlist").innerHTML = "";
            getUserMatches();
            document.getElementById("partnerlist").innerHTML = "";
            getUserPartners();
        }
   });
}


















