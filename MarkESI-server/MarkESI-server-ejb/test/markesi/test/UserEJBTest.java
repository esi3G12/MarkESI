/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.test;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import markesi.business.UserEJB;
import markesi.entity.User;
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


    public UserEJBTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        try {
            container = javax.ejb.embeddable.EJBContainer.createEJBContainer();

            UserEJB = (UserEJB) container.getContext().lookup(
                    "java:global/classes/UserEJB");

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
        User utilisateur = UserEJB.add("jguerriat@gmail.com", "g34871", "g30917", "guerriat", "jérôme");
        User user = UserEJB.login("g34871", "g30917");
        assertEquals(utilisateur,user);
    }
    
    @Test
    public void loginOKSpecialCharacters() {
        User utilisateur = UserEJB.add("'(§'(13@gmail.com", "'(§'(13", "g30917", "guerriat", "jérôme");
        assertNotNull(utilisateur);
    }
    
    @Test
    public void subscribeOKSpecialCharacters() {
        User utilisateur = UserEJB.add("'(§'(13@gmail.com", "§!156", "g30917", "guerriat", "jérôme");
        User user = UserEJB.login("§!156", "g30917");
        assertEquals(utilisateur,user);
    }

}
