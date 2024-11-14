<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Game Players</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>
    <!-- SockJS and Stomp -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
</head>
<body>
<#include "part/navbar.ftlh">

<div class="container mt-5">
    <h1 class="mb-4">Players in Game</h1>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>Username</th>
            <th>Cards</th>
            <th>Total Points</th>
        </tr>
        </thead>
        <tbody id="playersTableBody">
        <!-- Dynamic rows will be appended here -->
        </tbody>
    </table>

    <div class="mt-4">
        <button id="drawButton" class="btn btn-primary">Draw</button>
        <button id="passButton" class="btn btn-secondary">Pass</button>
    </div>
    <div class="mt-4">
        <button id="startButton" class="btn btn-primary">Start</button>
        <button id="leaveButton" class="btn btn-secondary">Leave</button>
    </div>

</div>

<script>
    // Function to get query parameters
    function getQueryParam(param) {
        var urlParams = new URLSearchParams(window.location.search);
        return urlParams.get(param);
    }

    var gameId = getQueryParam("id");

    function connect() {
        var socket = new SockJS('/ws');
        var stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/game/' + gameId, function (message) {
                var players = (JSON.parse(message.body)).players;
                updatePlayerTable(players);
            });
        });
    }

    function updatePlayerTable(players) {
        var tableBody = $('#playersTableBody');
        tableBody.empty();
        players.forEach(function (player) {
            var cards;
            if (player.cards != null) {
                cards = player.cards.map(function (card) {
                    return `<img src="` + card.url + `" alt="${card.value} of ${card.suit}" class="img-fluid" style="width: 50px; height: auto; margin-right: 5px;">`;
                }).join('');
            } else {
                cards = '';
            }

            var row = `
                    <tr>
                        <td>` + player.username + `</td>
                        <td>` + cards + `</td>
                        <td>` + player.totalPoints + `</td>
                    </tr>
                `;
            tableBody.append(row);
        });
    }

    function fetchInitialData() {
        $.ajax({
            url: `/api/game/` + gameId,
            method: 'GET',
            success: function (response) {
                updatePlayerTable(response.players);
            },
            error: function (xhr, status, error) {
                console.error('Failed to fetch initial data:', error);
            }
        });
    }

    function sendAction(action) {
        $.ajax({
            url: `/api/game/` + action + `/` + gameId,
            method: 'PUT',
            success: function (response) {
                console.log(action + ' action successful');
            },
            error: function (xhr, status, error) {
                console.error('Failed to send ' + action + ' action:', error);
            }
        });
    }

    $(document).ready(function () {
        if (gameId) {
            fetchInitialData()
            connect();

            $('#drawButton').on('click', function () {
                sendAction('draw');
            });

            $('#passButton').on('click', function () {
                sendAction('pass');
            });

            $('#startButton').on('click', function () {
                sendAction('start');
            });

            $('#leaveButton').on('click', function () {
                sendAction('leave');
            });
        } else {
            console.error("Game ID is not provided in the query parameters.");
        }
    });
</script>

<!-- Bootstrap JS and dependencies -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
</body>
</html>
