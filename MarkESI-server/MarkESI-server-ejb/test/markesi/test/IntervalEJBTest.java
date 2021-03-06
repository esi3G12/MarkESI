/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.test;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import markesi.business.AnnotationEJB;
import markesi.business.IntervalEJB;
import markesi.entity.Interval;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author g30917
 */
public class IntervalEJBTest {

    private static EJBContainer container;
    private static IntervalEJB intervalEJB;
    private static AnnotationEJB annotationEJB;

    public IntervalEJBTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        try {
            intervalEJB = (IntervalEJB) container.getContext().lookup(
                    "java:global/classes/IntervalEJB");
            annotationEJB = (AnnotationEJB) container.getContext().lookup(
                    "java:global/classes/AnnotationEJB");
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
    public void addInterval() {
        Interval intervalCreated = intervalEJB.create(1,5);
        Interval found = intervalEJB.findById(intervalCreated.getId());
        assertEquals(intervalCreated, found);
        assertEquals(1, found.getBegin());
        assertEquals(5, found.getEnd());
    }

    @Test
    public void testDelete() {
        Interval intervalCreated = intervalEJB.create(1,5);
        assertEquals(intervalCreated, intervalEJB.findById(intervalCreated.getId()));
        intervalEJB.delete(intervalCreated);
        //on ne trouve plus l'annotation
        assertNull(intervalEJB.findById(intervalCreated.getId()));
    }
    
    @Test
    public void testNotIntersect() {
        Interval i1 = intervalEJB.create(1,5);
        Interval i2 = intervalEJB.create(6,7);
        assertFalse(intervalEJB.intersects(i1, i2));
    }
    
    @Test
    public void testIntersect() {
        Interval i1 = intervalEJB.create(1,5);
        Interval i2 = intervalEJB.create(4,7);
        assertTrue(intervalEJB.intersects(i1, i2));
    }
    
    @Test
    public void testIntersectTouchIntervals() {
        Interval i1 = intervalEJB.create(1,5);
        Interval i2 = intervalEJB.create(5,7);
        assertTrue(intervalEJB.intersects(i1, i2));
    }
    
    @Test
    public void testIntersectSameIntervals() {
        Interval i1 = intervalEJB.create(1,5);
        Interval i2 = intervalEJB.create(1,5);
        assertTrue(intervalEJB.intersects(i1, i2));
    }
}
