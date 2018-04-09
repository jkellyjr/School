
// Wait for load
window.addEventListener("load", Setup, true);


// An individual player
function Player() {
    this.name = "default";
    this.ships = [];
    this.ownBoard = [[]];
    this.targetBoard = [[]];
    this.score = 0;


    // Create a player and associated grids
    this.create = function() {
        this.name = prompt("Please enter your name: ");
        var boats = prompt("Please enter the locations of your boats: ");
        this.ships = this.parseBoats(boats);

        // Create 10x10 player grids
        this.ownBoard = this.createOwnGrid(10, 10, this.ships);
        this.targetBoard = this.createTargetGrid(10, 10);
    }


    // Parse boats
    this.parseBoats = function(boats) {
        var alphaNum = JSON.stringify(boats).replace(/\W/g, '').toLowerCase();
        var ships = alphaNum.match(/[a-j][0-9]/g);

        var shipLocs = [];

        if (ships !== null && ships.length === 6) {
            for (var i = 0; i < (ships.length - 1); i++) {
                var char1 = ships[i].charAt(0);
                var char2 = ships[i + 1].charAt(0);

                var num1 = parseInt(ships[i].substring(1, 2));
                var num2 = parseInt(ships[i + 1].substring(1, 2));

                // Vertical alignment
                if (char1 === char2) {
                    var diff = num2 - num1;

                    var shipType = null
                    switch (diff) {
                        case 4:
                            shipType = "A";
                            break;
                        case 3:
                            shipType = "B";
                            break;
                        case 2:
                            shipType = "S";
                            break;
                        default:
                            console.log("Error: unable to determine ship type");
                            break;
                    }

                    var charVal = parseInt(char1.charCodeAt(0) - 96);
                    for (var j = num1; j <= num2; j++) {
                        var rowLoc = (j -1)  * 10;
                        var xCell = charVal + rowLoc;
                        shipLocs.push(xCell +shipType);
                    }

                }
                // Horizontal alignment
                else if (num1 === num2) {
                    var diff = 0;

                    var val1 = parseInt(char1.charCodeAt(0));
                    var val2 = parseInt(char2.charCodeAt(0));

                    var min = 0;
                    var max = 0;

                    // Determine start and end
                    if (val1 >= val2) {
                        diff = val1 - val2;
                        min = val2;
                        max = val1;
                    } else {
                        diff = val2 - val1;
                        min = val1;
                        max = val2;
                    }

                    var shipType = null
                    switch (diff) {
                        case 4:
                            shipType = "A";
                            break;
                        case 3:
                            shipType = "B";
                            break;
                        case 2:
                            shipType = "S";
                            break;
                        default:
                            console.log("Error: unable to determine ship type");
                            break;
                    }

                    var rowLoc = (num1 - 1) * 10;
                    for (var j = min; j <= max; j++) {
                        var colLoc = j - 96;
                        var xCell = colLoc + rowLoc;
                        shipLocs.push(xCell + shipType);
                    }
                }
            }
        }
        else {
            alert("Error: invalid ship locations.");
        }
        return shipLocs.sort();
    }


    // Creates 2D array of user with coordinates
    this.createOwnGrid = function(rows, cols, ships) {
        var arr = [];
        var n = 1;
        var type = "";

        // Build grid row first
        for (var i = 0; i < rows; i++) {
            arr[i] = [];
            for (var j = 0; j < cols; j++) {

                // Search for match
                for (var k = 0; k < ships.length; k++) {
                    var xCell = parseInt(ships[k].match(/[0-9]*/g));
                    type = ships[k].match(/[ABS]/g);

                    if (xCell === n) {
                        arr[i][j] = type;
                        break;
                    }
                }

                if (arr[i][j] === null || arr[i][j] === undefined) {
                    arr[i][j] = "";
                }

                n++;
            }
        }
        return arr;
    }


    // Creates blank target grid
    this.createTargetGrid = function(rows, cols, ships) {
        var arr = [];
        for (var i = 0; i < rows; i++) {
            arr[i] = [];
            for (var j = 0; j < cols; j++) {
                arr[i][j] = "";
            }
        }
        return arr;
    }


    // Update the players score
    this.updateScore = function(points) {
        this.score += points;
    }


    // Remove the piece of the ship that was hit
    var a = 0; var b = 0; var s = 0;
    this.updateShips = function(id, shipType) {

        // Check if ship is destroyed
        if (shipType == "A") {
            a++;
            if (a == 5) {
                alert("You destroyed the aircraft carrier!");
            }
        }
        else if (shipType == "B") {
            b++;
            if (b == 4) {
                alert("You destroyed the battleship!");
            }
        }
        else {
            s++;
            if (s == 3) {
                alert("You destroyed the submarine!");
            }
        }

        var shipId = id + shipType;

        // Remove ship piece from list
        if (this.ships.includes(shipId)) {
            this.ships.splice(this.ships.indexOf(shipId), 1);
        }
        else {
            console.log("Error: ship id not found");
        }

        console.log("#: " + this.ships + " ship length: " + this.ships.length);

    }

}


