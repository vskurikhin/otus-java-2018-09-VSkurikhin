<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  ~ Copyright (c) Victor N. Skurikhin 27.11.18 23:16.
  ~ inside.menu.jsp
  ~ $Id$
  ~ This is free and unencumbered software released into the public domain.
  ~ For more information, please refer to <http://unlicense.org>
  --%>

<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}/" />
        <tr>
            <td class="tg-0lax" colspan="2">
                <nav class="menu">
                    <ul>
                        <li><a id="navigation-menu-0" href="<c:out value="${baseURL}"/>inside.jsp#">Главная</a></li>
                        <li><a id="navigation-menu-1" href="<c:out value="${baseURL}"/>game.jsp#">Игра</a></li>
                        <li><a id="navigation-menu-2" href="<c:out value="${baseURL}"/>inside/request/dept/list">Запросы к Remote EJB</a></li>
                        <li><a id="navigation-menu-3" href="<c:out value="${baseURL}"/>logout">Выход</a></li>
                    </ul>
                </nav>
            </td>
        </tr>
