<form method="post" action="connexion">
    <fieldset>
        <legend>Connexion</legend>
        <p>Vous pouvez vous connecter via ce formulaire.</p>

        <label for="nom"> Login <span class="requis">*</span></label>
        <input type="text" id="login" name="login" value="${utilisateur.login}" size="20" maxlength="60" />
        <span class="erreur">${form.erreurs['login']}</span>
        <br />

        <label for="motdepasse">Mot de passe <span class="requis">*</span></label>
        <input type="password" id="pass" name="pass" value="" size="20" maxlength="20" />
        <span class="erreur">${form.erreurs['password']}</span>
        <br />

        <input type="submit" value="Connexion" />
        <br />

        <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
        <a href="?action=signup">Inscription</a>
    </fieldset>
 </form>
