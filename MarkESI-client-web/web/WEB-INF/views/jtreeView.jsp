 <script src="js/jquery.js"></script>
        <script src="js/easingPlg.js"></script>
        <script src="js/jqueryFileTree.js"></script>
        <script src="js/jtree.js"></script>
        <link type="text/css" rel="stylesheet" href="css/jqueryFileTree.css"/>

<div id="tree"></div>
<script type="text/javascript">
    $(document).ready( function() {
        $('#tree').fileTree({ root: 'C:\\UserLocal\\submissions' , script: 'js/connectors/jqueryFileTree.jsp'}, function(file) {
            window.location = "?action=viewFile&fileName="+file;
        });
    });
</script>