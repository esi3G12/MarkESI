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
    public void inscription() {
        User utilisateur = UserEJB.inscrire("jguerriat@gmail.com", "g30917", "g30917", "guerriat", "jérôme");
        assertNotNull(utilisateur);
    }
    
    @Test
    public void raf(){
        assertTrue(true);
    }


}
