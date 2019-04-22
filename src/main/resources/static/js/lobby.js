document.addEventListener("DOMContentLoaded", event => {
  var roomList = [
    { id: 21, players: ["Player1", "Player2", "Player3"], status: 0 },
    { id: 28, players: ["Player19", "Player20", "Player155", "Player25", "Player87", "Player99", "Player72"], status: 0 },
    { id: 23, players: ["Player55", "Player13", "Player15", "Player4", "Player10"], status: 1 },
  ];

  var lobbyBox = document.getElementsByClassName('lobby-list')[0];

  function refreshRoomList() {
    lobbyBox.innerHTML = "";

    roomList.forEach(function (room) {
        var roomNode = createRoomNode(room);
        lobbyBox.appendChild(roomNode);
    });
  }

  function createRoomNode(room) {
    var roomLink = document.createElement("a");
    roomLink.classList.add("rlink");

    var roomNode = document.createElement("div");
    roomNode.classList.add("room");

    var roomNum = document.createElement("div");
    roomNum.classList.add("rnum");
    roomNum.innerHTML = room.id;

    var roomCount = document.createElement("div");
    roomCount.classList.add("rcount");
    roomCount.innerHTML = room.players.length + "/7";

    var roomPlayers = document.createElement("div");
    roomPlayers.classList.add("rplayers");
    room.players.forEach(function (player) {
      roomPlayers.innerHTML += player;
      roomPlayers.innerHTML += ", ";
    });

    var roomStatus = document.createElement("div");
    roomStatus.classList.add("rstatus");
    if(room.players.length >= 7) {
      roomStatus.innerHTML = "Pełny pokój";
      roomNode.classList.add("full");
    } else if(room.status) {
      roomStatus.innerHTML = "W trakcie";
      roomNode.classList.add("in_progress");
    }
    else
      roomStatus.innerHTML = "Nie rozpoczęta";

    roomNode.appendChild(roomNum);
    roomNode.appendChild(roomCount);
    roomNode.appendChild(roomPlayers);
    roomNode.appendChild(roomStatus);

    roomLink.appendChild(roomNode);

    return roomLink;
  }

  function joinRoom(e, roomNode) {
    var roomID = $(roomNode).find(".rnum").text();

    var room = roomList.filter(obj => {
      return obj.id === parseInt(roomID, 10)
    })

    if(!room[0]){
      alert("Pokój " + roomID + " nie istnieje.");
      return;
    }

    if(room[0].players.length >= 7){
      alert("Pokój " + roomID + " jest pełny.");
      return;
    }

    if(room[0].status){
      alert("Pokój " + roomID + " rozpoczął już grę.");
      return;
    }

    if(room[0]) {
      window.location = "room.html?id=" + roomID;
    } 

  }

  refreshRoomList();

  $(document).on('click', 'a.refreshList', function() {
    refreshRoomList();
  });

  $(document).on('click', 'a.rlink', function(e) {
    joinRoom(e, this);
  });

});