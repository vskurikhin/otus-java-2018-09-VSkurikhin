let wsocketInside;

function onMessageStatistic(evt) {
    const page = JSON.parse(evt.data);
    let html = statisticTableHeader();


    for (i in page.registrations) {
        // noinspection JSUnfilteredForInLoop
        const e = page.registrations[i];
        console.log(page.registrations[i]);
        html += statisticTableTr(
            e.id, e.nameMarker, e.jspPageName, e.ipAddress, e.clientTime, e.sessionId, e.user, e.userAgent
        );
    }

    const div = document.getElementById('statistic-table');
    div.innerHTML = html

}

function connectInsideWs() {
    wsocketInside = new WebSocket("ws://" +  location.host + "/homework8/inside/ws");
    wsocketInside.onmessage = onMessageStatistic
}

window.addEventListener("load", connectInsideWs, false);
