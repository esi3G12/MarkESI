<form method="post" action="Front?action=connexion">
    <fieldset>
        <legend>Connexion</legend>
        <p>Vous pouvez vous connecter via ce formulaire.</p>

        <label for="nom"> Login <span class="requis">*</span></label>
        <input type="text" id="login" name="login" value="" size="20" maxlength="60" />        
        <br />

        <label for="motdepasse">Mot de passe <span class="requis">*</span></label>
        <input type="password" id="pass" name="pass" value="" size="20" maxlength="20" />        
        <br />

        <input type="submit" value="Connexion" />
        <br />        
        <a href="?action=signup">Inscription</a>
    </fieldset>
 </form>
