<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <fieldset>
        <legend>Envoi de fichier</legend>

      <form action="Front?action=uploadFile" method="POST" enctype="multipart/form-data">
            <p>Emission du fichier:<input type="file" name="source" size="30"></p>

            <p>
                <input type="submit" name="submitFichier" value="Annuler" title="Annuler l'émission">
                <input type="submit" name="submitFichier" value="Emettre" title="Emettre le fichier vers le serveur">
            </p>
        </form>              
    </fieldset>
</form>