// The game board
function Board(rows, cols) {
    this.row = rows;
    this.col = cols;

    // Handles all drawing of the grids
    this.drawBoard = function(playerGrid, opponentBoard, callback) {
        var grid = document.createElement('table');
        grid.className = 'grid';

        // Draw grid
        for (var i = 0; i < rows; i++) {
            var tr = grid.appendChild(document.createElement('tr'));
            for (var j = 0; j < cols; j++) {
                var cell = tr.appendChild(document.createElement('td'));

                // Indicate played cells and results
                if (playerGrid[i][j].includes("m")) {
                    cell.style.background = "white";
                }
                else if (playerGrid[i][j].includes("x")) {
                    cell.style.background = "red";
                }

                var gameArea = document.getElementById("battleship");

                // Attatch event listeners to cells of the target grid that have not been clicked already
                if (opponentBoard) {
                    if (!playerGrid[i][j].includes("x") && !playerGrid[i][j].includes("m")) {
                        cell.addEventListener('click', (function(i, j) {
                            return function() {
                                callback(i, j);
                            }
                        }) (i, j), false);
                    }


                    // Draw targetGrid
                    var target = document.createElement("targetGrid");
                    gameArea.appendChild(target);
                    if (target.hasChildNodes()) {
                        target.removeChild(target.childNodes[0]);
                    }
                    var tarChild = target.appendChild(grid);

                }
                // Show location of ships on their own board
                else {

                    if (playerGrid[i][j] == "m") {
                        cell.innerHTML = "";
                    } else if (playerGrid[i][j].includes("x")){
                        cell.innerHTML = playerGrid[i][j].match(/[ABS]/g);
                    } else {
                        cell.innerHTML = playerGrid[i][j];
                    }

                    // Draw own grid
                    var own = document.createElement("ownGrid");
                    gameArea.appendChild(own);

                    if (own.hasChildNodes()) {
                        own.removeChild(own.childNodes[0]);
                    }
                    var ownChild = own.appendChild(grid);
                }
            }
        }
    }


    // Handles drawing the leader board
    this.drawLeaderBoard = function(winners) {
        var leaderBoard = document.getElementById("leaderBoard");
        leaderBoard.className = "leaderBoard";

        var header = document.createElement("h3");
        header.appendChild(document.createTextNode("Leader Board: "));
        leaderBoard.appendChild(header);

        for (var i = 0; i <= 9; i++) {
            var text = "";

            if (winners[i] == undefined) {
                text = "This spot could be yours!";
            }
            else {
                text = winners[i].name + " scored " + winners[i].score + " points.";
            }

            var listItem = document.createElement("li");
            listItem.appendChild(document.createTextNode(text));
            leaderBoard.appendChild(listItem);
        }
    }

}



