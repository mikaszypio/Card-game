var dummyItems = [
	{ name: "winchester", b: "blue", suit: "tiles", symbol: "7" },
	{ name: "mustang", b: "blue", suit: "hearts", symbol: "4" },
	{ name: "barrel", b: "blue", suit: "pikes", symbol: "J" },
];
var players = [
	{ id: 1, name: "Player2", role:1, ch: 1, hp: 5, cards: 5, items: [dummyItems[0]] },
	{ id: 30, name: "Player4", ch: 2, hp: 0, cards: 4, items: [] },
	{ id: 19, name: "James", ch: 3, hp: 4, cards: 4, items: [dummyItems[2], dummyItems[0],dummyItems[2], dummyItems[0],dummyItems[2], dummyItems[0]] },
	{ id: 2, name: "Billy", ch: 5, hp: 3, cards: 3, items: [] },
	{ id: 127, name: "Player121", ch: 4, hp: 3, cards: 2, items: [] },
	{ id: 34, name: "Xx_CounterStriker_xX", ch: 9, hp: 4, cards: 3, items: [dummyItems[1]] },
	{ id: 55, name: "Username33", ch: 10, hp: 1, cards: 0, items: [] }
];

document.addEventListener("DOMContentLoaded", event => {
	var prevBox = document.getElementById("gb-preview");
	var playersBox = document.getElementById("gb-players");
	var handBox = document.getElementById("gb-hand");
	var stackBox = document.getElementById("gb-stack");

  var boardPts = {
    left: 114, 	center: 400, 	right: 686,
    top: 30, 		middle: 230, 	bottom: 370,
    left33: 256, left66: 544,
    middleUp: 148, middleDown: 302,
  };

	// juz posortowana kolejnosc graczy gdzie 1 rekord to widok pierwszej osoby

	var playersPos = {
		2: {
			p2: { x: boardPts.center, y: boardPts.top}
		},
		3: {
			p2: { x: boardPts.left, y: boardPts.middleUp},
			p3: { x: boardPts.right, y: boardPts.middleUp}
		},
		4: {
			p2: { x: boardPts.left, y: boardPts.middle},
			p3: { x: boardPts.center, y: boardPts.top},
			p4: { x: boardPts.right, y: boardPts.middle}
		},
		5: {
			p2: { x: boardPts.left, y: boardPts.middle},
			p3: { x: boardPts.left33, y: boardPts.top},
			p4: { x: boardPts.left66, y: boardPts.top},
			p5: { x: boardPts.right, y: boardPts.middle}
		},
		6: {
			p2: { x: boardPts.left, y: boardPts.middleDown},
			p3: { x: boardPts.left, y: boardPts.middleUp},
			p4: { x: boardPts.center, y: boardPts.top},
			p5: { x: boardPts.right, y: boardPts.middleUp},
			p6: { x: boardPts.right, y: boardPts.middleDown}
		},
		7: {
			p2: { x: boardPts.left, y: boardPts.middleDown},
			p3: { x: boardPts.left, y: boardPts.middleUp},
			p4: { x: boardPts.left33, y: boardPts.top},
			p5: { x: boardPts.left66, y: boardPts.top},
			p6: { x: boardPts.right, y: boardPts.middleUp},
			p7: { x: boardPts.right, y: boardPts.middleDown}
		}
	};
	var hand = [
		{ name: "mustang", b: "orange", suit: "tiles", symbol: "Q" },
		{ name: "barrel", b: "orange", suit: "hearts", symbol: "Q" },
		{ name: "dynamite", b: "orange", suit: "pikes", symbol: "10" },
		{ name: "volcanic", b: "orange", suit: "clovers", symbol: "2" },
		{ name: "winchester", b: "orange", suit: "hearts", symbol: "J" }
	];
	var stack = [
		{ name: "bang", b: "orange", suit: "tiles", symbol: "Q" }
	];
	var turnID = 1;
  
  var charDictionary = {
    1: { name: "Bart Cassady", desc: "Za każdym razem, gdy Bart zostanie trafiony, dobiera kartę."},
    2: { name: "Black Jack", desc: "Pokazuje drugą kartę, którą dobrał. Jeśli jest to kier lub karo, dobiera kolejną kartę."},
    3: { name: "Calamity Janet", desc: "Może używać kart Bang! jako Pudło! i na odwrót"},
    4: { name: "El Gringo", desc: "Za każdym razem, gdy zostanie trafiony przez gracza, zabiera mu losową kartę z ręki."},
    5: { name: "Jesse Jones", desc: "Pierwszą dobieraną kartę może wziąć z ręki dowolnego gracza."},
    6: { name: "Jourdonnais", desc: "Jeżeli odkryje kier podczas Pokera!, unika trafienia."},
    7: { name: "Kit Carlson", desc: "Przy dobieraniu kart ogląda trzy górne karty i zachowuje dwie z nich."},
    8: { name: "Lucky Duke", desc: "Przy Pokerze!, odkrywa dwie karty i wybiera tę, która mu bardziej odpowiada."},
    9: { name: "Paul Regret", desc: "Zasięg pomiędzy nim, a innymi graczami zostaje zwiększony o 1."},
    10: { name: "Pedro Ramirez", desc: "Może wybrać wierzchnią kartę ze stosu kart odrzuconych, zamiast pierwszej z dobieranych kart."},
    11: { name: "Rose Doolan", desc: "Widzi wszystkich graczy w zasięgu obniżonym o 1."},
    12: { name: "Sid Ketchum", desc: "Może odrzucić dwie karty z ręki, aby odzyskać punkt zdrowia."},
    13: { name: "Slab Zabojca", desc: "Inni gracze potrzebują dwóch kart Pudła!, aby anulować zagrany przez niego Bang!"},
    14: { name: "Suzy Lafayette", desc: "Kiedy zabraknie jej kart na ręce, dobiera kartę z talii."},
    15: { name: "Sam Sęp", desc: "Otrzymuje wszystkie karty wyeliminowanego gracza."},
    16: { name: "Willy the Kid", desc: "Może zagrać dowolną liczbę kart Bang! w swojej turze."}
  }
  
  var cardDictionary = {
    "bang": { name: "Bang!", desc: "Zadaje <b>1</b> punkt życia wybranemu graczowi." },
    "saloon": { name: "Saloon", desc: "Przywraca <b>2</b> punkty życia rzucającemu Saloon oraz <b>1</b> punkt pozostałym graczom." },
    "duel": { name: "Pojedynek", desc: "Rzuca pojedynek wybranemu graczowi. Pojedynkujący się rzucają nawzajem karty Bang! Pierwszy gracz, który nie będzie w stanie rzucić karty Bang!, przegrywa pojedynek i traci <b>1</b> punkt życia." },
    "catbalou": { name: "Kasia Balou", desc: "Niszczy dowolną kartę u wybranego gracza." },
    "indians": { name: "Indianie", desc: "Wszyscy oprócz rzucającego muszą wyrzucić kartę Pudło! inaczej tracą <b>1</b> punkt życia." },
    "gatling": { name: "Gatling", desc: "Wszyscy oprócz rzucającego muszą wyrzucić kartę Bang! inaczej tracą <b>1</b> punkt życia." },
    "wellsfargo": { name: "Wells Fargo", desc: "Pozwala dobrać <b>3</b> karty z talii." },
    "stagecoach": { name: "Dyliżans", desc: "Pozwala dobrać <b>2</b> karty z talii." },
    "beer": { name: "Piwko", desc: "Przywraca <b>1</b> punkt życia." },
    "panic": { name: "Panika", desc: "Zabiera dowolną kartę u dowolnego gracza w zasięgu <b>1</b>." },
    "missed": { name: "Pudło!", desc: "Anuluje efekt Bang!" },
    "generalstore": { name: "Sklep", desc: "Odkrywa tyle kart z talii ilu graczy uczestniczy w rozgrywce. Następnie, zaczynając od siebie, każdy gracz dobiera <b>1</b> kartę z odkrytego stosu." },
    "mustang": { name: "Mustang", desc: "Zwiększa zasięg pomiędzy rzucającym, a innymi graczami o <b>1</b>." },
    "volcanic": { name: "Volcanic", desc: "Zasięg: <b>1</b><br>Pozwala zagrać dowolną ilość kart Bang! podczas jednej tury." },
    "schofield": { name: "Schofield", desc: "Zasięg: <b>2</b>" },
    "remington": { name: "Remington", desc: "Zasięg: <b>3</b>" },
    "winchester": { name: "Winchester", desc: "Zasięg: <b>5</b>" },
    "revcarabine": { name: "Rev. Carbine", desc: "Zasięg: <b>4</b>" },
    "scope": { name: "Luneta", desc: "Pozwala dostrzec wszystkich graczy w zasięgu obniżonym o <b>1</b>." },
    "dynamite": { name: "Dynamit", desc: "" },
    "barrel": { name: "Baryłka", desc: "Kiedy gracz jest celem karty Bang!, może sprawdzić Poker! w celu uniknięcia obrażeń." },
    "jail": { name: "Więzienie", desc: "Pozwala uwięzić dowolnego gracza oprócz szerfya. Uwięziony gracz musi sprawdzić Poker! zanim rozpocznie swoją turę. Jeżeli odkryje karo, jego tura przebiega w normalny sposób, inaczej traci turę. Po sprawdzeniu odrzuca Więzienie." },
  }
	

	$(document).off('mousemove', '.gb-card').on('mousemove', '.gb-card', function(e) {
		previewCard(e, this);
	});

	$(document).off('mouseout', '.gb-card').on('mouseout', '.gb-card', function(e) {
		$( "#gb-preview" ).empty();
	});

	$(document).off('mousemove', '.gb-portrait').on('mousemove', '.gb-portrait', function(e) {
		previewCharacter(e, this);
	});

	$(document).off('mouseout', '.gb-portrait').on('mouseout', '.gb-portrait', function(e) {
		$( "#gb-preview" ).empty();
	});
  
	$(document).off('mousemove', '.gb-icon').on('mousemove', '.gb-icon', function(e) {
		previewItem(e, this);
	});

	$(document).off('mouseout', '.gb-icon').on('mouseout', '.gb-icon', function(e) {
		$( "#gb-preview" ).empty();
	});  

	$( "#btt1" ).click(function() {
		 addCardToHand(bangCard);
	 });
	 $( "#btt2" ).click(function() {
		removeCardFromHand();
	});
	$( "#btt3" ).click(function() {
		nextTurn();
	});
	$( "#btt4" ).click(function() {
		targetPlayer();
	});
	

//Function declarations------------------------------------------------------------------------
function addCardToHand(card) {
	hand.push(card);
	drawHand();
}

function removeCardFromHand() {
	hand.pop();
	drawHand();
}
function nextTurn(){
	var i=0;
	while(turnID !== players[i].id || i >= players.length){
		i++;
	}
	if(i+1<players.length){
		turnID = players[i+1].id;
	}else{
		turnID = players[0].id;
	}
	draw();
	
	if(turnID === players[0].id){
		myTurn();
	}
}
function changeTurnRNG(){
	var playerID =  Math.floor(Math.random() * players.length);
	turnID = players[playerID].id;
	draw();
	
	if(turnID === players[0].id){
		myTurn();
	}
}

function previewCard(e, cardNode) {
	prevBox.innerHTML = "";

	var prevCard = cardNode.cloneNode(true);
  var cardName = prevCard.classList.item(1).split("-").pop();

	prevCard.classList.remove("gb-small");
	prevCard.classList.add("gb-medium");
	prevCard.removeAttribute("style");

	var prevDesc = document.createElement("div");
	prevDesc.classList.add("gb-card-desc");
  
  var prevName = document.createElement("span");
  prevName.classList.add("gb-card-name");
	prevName.innerHTML = cardDictionary[cardName].name;
  
  var prevUsage = document.createElement("span");
  prevUsage.classList.add("gb-card-usage");
  prevUsage.innerHTML = cardDictionary[cardName].desc;
  
  prevDesc.appendChild(prevName);
  prevDesc.appendChild(prevUsage);
	
	prevBox.appendChild(prevCard);
	prevBox.appendChild(prevDesc);

	calculatePreviewPosition(e);
}

function previewCharacter(e, charNode) {
	prevBox.innerHTML = "";

	var prevChar = charNode.cloneNode(true);
  var charID = String(prevChar.className.match(/gb-ch\d+/)).split("ch").pop();

	prevChar.classList.add("gb-large");

	var prevDesc = document.createElement("div");
	prevDesc.classList.add("gb-char-desc");
  
  var prevName = document.createElement("span");
  prevName.classList.add("gb-char-name");
	prevName.innerHTML = charDictionary[charID].name;
  
  var prevPerk = document.createElement("span");
  prevPerk.classList.add("gb-char-perk");
  prevPerk.innerHTML = charDictionary[charID].desc;
  
  prevDesc.appendChild(prevName);
  prevDesc.appendChild(prevPerk);

	prevBox.appendChild(prevChar);
	prevBox.appendChild(prevDesc);

	calculatePreviewPosition(e);
}

function previewItem(e, itemNode) {
	prevBox.innerHTML = "";

	var prevItem = itemNode.cloneNode(true);
  var itemName = prevItem.classList.item(0).split("-").pop();

	prevItem.classList.remove("gb-small", "gb-icon");
	prevItem.classList.add("gb-medium", "gb-card");
  prevItem.getElementsByClassName("gb-card-symbol")[0].removeAttribute("style");

	var prevDesc = document.createElement("div");
	prevDesc.classList.add("gb-card-desc");
  
  var prevName = document.createElement("span");
  prevName.classList.add("gb-card-name");
	prevName.innerHTML = cardDictionary[itemName].name;
  
  var prevUsage = document.createElement("span");
  prevUsage.classList.add("gb-card-usage");
  prevUsage.innerHTML = cardDictionary[itemName].desc;
  
  prevDesc.appendChild(prevName);
  prevDesc.appendChild(prevUsage);
	
	prevBox.appendChild(prevItem);
	prevBox.appendChild(prevDesc);

	calculatePreviewPosition(e);
}

function calculatePreviewPosition(e) {
	var offset = $('#gameboard').offset();
	var cursor = {
		x: e.clientX - offset.left,
		y: e.clientY - offset.top
	}
	var prevBoxHeight = calculatePreviewHeight();
	var prevBoxWidth = prevBox.offsetWidth;
	var margin = 4;

	var prevBoxLeft = cursor.x;
	var prevBoxTop = cursor.y;

	if(cursor.x > 400)
		prevBoxLeft -= (prevBoxWidth + margin);
	else
		prevBoxLeft += margin;

	if(cursor.y > 300)
		prevBoxTop -= (prevBoxHeight + margin);
	else
		prevBoxTop += margin;

	prevBox.style.left = prevBoxLeft + "px";
	prevBox.style.top = prevBoxTop + "px";
}

function calculatePreviewHeight() {
	var descTop = parseInt($("#gb-preview div[class$='desc']").css('top'), 10);
	var descHeight = $("#gb-preview div[class$='desc']").outerHeight(true);

	return descTop + descHeight;
}

function drawCard(card, size = "small"){
	var cardNode = document.createElement("div");

	var cardName = "gb-" + card.name;
	var cardBorder = "gb-b-" + card.b;
	var cardSuit = "gb-" + card.suit;
	var cardSize = "gb-" + size;

	cardNode.classList.add("gb-card", 
		cardName, cardBorder, cardSuit, cardSize);

	var cardSymbolNode = document.createElement("span");
	cardSymbolNode.classList.add("gb-card-symbol");
	cardSymbolNode.innerHTML = card.symbol;

	cardNode.appendChild(cardSymbolNode);

	return cardNode;
}

function whichChild(elem){
	var  i= 0;
	while((elem=elem.previousSibling)!=null) ++i;
	return i;
}

function drawHand() {
	handBox.innerHTML = "";
	var handWidth = handBox.offsetWidth;
	var cardsCount = hand.length;
	for(var i=0; i<cardsCount; i++) {
		card = drawCard(hand[i]);
		handBox.appendChild(card);
	}
	var cards = handBox.getElementsByClassName("gb-card");

	if(handWidth < cardsCount * 72){
		var cardSpacing = (handWidth - 72) / (cardsCount - 1);

		for(let i = 0; i < cardsCount-1; ++i){
			cards[i].style.left = i * cardSpacing + "px";
		}
		cards[cardsCount-1].style.left = handWidth - 72 + "px";
	} else {
		var center = Math.abs((handWidth - (cardsCount * 72)) / 2);

		for(let i = 0; i < cardsCount; ++i){
			cards[i].style.left = (i * 72 + center) + "px";
		}
	}
}

function drawStack(card = stack[0],size = "small") {
	stackBox.style.left = boardPts.center + "px";
	stackBox.style.top = boardPts.middle + "px";
  
	var cardNode = drawCard(card);
	stackBox.appendChild(cardNode);
}

function draw(){
	drawPlayers();
	drawHand();
	drawStack();
}



function drawPlayers() {
	playersBox.innerHTML = "";
	var i = 0;
	var len = players.length;

	for(var i=0; i<len; i++) {
		var playerNode = document.createElement("div");
		playerNode.id = players[i].id;
		playerNode.classList.add("gb-player");

		if(turnID == players[i].id){
			playerTurn = document.createElement("div");
			playerTurn.classList.add("gb-turn");

			playerNode.appendChild(playerTurn);

		}

		var playerRole = document.createElement("div");
		playerRole.classList.add("gb-role");
		if('role' in players[i]){
			playerRole.style.display = "block";
			playerRole.classList.add("gb-r"+players[i].role);
		} else {
			playerRole.style.display = "none";
		}

		var playerCharacter = document.createElement("div");
		playerCharacter.classList.add("gb-portrait", "gb-ch"+players[i].ch);

		var playerInfo = document.createElement("div");
		playerInfo.classList.add("gb-info");

		var playerName = document.createElement("div");
		playerName.classList.add("gb-name");
		playerName.innerHTML = players[i].name;

		var playerStats = document.createElement("div");
		playerStats.classList.add("gb-stats");

		var playerHealth = document.createElement("div");
		playerHealth.classList.add("gb-hp");
		playerHealth.innerHTML = players[i].hp;

		var playerCards = document.createElement("div");
		playerCards.classList.add("gb-cards");
		playerCards.innerHTML = players[i].cards;

		playerStats.appendChild(playerHealth);
		playerStats.appendChild(playerCards);

		var playerItems = document.createElement("div");
		playerItems.classList.add("gb-items");

		players[i].items.forEach(function (item) {
		var itemNode = drawCard(item);
		itemNode.classList.remove("gb-card");
		itemNode.classList.remove("gb-small");
		itemNode.getElementsByClassName("gb-card-symbol")[0].style.display = "none";
		itemNode.classList.add("gb-icon");
		playerItems.appendChild(itemNode);
		});

		playerInfo.appendChild(playerName);
		playerInfo.appendChild(playerStats);
		playerInfo.appendChild(playerItems);

		playerNode.appendChild(playerRole);
		playerNode.appendChild(playerCharacter);
		playerNode.appendChild(playerInfo);

		if(i==0) {
			playerNode.style.left = boardPts.center + "px";
			playerNode.style.top = boardPts.bottom + "px";
		} else {
			playerNode.style.left = playersPos[len]['p'+(i+1)].x + "px";
			playerNode.style.top = playersPos[len]['p'+(i+1)].y + "px";
		}
		if(playerNode)
		playersBox.appendChild(playerNode);
	}
}
function myTurn(){
	draw();
	let container = document.querySelector('#gb-hand');
	let element = container.querySelectorAll('.gb-card');
	element.forEach(function(el) {
		el.onclick = function () {
		console.log(hand[whichChild(this)].name);
		stompClient2.send("/app/activegames/1/"+players[0].id, {}, JSON.stringify({'author': players[0].name ,'content': hand[whichChild(this)].name}));
		};
	});
}


function targetPlayer(){
	draw();
	let element = document.querySelectorAll('.gb-player');
	element.forEach(function(el) {
		el.onclick = function () {
		console.log(players[whichChild(this)].name);
		};
	});
}

	$( "#msg" ).off().on('keyup', function (e) {
		if (e.keyCode == 13) {
			console.log("\""+$( "#msg" ).val()+"\"");
			stompClient1.send("/app/chatterbox/1", {}, JSON.stringify({'author': players[0].name ,'content': $( "#msg" ).val()}));
		}
	});
draw();
if(turnID === players[0].id){
	myTurn();
}
});

var stompClient1 = null;
var stompClient2 = null;
function connect_chat() {
	var socket = new SockJS('/chat-socket');
	stompClient1 = Stomp.over(socket);
	stompClient1.connect({}, function (frame) {		
		//console.log('Connected: ' + frame);
		try{
		stompClient1.subscribe('/chat/1', function (message) {
			
			postMessage(JSON.parse(message.body));
			
		});}catch(e){};
	});
}
function connect_game() {
	var socket = new SockJS('/game-socket');
	stompClient2 = Stomp.over(socket);
	stompClient2.connect({}, function (frame) {		
		//console.log('Connected: ' + frame);
		try{
		stompClient2.subscribe("/game/1/"+players[0].id, function (message) {
			
			game_response(JSON.parse(message.body));
			
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
function game_response(message) {
	console.log(message);
}
connect_chat();
connect_game();