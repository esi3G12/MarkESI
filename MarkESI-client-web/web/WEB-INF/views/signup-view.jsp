<form method="post" action="Front?action=adduser" id="signup" class="master_form">
    <fieldset>
        <legend>Inscription</legend>
        <p>Vous pouvez vous inscrire via ce formulaire.</p>

        <div class="form_part">
        <label for="nom">Adresse email <span class="requis">*</span></label>
        <input type="email" id="email" name="email" value="" size="20" maxlength="60" />
        </div>

        <div class="form_part">
        <label for="nom"> Login <span class="requis">*</span></label>
        <input type="text" id="login" name="login" value="" size="20" maxlength="60" />
        </div>

        <div class="form_part">
        <label for="motdepasse">Mot de passe <span class="requis">*</span></label>
        <input type="password" id="pass" name="pass" value="" size="20" maxlength="20" />
        </div>

        <div class="form_part">
        <label for="nom"> Nom <span class="requis">*</span></label>
        <input type="text" id="nom" name="nom" value="" size="20" maxlength="60" />
        </div>
        
        <div class="form_part">
        <label for="nom"> Prénom <span class="requis">*</span></label>
        <input type="text" id="prenom" name="prenom" value="" size="20" maxlength="60" />
        </div>
        
        <input type="submit" value="Inscription" class="signup"/>
    </fieldset>

</form>