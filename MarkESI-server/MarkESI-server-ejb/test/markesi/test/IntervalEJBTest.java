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
import markesi.business.IntervalEJB;
import markesi.entity.Interval;
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
public class IntervalEJBTest {

    private static EJBContainer container;
    @EJB
    private static IntervalEJB IntervalEJB;

    public IntervalEJBTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        try {
            IntervalEJB = (IntervalEJB) container.getContext().lookup(
                    "java:global/classes/IntervalEJB");
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
    public void addAnnotation() {
        Interval intervalCreated = IntervalEJB.create(1,5);
        Interval found = IntervalEJB.findById(intervalCreated.getId());
        assertEquals(intervalCreated, found);
        assertEquals(1, found.getBegin());
        assertEquals(5, found.getEnd());
    }

    @Test
    public void testDelete() {
        Interval ntervalReturn = IntervalEJB.create(1,5);
        assertEquals(ntervalReturn, IntervalEJB.findById(ntervalReturn.getId()));
        IntervalEJB.delete(ntervalReturn);
        //on ne trouve plus l'annotation
        assertNull(IntervalEJB.findById(ntervalReturn.getId()));
    }
}
