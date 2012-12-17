<%
boolean connected = (Boolean)request.getAttribute("connected");
if (connected == true) { %>
<form action="Front?action=deco" method="POST">
    <input type="submit" class="button" value="Déco"/>
</form>
<% } %>