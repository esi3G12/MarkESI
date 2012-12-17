<%-- 
    Document   : file-screen
    Created on : 07-nov.-2012, 14:36:11
    Author     : g34871
--%>

<%@page import="java.util.Collection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MarkESI</title>
        <style><%@include file="css/style.css"%>

            <%@include file="css/notification.css"%></style>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
        <script type="text/javascript">
            <%@include file="js/lib/jquery-pp.js"%>
            <%@include file="js/lib/selection.js"%>
        </script>
    </head>
    <body>
        <% Boolean isSigningUp = (Boolean) request.getAttribute("isSigningUp");
            if ((Boolean) request.getAttribute("connected") == true) {
        %>
        <div id="left" class="big_div">
            <h2>MarkESI - Correction</h2>
            <div class="padding">
                <jsp:include page="views/logout-view.jsp"/>
                <hr/>
                <jsp:include page="views/jtreeView.jsp"/>
                <hr/>
                <jsp:include page="views/add-file-view.jsp"/>
            </div>
        </div>
        <% if (request.getParameter("action") != null && request.getParameter("action").equals("viewFile")) {
        %>
        <div id="center">
            <jsp:include page = "views/file-view.jsp" />
        </div>
        <div id="right">
            <jsp:include page = "views/annotation-view.jsp" />
            <jsp:include page = "views/add-annotation-view.jsp" />
        </div>
        <% }
        } else if (isSigningUp != null && isSigningUp == true) {%>
        <jsp:include page = "views/signup-view.jsp" />
        <% } else {%>
        <jsp:include page = "views/user-connexion-view.jsp" />
        <%}%>
    </body>
</html>
