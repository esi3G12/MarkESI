 <script src="js/jquery.js"></script>
        <script src="js/easingPlg.js"></script>
        <script src="js/jqueryFileTree.js"></script>
        <script src="js/jtree.js"></script>
        <link type="text/css" rel="stylesheet" href="css/jqueryFileTree.css"/>

<div id="tree"></div>
<script type="text/javascript">
    $(document).ready( function() {
        $('#tree').fileTree({ root: 'Z:/Test/' , script: 'js/connectors/jqueryFileTree.jsp'}, function(file) {
            alert(file);
        });
    });
</script>