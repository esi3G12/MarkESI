/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.business;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import markesi.entity.User;

/**
 *
 * @author LSV
 */
@Stateful
public class UserEJB {

    @PersistenceContext(unitName = "MarkESI-PU")
    private EntityManager em;

    public User login(String username, String password) {
        User usr = em.find(User.class, username);
        if (usr.getPassword().equals(md5(password))) {
            return usr;
        }else{
            return null;
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public User ajouter(String email, String username, String password, String nom, String prenom) {
        User utilisateur = new User();
        utilisateur.setUserName(username);
        utilisateur.setName(nom);
        utilisateur.setFirstname(prenom);
        utilisateur.setEmail(email);
        //on genère le MD5 pour le password (histoire de pas le sauver en clair..)
        utilisateur.setPassword(md5(password));
        em.persist(utilisateur);
        return utilisateur;
    }

    public static String md5(String input) {
        String md5 = null;
        if (null == input) {
            return null;
        }
        try {
            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");
            //Update input string in message digest
            digest.update(input.getBytes(), 0, input.length());
            //Converts message digest value in base 16 (hex)
            md5 = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }
}
