function statisticTableHeader() {
    return '<table class="tg1 body">\n' +
           '    <tr class="tg1 main-header w3-center">\n' +
           '        <th class="tg1-0lax">ID</th>\n' +
           '        <th class="tg1-0lax">Marker</th>\n' +
           '        <th class="tg1-0lax">Page</th>\n' +
           '        <th class="tg1-0lax">IP</th>\n' +
           '        <th class="tg1-0lax">Time</th>\n' +
           '        <th class="tg1-0lax">Session-Id</th>\n' +
           '        <th class="tg1-0lax">User-Name</th>\n' +
           '        <th class="tg1-0lax">Previous-Id</th>\n' +
           '    </tr>';
}

function statisticTableTr(id, nameMarker, jspPageName, ipAddress, clientTime, sessionId, user, userAgent) {
    return '<tr class="tg1">\n' +
           '    <td class="tg1-0lax" style="text-align:right!important" rowspan="2">' + id +'&nbsp;</td>\n' +
           '    <td class="tg1-0lax">' + nameMarker + '</td>\n' +
           '    <td class="tg1-0lax w3-center">' + jspPageName + '</td>\n' +
           '    <td class="tg1-0lax w3-center">' + ipAddress + '</td>\n' +
           '    <td class="tg1-0lax w3-center">' + clientTime + '</td>\n' +
           '    <td class="tg1-0lax w3-center">' + sessionId + '</td>\n' +
           '    <td class="tg1-0lax w3-center">' + user + '</td>\n' +
           '    <td class="tg1-0lax w3-center">-1</td>\n' +
           '</tr>\n' +
           '<tr class="tg1">\n' +
           '    <td class="tg1-0lax w3-center" colspan="7"><b>User-Agent:&nbsp;|&nbsp;</b>' + userAgent + '</td>\n' +
           '</tr>';
}