// Control for the game
function PlayGame(player1, player2, winners) {
    this.board = new Board(10, 10);
    this.player1 = player1;
    this.player2 = player2;
    this.winners = winners;

    // Draw the leader board
    board.drawLeaderBoard(this.winners);

    // Switch the user playing
    this.switchPlayer = function(player) {
        var upNext;

        if (player === this.player1) {
            upNext = this.player2;
        } else {
            upNext = this.player1;
        }

        this.clearDOM(upNext, player);
        var alert1 = document.createElement("switchPlayerAlert");
        alert1.addEventListener('click', alert("Click OKAY to being " + upNext.name + "'s turn."), this.draw(upNext, player));
    }


    // Draws the target board
    var gameEnded = false;
    this.drawTargetBoard = function(user, other) {
        board.drawBoard(user.targetBoard, true, function(row, col) {

            // Determine hit/miss
            var shotResult = other.ownBoard[row][col];
            if (shotResult == "A" || shotResult == "B" || shotResult == "S") {
                alert("Hit!");
                user.targetBoard[row][col] = "x";
                other.ownBoard[row][col] += "x";

                var id = (row * 10) + (col + 1);
                this.hit(user, other, shotResult, id);
            }
            else {
                alert("Miss.");
                user.targetBoard[row][col] = "m";
                other.ownBoard[row][col] = "m";
            }

            if (!gameEnded) {
                this.switchPlayer(user);
            }
        });
    }


    // Draws the user board
    this.drawOwnBoard = function(user) {
        board.drawBoard(user.ownBoard, false);
    }


    // When the player hits a ship, update score, and ship hits
    this.hit = function(player, other, shipType, id) {

        other.updateShips(id, shipType);

        // Update the score
        player.updateScore(2);
        other.updateScore(-2);

        // See if there are any ship pieces left
        if (other.ships.length == 0) {
            this.endGame(player);
        }
    }


    // Clears the dom and redraws it
    this.clearDOM = function() {
        var gameArea = document.getElementById("battleship");

        while (gameArea.firstChild) {
            gameArea.removeChild(gameArea.firstChild);
        }
    }


    // Helper to perpetuate cycle
    this.draw = function(player, other) {
        this.drawTargetBoard(player, other);
        this.drawOwnBoard(player);
    }


    // Starts the initial loop
    this.start = function() {
        alert("Click OKAY to begin " + player1.name +"'s turn.");
        this.drawTargetBoard(this.player1, this.player2);
        this.drawOwnBoard(this.player1);
    }


    // Controls the saving of the game and ending
    this.endGame = function(winner) {
        var playArea = document.getElementById("battleship");
        playArea.remove();

        gameEnded = true;

        var welcome = document.getElementById("welcome");
        welcome.remove();

        var mainMsg = document.getElementById("mainMsg");
        var head = document.createElement("h1");
        var congratsMsg = document.createTextNode("Congratulations! " + winner.name + " you won.");

        mainMsg.appendChild(head);
        head.appendChild(congratsMsg);

        this.savePlayer(winner);
    }


    // Save player information
    this.savePlayer = function(player) {
        console.log("attempting to save player");
        if (typeof(Storage) !== "undefined") {
            var index = localStorage.length;
            localStorage.setItem("battleship-" + index, JSON.stringify(player));
        } else {
            console.log("Error: unable to store locally <Player.savePlayer>");
        }
    }


    // Game entrance
    this.start();
}



function Setup() {
    this.player1 = new Player();
    this.player2 = new Player();

    this.player1.create();
    this.player2.create();

    this.winners = [];

    // Loads the list of previous winners
    this.loadWinners = function() {
        if (typeof(Storage) !== undefined) {
            for (var i = 0; i < localStorage.length; i++) {
                var player = JSON.parse(localStorage.getItem("battleship-" + i));
                console.log("player from local store: " + player.name + " <score>: " + player.score);
                this.winners.push(player);
            }
        }
    }


    // Compare wrt the players score
    this.compare = function(a, b) {
        if (a.score > b.score) {
            return -1;
        }
        else if (a.score < b.score) {
            return 1;
        }
        else {
            return 0;
        }
    }

    // Load, sort and return the top 10 winners
    this.loadWinners();
    this.winners.sort(this.compare);
    this.winners.slice(0, 9);

    // Initiate the game
    PlayGame(player1, player2, winners);
}
