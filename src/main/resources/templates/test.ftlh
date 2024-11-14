<!DOCTYPE html>
<html>
<head>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title>Chat Tupi | Spring Boot + WebSocket</title>
    <link rel="stylesheet" href="/css/main.css" />
</head>
<body background="maxresdefault.jpg"
      style="background-position: center; background-repeat: no-repeat; background-size: cover;">
<noscript>
    <h2>Opa! Parece que este browser não suporta JavaScript</h2>
</noscript>

<div id="chat-page">
    <div class="chat-container">
        <div class="chat-header">
            <h2>ChatBox Tupi</h2>
        </div>
        <div class="connecting">Conectando ao chat...</div>
        <ul id="messageArea">

        </ul>
        <form id="messageForm" name="messageForm" nameForm="messageForm">
            <div class="form-group">
                <div class="input-group clearfix">
                    <input type="text" id="message" placeholder="Digite uma mensagem..."
                           autocomplete="off" class="form-control" />
                    <button type="submit" class="primary">Enviar</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script
        src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
<script
        src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>

<script>

    var connectingElement = document.querySelector('.connecting');
    var messageArea = document.querySelector('#messageArea');

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

        stompClient.send("/app/chat.register",
            {},
            JSON.stringify({sender: 'kek', type: 'JOIN'})
        )

        // Tell your username to the server
        connectingElement.classList.add('hidden');
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

</script>
</body>
</html>