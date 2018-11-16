var wsocket;

function onMessageForexCBR(evt) {
    document.getElementById("rate").innerHTML = evt.data
}

function connectForexCBR() {
    wsocket = new WebSocket("ws://localhost:8080/homework8/forexcbr");
    wsocket.onmessage = onMessageForexCBR
}

window.addEventListener("loadForexCBR", connectForexCBR, false);
