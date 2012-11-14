<%-- 
    Document   : error
    Created on : 07-nov.-2012, 14:45:18
    Author     : g34871
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Erreur : <%=request.getAttribute("errorType")%></title>
    </head>
    <body>
        <h1>Une erreur est survenue : <%=request.getAttribute("errorType")%></h1>
        <p>
            <%=request.getAttribute("error")%>
        </p>
        <a href="?">Retour à la page d'accueil</a>
    </body>
</html>
