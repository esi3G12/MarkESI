<form method="post" action="Front?action=connexion" id="connexion" class="master_form">
    <fieldset>
        <legend>Connexion</legend>
        <p>Vous pouvez vous connecter via ce formulaire.</p>

        <div class="form_part">
        <label for="nom"> Login <span class="requis">*</span></label>
        <input type="text" id="login" name="login" value="" maxlength="60" />        
        </div>

        <div class="form_part">
        <label for="motdepasse">Mot de passe <span class="requis">*</span></label>
        <input type="password" id="pass" name="pass" value="" maxlength="20" />        
        </div>

        <a href="?action=signup" class="signup button">Inscription</a>
        <input type="submit" value="Connexion" />
    </fieldset>
 </form>
