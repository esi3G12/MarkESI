/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.test;

import markesi.entity.Interval;
import java.util.Collection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import markesi.business.AnnotationEJB;
import markesi.business.IntervalEJB;
import markesi.business.SubFileEJB;
import markesi.entity.Annotation;
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
public class AnnotationEJBTest {

    private static EJBContainer container;
    private static AnnotationEJB annotationEJB;
    private static SubFileEJB subFileEJB;
    private static IntervalEJB intervalEJB;

    public AnnotationEJBTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        try {
            container = javax.ejb.embeddable.EJBContainer.createEJBContainer();

            annotationEJB = (AnnotationEJB) container.getContext().lookup(
                    "java:global/classes/AnnotationEJB");
            subFileEJB = (SubFileEJB) container.getContext().lookup(
                    "java:global/classes/SubFileEJB");
            intervalEJB = (IntervalEJB) container.getContext().lookup(
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
        Annotation annotationReturned = annotationEJB.create("test");
        assertEquals(annotationReturned, annotationEJB.findById(annotationReturned.getId()));
    }

    @Test
    public void testDelete() {

        Annotation annotationReturned = annotationEJB.create("test");
        assertEquals(annotationReturned, annotationEJB.findById(annotationReturned.getId()));
        annotationEJB.delete(annotationReturned);
        //on ne trouve plus l'annotation
        assertNull(annotationEJB.findById(annotationReturned.getId()));
    }
    
    @Test
    public void TestAddInterval() {
        Annotation annotationReturned = annotationEJB.create("test2");
        Interval i = intervalEJB.create(0, 5);
        
        assertEquals(0, annotationReturned.getIntervalCollection().size());
        
        annotationEJB.addInterval(annotationReturned, i);
        
        annotationReturned = annotationEJB.findById(annotationReturned.getId());
        
        assertEquals(1, annotationReturned.getIntervalCollection().size());
    }
    
    @Test
    public void TestAddIntervals() {
        Annotation annotationReturned = annotationEJB.create("test3");
        Interval i = intervalEJB.create(0, 5);
        Interval i2 = intervalEJB.create(1, 15);
        Interval i3 = intervalEJB.create(16, 25);
        Collection<Interval> intervalCollection = new ArrayList<Interval>();
        intervalCollection.add(i);
        intervalCollection.add(i2);
        intervalCollection.add(i3);
        
        assertEquals(0, annotationReturned.getIntervalCollection().size());
        
        annotationEJB.addIntervals(annotationReturned.getId(), intervalCollection);
        
        annotationReturned = annotationEJB.findById(annotationReturned.getId());
        
        assertEquals(3, annotationReturned.getIntervalCollection().size());
    }

}
