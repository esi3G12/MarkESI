<LINK href="lib/css/jqueryFileTree.css" rel="stylesheet" type="text/css">
<script src="lib/js/easingPlg.js"></script>
<script src="lib/js/jqueryFileTree.js"></script>
<script src="lib/js/jtree.js"></script>   
<script type="text/javascript">
    $(document).ready( function() {
        $('#tree').fileTree({ root: '/Documents and Settings/g35079/Desktop' , script: 'lib/connectors/jqueryFileTree.jsp'}, function(file) {
            alert(file);
        });
    });
</script>