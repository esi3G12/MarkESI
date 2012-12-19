/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.test;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import markesi.business.SubmissionEJB;
import markesi.business.UserEJB;
import markesi.entity.Submission;
import markesi.entity.User;
import markesi.exceptions.MarkESIException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author g30917
 */
public class UserEJBTest {

    private static EJBContainer container;
    @EJB
    private static UserEJB UserEJB;
    @EJB
    private static SubmissionEJB submissionEJB;

    public UserEJBTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        try {
            container = javax.ejb.embeddable.EJBContainer.createEJBContainer();

            UserEJB = (UserEJB) container.getContext().lookup(
                    "java:global/classes/UserEJB");

            submissionEJB = (SubmissionEJB) container.getContext().lookup(
                    "java:global/classes/SubmissionEJB");

        } catch (NamingException ex) {
            Logger.getLogger(SubmissionEJBTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterClass
    public static void tearDownClass() {
        container.close();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void subscribeOK() {
        User utilisateur = UserEJB.add("jguerriat@gmail.com", "g30917", "g30917", "guerriat", "jérôme");
        assertNotNull(utilisateur);
    }

    @Test
    public void loginOK() {
        User utilisateur = UserEJB.add("jguerriat2@gmail.com", "g34871", "g30917", "guerriat", "jérôme");
        User user = UserEJB.login("g34871", "g30917");
        assertEquals(utilisateur, user);
    }

    @Test
    public void loginOKSpecialCharacters() {
        User utilisateur = UserEJB.add("'(§'(133@gmail.com", "'(§'(13", "g30917", "guerriat", "jérôme");
        assertNotNull(utilisateur);
    }

    @Test
    public void subscribeOKSpecialCharacters() {
        User utilisateur = UserEJB.add("'(§'(135@gmail.com", "§!156", "((((()))))", "guerriat", "jérôme");
        User user = UserEJB.login("§!156", "((((()))))");
        assertEquals(utilisateur, user);
    }

    @Test(expected = EJBException.class)
    public void subscribeOnlyOnce() {
        User utilisateur = UserEJB.add("jguerriat5@gmail.com", "sryj", "aaaa", "guerriat", "jérôme");
        //cette ligne doit creer une violation de la contrainte d'email 
        User utilisateur2 = UserEJB.add("jguerriat5@gmail.com", "user2", "aaaa", "guerriat", "jérôme");
        assertNull(UserEJB.login("user2", "aaaa"));
    }

    @Test
    public void passwordIsMd5() {
        //test pour savoir si le password sauvé est bien le MD5
        User utilisateur = UserEJB.add("a@a.com", "a", "a", "a", "a");
        User user = UserEJB.login("a", "a");
        String passwordMD5 = UserEJB.md5("a");
        assertEquals(passwordMD5, user.getPassword());
    }

    public void subScribeAndAddSubmission() throws MarkESIException {
        User utilisateur = UserEJB.add("b@b.com", "b", "b", "b", "b");
        Submission submission = submissionEJB.addSubmission("test");
        UserEJB.addSubmission(utilisateur, submission);
        User user = UserEJB.login("b", "b");
        assertEquals(user.getSubmissionCollection().size(), 1);
    }
}
