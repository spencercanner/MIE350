$(document).ready(function() {
	$("#welcome").html("<div class='topmenutext'>Welcome, " + window.localStorage.getItem("first") + "</div>");
	
	$.ajax({
        url: 'ActivityController?action=fetch',
        data: 'activityid=' + window.localStorage.getItem("activityid"),
        method:'get',
        success: function(data){
        	console.log(data);
        	var row = data.split(",");
        	var activityname = row[2];
        	var day = row[3];
        	var time = row[4];
        	var experience = row[5];
        	$("#activity").val(activityname);
        	$("#experience").val(experience);
        	$("#day").val(day);
        	$("#time").val(time);
        }
	
   });
})

function updateActivity(){
	$.ajax({
        url: 'ActivityController?action=update',
        data: 'activityid=' + window.localStorage.getItem("activityid") +
        '&activity=' + $("#activity").val() + "&experience=" + $("#experience").val() +
        '&day=' + $("#day").val() +"&time=" + $("#time").val() + "&user=" + window.localStorage.getItem("id"),
        method:'get',
        success: function(data){
        	window.location = "home.jsp?nocache=" + (new Date()).getTime();
        }
	
   });
}