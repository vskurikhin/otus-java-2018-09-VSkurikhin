<#assign tags=JspTaglibs["/WEB-INF/custom_tag.tld"]>

<!DOCTYPE html>
<html lang=ru>
<head>
    <meta charset=UTF-8>
    <title id="title">Home Work 7 of JavaEE 2018-09 | a work showcasing the feature of Freemarker</title>
    <link rel="stylesheet" href="css/style-all.min.css"/>
    <script type="text/javascript" language="javascript" src="js/script-all.min.js"></script>
<@tags.sender_stat pageName = "visits_stat" />
    <script type="text/javascript" language="javascript" src="inside/inside.nocache.js"></script>
</head>

<body class="body">
    <table class="tg body">
<@include_page path="/WEB-INF/header.jsp"/>
<@include_page path="/WEB-INF/inside.menu.jsp"/>
        <tr>
            <td class="tg-0lax my-left">
                <main id="main" class="w3-col m12 w3-margin-0 my-left">
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
                </main>
            </td>
            <td class="tg-0lax my-right" style="border-left: 2px dotted black;" colspan="2">
<@include_page path="/WEB-INF/aside.jsp"/>
            </td>
        </tr>
<@include_page path="/WEB-INF/footer.jsp"/>
<%@ include file = "WEB-INF/footer.jsp" %>
    </table>
</body>
</html>
