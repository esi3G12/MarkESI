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
        <title><%=request.getAttribute("title")%></title>
        <style><%@include file="css/style.css"%>

            <%@include file="css/notification.css"%></style>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
        <script type="text/javascript">
            <%@include file="js/lib/jquery-pp.js"%>
        </script>
        <script type="text/javascript">
            <%@include file="js/lib/selection.js"%>
        </script>
    </head>
    <body>

        <%
            Collection<String> views = (Collection<String>) request.getAttribute("views");
            if (views != null) {
                for (String view : views) {
        %>
        <jsp:include page="<%=view%>"/>
        <%
                }
            }
        %>
    </body>
</html>
