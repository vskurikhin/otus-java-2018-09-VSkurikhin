<#assign tags=JspTaglibs["/WEB-INF/custom_tag.tld"]>

<!DOCTYPE html>
<html lang=ru>
<head>
    <meta charset=UTF-8>
    <title id="title">Home Work 7 of JavaEE 2018-09 | a work showcasing the feature of Freemarker</title>
    <link rel="stylesheet" href="../css/style-all.min.css"/>
    <script type="text/javascript" language="javascript" src="../js/script-all.min.js"></script>
<@tags.sender_stat pageName = "visits_stat" />
    <script type="text/javascript" language="javascript" src="../inside/inside.nocache.js"></script>
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
                    <input type="button" value="Включить/отключить сбор статистики" onclick="switchCollectionEnabled()">

                    <table class="tg body">
                        <tr class="main-header w3-center">
                            <th class="tg-0lax">ID</th>
                            <th class="tg-0lax">Marker</th>
                            <th class="tg-0lax">Page</th>
                            <th class="tg-0lax">IP</th>
                            <th class="tg-0lax">User-agent</th>
                        </tr>
                        <#list stat_elememts as elem>
                        <tr>
                            <td class="tg-0lax">${elem.id}</td>
                            <td class="tg-0lax">${elem.nameMarker}</td>
                            <td class="tg-0lax">${elem.jspPageName}</td>
                            <td class="tg-0lax">${elem.ipAddress}</td>
                            <td class="tg-0lax">${elem.userAgent}</td>
                        </tr>
                        </#list>
                    </table>
                </main>
            </td>
            <td class="tg-0lax my-right" style="border-left: 2px dotted black;" colspan="2">
<@include_page path="/WEB-INF/aside.jsp"/>
            </td>
        </tr>
<@include_page path="/WEB-INF/footer.jsp"/>
    </table>
</body>
</html>
