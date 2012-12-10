<div id="panel" class="big_div">
    <h2>Annotations</h2>
    <div class="padding">
        <form id="sel_form" action="/json?action=post">
            Ajouter une annotation :<br/>
            <textarea id="annotation"></textarea>
            <hr/>
            Aux sélections suivantes :<br/>
            <div id="selections">
                <div id="no_selection">Aucune sélection...</div>
            </div>
            <hr/>
            <div class="align_right">
                <button id="add_annot">Ajouter une annotation</button>
            </div>
            <input type="hidden" name="json" id="json"/>
        </form>
    </div>
</div>
<script type="text/javascript">
    <%@include file="../js/add-annot.js"%>
</script>