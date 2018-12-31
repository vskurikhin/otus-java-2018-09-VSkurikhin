function prepareAjaxRequestAndSend(method, uri, async, contentType, requestBody, onSuccess, onFail) {
    let xhr = new XMLHttpRequest();
    xhr.open(method, uri, async);

    if (contentType && contentType != "") {
        xhr.setRequestHeader('Content-Type', contentType);
    }

    xhr.onreadystatechange = function () {
        if (this.readyState != 4) return;

        if (this.status == 200 && onSuccess) {
            onSuccess(xhr);
        } else if (onFail){
            onFail(xhr);
        }
    };

    if (requestBody && requestBody != "") {
        xhr.send(requestBody)
    } else {
        xhr.send()
    }
}

function checkResult() {
    let userName = document.getElementById("userName");
    let value = document.getElementById("value");
    var url = "game_attempt?user_name=" + encodeURIComponent(userName.value);
    url += "&value=" + encodeURIComponent(value.value);

    console.log(url);

    prepareAjaxRequestAndSend("GET", url, true, "", "", function (xhr) {
        let resp = JSON.parse(xhr.responseText);

        let gameResult = document.getElementById("gameResult");
        gameResult.innerHTML = "<span class = \"" + (resp.status && resp.status == true? "game-result-good": "game-result-bad") + "\">" + resp.message + "</span>";
    });
}


