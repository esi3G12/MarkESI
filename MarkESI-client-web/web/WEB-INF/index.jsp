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
        <jsp:include page="views/logout-view.jsp"/>
        <jsp:include page="views/jtreeView.jsp"/>
        <jsp:include page="views/add-file-view.jsp"/>
        <% if (request.getParameter("action") != null && request.getParameter("action").equals("viewFile")) {
        %> 
        <jsp:include page = "views/file-view.jsp" /> 
        <jsp:include page = "views/annotation-view.jsp" />
        <jsp:include page = "views/add-annotation-view.jsp" />
        <%            }
        %>

    </body>
</html>
