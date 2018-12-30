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
	var socket = new SockJS('/card-game-websocket');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function (frame) {
		setConnected(true);
		console.log('Connected: ' + frame);
		stompClient.subscribe('/chat/1', function (message) {
			showUsername(JSON.parse(message.body).content);
		});
	});
}

function disconnect() {
	if (stompClient !== null) {
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}

function sendUsername() {
	stompClient.send("/app/chatterbox/1", {}, JSON.stringify({'username': $("#username").val()}));
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
