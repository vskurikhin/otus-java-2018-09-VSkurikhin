<@include_page path="/WEB-INF/header.jsp"/>

        <#assign tags=JspTaglibs["/WEB-INF/custom_tag.tld"]>
        <@ tags.visits_stat_sender pageName = "visits_stat" />
        Включен сбор статистики: <span id = "isCollectionEnabled">
        <#if stat_enabled>
            да
        <#else>
            нет
        </#if>
        </span>
        <br>
        <input type="button" value="Включить/отключить сбор статистики" onclick="switchStatCollectionEnabled()">

        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.1/Chart.min.js"type="text/javascript"></script>

        <canvas id="myChart" width="400" height="200"></canvas>
        <script>
            var ctx = document.getElementById("myChart").getContext('2d');
            var myChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: [${chart_labels}],
                    datasets: [{
                        label: 'Посещаемость страниц',
                        data: [${chart_values}],
                        borderWidth: 1,
                        backgroundColor: "#4682b4"
                    }]
                },
                options: {
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero: true
                            }
                        }]
                    }
                }
            });
        </script>

        <div class="stat-table">
            <#list statElems as elem>
                <div class="stat-table-elem">
                    <div class="stat-table-row">
                        <div class="stat-table-data-header">ID:</div>
                        <div class="stat-table-data-value stat-table-col1">${elem.id}</div>
                        <div class="stat-table-data-header">Имя маркера:</div>
                        <div class="stat-table-data-value stat-table-col2">${elem.nameMarker}</div>
                        <div class="stat-table-data-header stat-table-col3">Страница:</div>
                        <div class="stat-table-data-header">Браузер:</div>
                        <div class="stat-table-data-value">${elem.userAgent}</div>

                        <div class="stat-table-data-header">Время клиента:</div>
                        <div class="stat-table-data-value">${elem.clientTime}</div>
                    </div>
                    <div class="stat-table-row">
                        <div class="stat-table-data-header stat-table-col1">IDпрш:</div>
                        <div class="stat-table-data-value">${elem.prev_id}</div>
                        <div class="stat-table-data-header">ID сессии:</div>
                        <div class="stat-table-data-value stat-table-col2">${elem.sessionId}</div>
                        <div class="stat-table-data-value">${elem.jspPageName}</div>
                        <div class="stat-table-data-header">IP клиента:</div>
                        <div class="stat-table-data-value">${elem.ipAddress}</div>

                        <div class="stat-table-data-header">Время сервера:</div>
                        <div class="stat-table-data-value">${elem.serverTime}</div>
                    </div>
                </div>
            </#list>
        </div>

<@include_page path="/WEB-INF/footer.jsp"/>