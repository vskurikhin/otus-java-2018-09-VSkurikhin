<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.io.*,java.util.*" %>

<!DOCTYPE html>
<html lang=ru>
<head>
    <meta charset=UTF-8>
    <title id="title">Home Work 6 of JavaEE 2018-09 | a work showcasing the feature of GWT</title>
    <link rel="stylesheet" href="css/style-all.min.css"/>
    <script type="text/javascript" language="javascript" src="js/script-all.min.js"></script>
    <script type="text/javascript" language="javascript" src="welcome/welcome.nocache.js"></script>
</head>

<body class="body">
    <table class="tg body">
<%@ include file = "header.jsp" %>
<%@ include file = "welcome.menu.jsp" %>
        <tr>
            <td class="tg-0lax my-left">
                <main id="main" class="w3-col m12 w3-margin-0 my-left">
                    <div id="login-container"></div>
                </main>
            </td>
            <td class="tg-0lax my-right" style="border-left: 2px dotted black;" colspan="2">
<%@ include file = "aside.jsp" %>
            </td>
        </tr>
<%@ include file = "footer.jsp" %>
    </table>
</body>
</html>
