var latestMessage;
var timeoutID;

window.onload = function() {
    getMessages();
}

function sendMessage() {
    var newMsg = document.getElementById("newMessage").value
    console.log("message: " + newMsg)

    if (!newMsg) { return }

    var req = new XMLHttpRequest()

    if (!req) {
        alert("Error creating XMLHTTP instance!")
        return
    }

    req.onreadystatechange = function() {
        handleNewMessage(req)
    }

    var url = "/message/?room_id=" + room_id

    req.open("POST", url)
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")

    var data = "text=" + newMsg
    req.send(data)
}

function handleNewMessage(req) {
    if (req.readyState == XMLHttpRequest.DONE) {
        if (req.status == 200) {
            document.getElementById("newMessage").value = null;
        }
        else {
            alert("There was a problem sending your message.");
        }
    }
}
