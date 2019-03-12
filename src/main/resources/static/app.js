var stompClient = null;

function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
	if (connected) {
		$("#chatterbox").show();
	}
	else {
		$("#conversation").hide();
	}
	$("#messages").html("");
}

function connect() {
	var socket = new SockJS('/game-socket');
	console.log("Session checking...:");
	var session_val = "null"
	$.get("/session",
	function(data, status){
		session_val = console.log("Session id:" + data);
	});
	//$("#messages").append("<tr><td>" + session_val + "</td></tr>");
	//stompClient = Stomp.over(socket);
	//stompClient.connect({}, function (frame) {
		//setConnected(true);
		//console.log('Connected: ' + frame);
		//stompClient.subscribe('/game/1/2', function (message) {
		//	showUsername(JSON.parse(message.body).content);
		//});
		//stompClient.subscribe('/game/1/1', function (message) {
		//	showUsername(JSON.parse(message.body).content);
		//});
	//});
}

function disconnect() {
	if (stompClient !== null) {
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}

function sendUsername() {
	stompClient.send("/app/activegames/start", {}, JSON.stringify({author: 'tom', content: 'hello' }));
	//stompClient.send("/app/activegames/1/1", {}, JSON.stringify({author: 'tom', content: 'hello' }));
}

function showUsername(message) {
	$("#messages").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendUsername(); });
});
