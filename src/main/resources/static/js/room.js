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


  function refreshStartBtn() {}

  refresh();
});