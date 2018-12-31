<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.io.*,java.util.*" %>
<%@ taglib prefix="tags" uri="/custom_tag" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="req" value="${pageContext.request}"/>
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}/"/>

<!DOCTYPE html>
<html lang=ru>
<head>
    <meta charset=UTF-8>
    <title id="title">Home Work 12 of JavaEE 2018-09 | a work showcasing the JavaEE Security</title>
    <link rel="stylesheet" href="<c:out value="${baseURL}"/>css/style-all.min.css"/>
    <script type="text/javascript" language="javascript" src="<c:out value="${baseURL}"/>js/script-all.min.js"></script>
</head>

<body class="body">
<table class="tg body">
    <%@ include file="WEB-INF/header.jsp" %>
    <%@ include file="WEB-INF/welcome.menu.jsp" %>
    <tr>
        <td class="tg-0lax my-left">
            <main id="main" class="w3-col m12 w3-margin-0 my-left">
                <hr class="separator w3-margin">
                <h3>Загадайте число от 0 до 10</h3>
                <label for="userName">Имя игрока: </label> <input type="text" value="funt" id="userName"
                                                                  name="user_name">
                <br/>
                <label for="value">Выбранное число: </label>
                <select id="value" name="value">
                    <option value="0" selected>0</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                </select>
                <br/>
                <br/>
                <input type="button" value="Проверить" onclick="checkResult()">
                </form>
                <hr class="separator w3-margin">
                <p class="w3-margin"> &nbsp; </p>
                <br/>
                <br/>
                <div id="gameResult"></div>
            </main>
        </td>
        <td class="tg-0lax my-right" style="border-left: 2px dotted black;" colspan="2">
            <%@ include file="WEB-INF/aside.jsp" %>
        </td>
    </tr>
    <%@ include file="WEB-INF/footer.jsp" %>
</table>
</body>
</html>
