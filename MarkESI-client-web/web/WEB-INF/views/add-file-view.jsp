<%@page contentType="text/html" pageEncoding="UTF-8"%>
<form action="?action=upload" method="post" enctype="multipart/form-data">
    <fieldset>
        <legend>Envoi de fichier</legend>

        <label for="description">Description du fichier</label>
        <input type="text" id="description" name="description" value="" />
        <br />

        <label for="fichier">Emplacement du fichier <span class="requis">*</span></label>
        <input type="file" id="fichier" name="fichier" />
        <br />
        <input type="hidden" name="action" id="action" value="upload"/>
        <input type="submit" value="Envoyer" class="sansLabel" />
        <br />                
    </fieldset>
</form>