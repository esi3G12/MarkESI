<form method="post" action="Front?action=adduser">
    <fieldset>
        <legend>Inscription</legend>
        <p>Vous pouvez vous inscrire via ce formulaire.</p>

        <label for="nom">Adresse email <span class="requis">*</span></label>
        <input type="email" id="email" name="email" value="" size="20" maxlength="60" />
        <span class="erreur">${form.erreurs['email']}</span>
        <br />

        <label for="nom"> Login <span class="requis">*</span></label>
        <input type="text" id="login" name="login" value="" size="20" maxlength="60" />
        <span class="erreur">${form.erreurs['login']}</span>
        <br />

        <label for="motdepasse">Mot de passe <span class="requis">*</span></label>
        <input type="password" id="pass" name="pass" value="" size="20" maxlength="20" />
        <span class="erreur">${form.erreurs['password']}</span>
        <br />

        <label for="nom"> Nom <span class="requis">*</span></label>
        <input type="text" id="nom" name="nom" value="" size="20" maxlength="60" />
        <span class="erreur">${form.erreurs['login']}</span>
        <br />
        
        <label for="nom"> Prénom <span class="requis">*</span></label>
        <input type="text" id="prenom" name="prenom" value="" size="20" maxlength="60" />
        <span class="erreur">${form.erreurs['login']}</span>
        <br />
        
        <input type="submit" value="Inscription" />

        <br />

        <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>

    </fieldset>

</form>