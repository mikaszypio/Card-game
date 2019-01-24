document.addEventListener("DOMContentLoaded", event => {
  var players = [
    { id: 23, name: "Player2", rank: 10 },
    { id: 33, name: "Player3", rank: 0 },
    { id: 30, name: "Player4", rank: 2 }
  ];

  function refresh() {
    refreshRoomTitle();
    refreshPlayersList();
    refreshStartBtn();
  }

  function refreshRoomTitle() {
    var url = new URL(window.location.href);
    var roomID = url.searchParams.get("id");

    var titleNode = document.getElementsByClassName('title-bar')[0];

    var titleTextNode = document.createElement("h2");
    titleTextNode.innerHTML = "Pokój " + roomID;

    titleNode.appendChild(titleTextNode);
  }

  function refreshPlayersList() {
    var playersListNode = document.getElementsByClassName('players-list')[0];

    players.forEach(function (player) {
        var playerNode = document.createElement("div");
        playerNode.id = player.id;
        playerNode.classList.add("player-cell");

        var playerName = document.createElement("div");
        playerName.classList.add("name");
        playerName.innerHTML = player.name;

        var playerRank = document.createElement("div");
        playerRank.classList.add("rank");
        playerRank.innerHTML = "Wygrane: " + player.rank;

        playerNode.appendChild(playerName);
        playerNode.appendChild(playerRank);

        playersListNode.appendChild(playerNode);
    });
  }
  $( "#msg" ).off().on('keyup', function (e) {
		if (e.keyCode == 13) {
			console.log("\""+$( "#msg" ).val()+"\"");
			stompClient.send("/app/chatterbox/1", {}, JSON.stringify({'author': players[0].name ,'content': $( "#msg" ).val()}));
		}
	});

  function refreshStartBtn() {}

  refresh();
});
var stompClient = null;
function connect_chat() {
	var socket = new SockJS('/chat-socket');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function (frame) {		
		//console.log('Connected: ' + frame);
		try{
		stompClient.subscribe('/chat/1', function (message) {
			
			postMessage(JSON.parse(message.body));
			
		});}catch(e){};
	});
}
function postMessage(message) {
	let wiadomosc = document.createElement("div");
	wiadomosc.classList.add("chat-msg");
	let autor = document.createElement("span");
	autor.classList.add("chat-msg-username");
	autor.innerHTML = message.author;
	let tresc = document.createElement("span");
	tresc.classList.add("chat-msg-text");
	tresc.innerHTML = message.content;
	wiadomosc.appendChild(autor);
	wiadomosc.appendChild(tresc);
	$(".chat-list").append(wiadomosc);
}
connect_chat();