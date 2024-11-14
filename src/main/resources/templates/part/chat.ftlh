<#-- Set the content type for the page -->
<#assign contentType = "text/html;charset=UTF-8">
<#macro csrfMetaTags>
    <meta name="csrf-token" content="${_csrf.token}" />
    <meta name="csrf-header" content="${_csrf.headerName}" />
</#macro>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title>Chat</title>
    <@csrfMetaTags />
    <style>
        /* Add your chat page styling here */
        #chat-page { max-width: 600px; margin: 0 auto; }
        .chat-container { border: 1px solid #ccc; padding: 20px; }
        .chat-header { font-size: 1.5em; text-align: center; margin-bottom: 10px; }
        #messageArea { list-style-type: none; padding: 0; height: 300px; overflow-y: scroll; border-bottom: 1px solid #ccc; margin-bottom: 10px; }
        .input-group { display: flex; justify-content: space-between; }
        .form-group { margin-bottom: 10px; }
    </style>
</head>
<body>

<div id="chat-page">
    <div class="chat-container">
        <div class="chat-header">
            <h2>Chat</h2>
        </div>

        <ul id="messageArea">
            <!-- Chat messages will appear here -->
        </ul>

        <form id="messageForm" name="messageForm">
            <div class="form-group">
                <div class="input-group">
                    <input type="text" id="message" placeholder="Write your message..." autocomplete="off" class="form-control"/>
                    <button type="submit" class="btn btn-primary">Send</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>

<script>
    var messageArea = document.querySelector('#messageArea');
    var messageInput = document.querySelector('#message');
    var stompClient = null;

    window.onload = function connect(event) {
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
        event.preventDefault();
    };

    function onConnected() {
        // Subscribe to the Public Topic
        stompClient.subscribe('/topic/chat', onMessageReceived);

        // Register the user when they connect
        stompClient.send("/app/chat.register", {},
            JSON.stringify({ sender: 'kek', type: 'JOIN' })
        );
    }

    function onError(error) {
        console.error("Error connecting: " + error);
    }

    function onMessageReceived(payload) {
        var message = JSON.parse(payload.body);
        var messageElement = document.createElement('li');
        messageElement.classList.add('event-message');

        var content = message.author + ": " + message.text;
        var textElement = document.createElement('p');
        var messageText = document.createTextNode(content);
        textElement.appendChild(messageText);

        messageElement.appendChild(textElement);
        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight; // Scroll to the latest message
    }

    function send(event) {
        var messageContent = messageInput.value.trim();

        if (messageContent && stompClient) {
            var chatMessage = { text: messageInput.value };
            stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage));
            messageInput.value = ''; // Clear the message input field
        }
        event.preventDefault();
    }

    document.querySelector('#messageForm').addEventListener('submit', send);
</script>

</body>
</html>
