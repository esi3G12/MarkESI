<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="panel" class="big_div">
    <h2>Annotations</h2>
    <div class="padding">
        <form>
            Ajouter une annotation :<br/>
            <textarea id="annotation"></textarea>
            <hr/>
            Aux sélections suivantes :<br/>
            <div id="selections">
                <div id="no_selection">Aucune sélection...</div>
            </div>
            <hr/>
            <div class="align_right">
                <button id="add_annot">Ajouter l'annotation</button>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    <%@include file="../js/add-annot.js"%>
</script>