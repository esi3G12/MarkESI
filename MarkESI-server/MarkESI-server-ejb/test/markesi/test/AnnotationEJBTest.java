/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import markesi.entity.Interval;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import markesi.business.AnnotationEJB;
import markesi.business.SubFileEJB;
import markesi.entity.Annotation;
import markesi.entity.SubFile;
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
    @EJB
    private static AnnotationEJB AnnotationEJB;
    private static SubFileEJB subFileEJB;

    public AnnotationEJBTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        try {
            container = javax.ejb.embeddable.EJBContainer.createEJBContainer();

            AnnotationEJB = (AnnotationEJB) container.getContext().lookup(
                    "java:global/classes/AnnotationEJB");
            subFileEJB = (SubFileEJB) container.getContext().lookup(
                    "java:global/classes/SubFileEJB");
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
        Annotation annotationReturned = AnnotationEJB.create("test");
        assertEquals(annotationReturned, AnnotationEJB.findById(annotationReturned.getId()));
    }

    @Test
    public void addAnnotationWithIntervals() {
        Collection<Interval> intervals = new ArrayList<Interval>();
        Interval inter = new Interval();
        Interval inter2 = new Interval();
        inter.setBegin(1);
        inter.setEnd(5);
        inter2.setBegin(9);
        inter2.setEnd(15);
        intervals.add(inter);
        intervals.add(inter2);

        Annotation annotationReturned = AnnotationEJB.createWithIntervals("test", intervals);
        //on a bien une collection de taille2
        assertEquals(2, annotationReturned.getIntervalCollection().size());

    }

    @Test
    public void addAnnotationWithIntervals2() {
        Collection<Interval> intervals = new ArrayList<Interval>();
        Interval inter = new Interval();
        Interval inter2 = new Interval();
        inter.setBegin(1);
        inter.setEnd(5);
        inter2.setBegin(9);
        inter2.setEnd(15);
        intervals.add(inter);
        intervals.add(inter2);

        int i = 0;
        Annotation annotationReturned = AnnotationEJB.createWithIntervals("test2", intervals);
        for (Interval interval : annotationReturned.getIntervalCollection()) {
            if (interval.getBegin() == inter.getBegin() && interval.getEnd() == inter.getEnd()) {
                i++;
            }
            //les intervals ont bien Ã©tÃ© sauvÃ© (ID!=0)
            assertFalse(interval.getId() == 0);
        }
        //on a bien un seul interval égal à inter (pas deux)
        assertEquals(1, i);

    }

    @Test
    public void testDelete() {

        Annotation annotationReturned = AnnotationEJB.create("test");
        assertEquals(annotationReturned, AnnotationEJB.findById(annotationReturned.getId()));
        AnnotationEJB.delete(annotationReturned);
        //on ne trouve plus l'annotation
        assertNull(AnnotationEJB.findById(annotationReturned.getId()));
    }

    @Test
    public void addIntervalSimilaires() {
        Collection<Interval> intervals = new ArrayList<Interval>();
        Interval inter = new Interval();
        Interval inter2 = new Interval();
        inter.setBegin(1);
        inter.setEnd(5);
        inter2.setBegin(1);
        inter2.setEnd(5);
        intervals.add(inter);
        intervals.add(inter2);

        System.out.println("Premier ajout:");
        Annotation annotationReturned = AnnotationEJB.createWithIntervals("test", intervals);

        Collection<Interval> intervals2 = new ArrayList<Interval>();
        Interval inter3 = new Interval();
        Interval inter4 = new Interval();
        inter3.setBegin(1);
        inter3.setEnd(5);
        inter4.setBegin(1);
        inter4.setEnd(5);
        intervals.add(inter3);
        intervals.add(inter4);

        System.out.println("deuxieme ajout:");
        AnnotationEJB.addIntervals(annotationReturned.getId(), intervals2);

        //on ne devrait pas pouvoir ajouter deux intervals égaux dans une meme annotation
        assertEquals(1, annotationReturned.getIntervalCollection().size());

    }

    @Test
    public void addIntervalChevauche() {
        Collection<Interval> intervals = new ArrayList<Interval>();
        Interval inter = new Interval();
        Interval inter2 = new Interval();
        inter.setBegin(1);
        inter.setEnd(5);
        inter2.setBegin(3);
        inter2.setEnd(8);
        intervals.add(inter);
        intervals.add(inter2);

        int i = 0;
        Annotation annotationReturned = AnnotationEJB.createWithIntervals("test", intervals);
        for (Interval interval : annotationReturned.getIntervalCollection()) {
            if (interval.getBegin() == inter.getBegin() && interval.getEnd() == inter.getEnd()) {
                i++;
            }

        }
        //on ne devrait pas pouvoir ajouter des intervals qui se chevauchent
        assertEquals(1, annotationReturned.getIntervalCollection().size());
    }
}
