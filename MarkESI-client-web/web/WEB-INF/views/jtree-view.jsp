<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script>
    <%@include file="../js/lib/easingPlg.js" %>
</script>
<script>
    <%@include file="../js/lib/jqueryFileTree.js" %>
</script>   
<script>
    <%@include file="../js/lib/jtree.js" %>
</script>
<style><%@include file="../css/jqueryFileTree.css"%></style>

<div id="tree"></div>

<script type="text/javascript">
    $(document).ready( function() {
        $('#tree').fileTree({ root: '/Documents and Settings/g35079/Desktop' , script: '../js/lib/connectors/jqueryFileTree.jsp'}, function(file) {
            alert(file);
        });
    });
</script>