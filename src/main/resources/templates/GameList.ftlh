<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Game List</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>
</head>
<body>

<#include "part/navbar.ftlh">

<div class="container mt-5">
    <h1 class="mb-4">Available Games</h1>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>Creator</th>
            <th>Current Players</th>
            <th>Max Players</th>
            <th>Bet Amount</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <#list gameList as game>
            <tr>
                <td>${game.dealerName}</td>
                <td>${game.playerAmount}</td>
                <td>${game.maxPlayers}</td>
                <td>${game.price}</td>
                <td>
                    <form action="/main" method="post">
                        <input type="hidden" name="gameId" value="${game.id}">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                        <button type="submit" class="btn btn-primary">Join</button>
                    </form>
                </td>
            </tr>
        </#list>
        </tbody>
        <form action="/api/game/create" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

            <button type="submit" class="btn btn-primary">Create game</button>
        </form>
    </table>
    <div id="chat-page">
        <div class="chat-container">
            <ul id="messageArea"></ul>
            <form id="messageForm" name="messageForm">
                <div class="form-group">
                    <div class="input-group clearfix">
                        <input type="text" id="message" placeholder="Write your message" autocomplete="off" class="form-control" />
                        <button type="submit" class="primary">Send</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>

<script>

    var connectingElement = document.querySelector('.connecting');
    var messageArea = document.querySelector('#messageArea');
    var messageForm = document.querySelector('#messageForm');
    var messageInput = document.querySelector('#message');

    var stompClient = null;
    window.onload = function connect(event) {

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
        event.preventDefault();
    }

    function onConnected() {
        // Subscribe to the Public Topic
        stompClient.subscribe('/topic/chat', onMessageReceived);
    }

    function onError(error) {
    }

    function onMessageReceived(payload) {
        var message = JSON.parse(payload.body);
        var messageElement = document.createElement('li');
        messageElement.classList.add('event-message');
        message.content = message.author + ': '+ message.text

        var textElement = document.createElement('p');
        var messageText = document.createTextNode(message.content);
        textElement.appendChild(messageText);

        messageElement.appendChild(textElement);

        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;
    }

    function send(event) {
        var messageContent = messageInput.value.trim();

        if(messageContent && stompClient) {
            var chatMessage = {
                text: messageInput.value,
            };

            stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage));
            messageInput.value = '';
        }
        event.preventDefault();
    }

    messageForm.addEventListener('submit', send, true)

</script>

</body>
</html>