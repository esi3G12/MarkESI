/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import markesi.business.AnnotationEJB;
import markesi.business.IntervalEJB;
import markesi.business.SubFileEJB;
import markesi.entity.Interval;
import markesi.entity.SubFile;
import markesi.entity.Submission;
import markesi.exceptions.MarkESIException;
import markesi.test.SubmissionEJBTest;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author G35309
 */
public class SubFileManagerTest {

    private static EJBContainer container;
    private static SubFileManagerRemote subfileManager;
    private static SubFileEJB subFileEJB;
    private static AnnotationEJB annotationEJB;
    private static IntervalEJB intervalEJB;

    public SubFileManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        try {
            subfileManager = (SubFileManagerRemote) container.getContext().lookup(
                    "java:global/classes/SubFileManager");
            subFileEJB = (SubFileEJB) container.getContext().lookup(
                    "java:global/classes/SubFileEJB");
            annotationEJB = (AnnotationEJB) container.getContext().lookup(
                    "java:global/classes/AnnotationEJB");
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

    /**
     * Test of addSubmission method, of class SubFileManager.
     */
    @Test
    public void testAddSubmission() throws Exception {
        System.out.println("addSubmission");
        String name = "submission_test";
        Submission result = subfileManager.addSubmission(name);
        assertEquals(subfileManager.getSubmissionById(result.getId()), result);
    }

    @Test
    public void testAddSubmissionsWithSameNames() throws MarkESIException {
        System.out.println("addSubmission");
        String name = "submission_test";
        Submission result = subfileManager.addSubmission(name);
        Submission result2 = subfileManager.addSubmission(name);
        assertNotSame(result2, result);
    }

    @Test(expected = MarkESIException.class)
    public void testAddSubmissionWithInvalidName() throws MarkESIException {
        System.out.println("addSubmission");
        String name = "sub*mi\\ssion_test";
        Submission result = subfileManager.addSubmission(name);
    }

    @Test(expected = MarkESIException.class)
    public void testAddSubmissionWithEmptyName() throws MarkESIException {
        System.out.println("addSubmission");
        String name = "";
        Submission result = subfileManager.addSubmission(name);
    }

    @Test(expected = MarkESIException.class)
    public void testAddSubmissionWithNullName() throws MarkESIException {
        System.out.println("addSubmission");
        String name = null;
        Submission result = subfileManager.addSubmission(name);
    }

//    @Test
//    public void testAddSubFileToSubmission() throws MarkESIException {
//        String subName = "submission_test";                
//        Submission result = subfileManager.addSubmission(subName);
//        String fileName = "test.java";
//        subfileManager.addSubFileToSubmission("content for test", fileName, result);
//        result = subfileManager.getSubmissionById(result.getId());
//        ((List)result.getSubFiles()).size();
//        assertNotNull(((List)result.getSubFiles()).get(0));
//    }
    @Test(expected = MarkESIException.class)
    public void testAddSubFileToSubmissionWithNullName() throws MarkESIException {
        String subName = "submission_test";
        Submission result = subfileManager.addSubmission(subName);
        String fileName = null;
        subfileManager.addSubFileToSubmission("content for test", fileName, result);
    }

    @Test(expected = MarkESIException.class)
    public void testAddSubFileToSubmissionWithEmptyName() throws MarkESIException {
        String subName = "submission_test";
        Submission result = subfileManager.addSubmission(subName);
        String fileName = "";
        subfileManager.addSubFileToSubmission("content for test", fileName, result);
    }

    @Test(expected = MarkESIException.class)
    public void testAddSubFileToSubmissionWithInvalidName() throws MarkESIException {
        String subName = "submission_test";
        Submission result = subfileManager.addSubmission(subName);
        String fileName = "fezf*.java";
        subfileManager.addSubFileToSubmission("content for test", fileName, result);
    }

    @Test
    public void addRightAnnotationTest() throws MarkESIException {
        SubFile file = subFileEJB.add(null, "filename", "C:\\UserLocal\\FileTest.java");
        Interval interval = intervalEJB.create(10, 100);
        ArrayList<Interval> intervals = new ArrayList<Interval>();
        intervals.add(interval);
        subfileManager.addAnnotation(file.getId(), "text", intervals);
        file = subFileEJB.getSubFileById(file.getId());
        assertEquals(file.getAnnotationCollection().size(), 1);
    }

    @Test(expected = MarkESIException.class)
    public void addAnnotationNullTextTest() throws MarkESIException {
        SubFile file = subFileEJB.add(null, "filename", "C:\\UserLocal\\FileTest.java");
        Interval interval = intervalEJB.create(10, 100);
        Collection<Interval> intervals = new ArrayList<Interval>();
        intervals.add(interval);
        subfileManager.addAnnotation(file.getId(), null, intervals);
    }

    @Test(expected = MarkESIException.class)
    public void addAnnotationEmptyTextTest() throws MarkESIException {
        SubFile file = subFileEJB.add(null, "filename", "C:\\UserLocal\\FileTest.java");
        Interval interval = intervalEJB.create(10, 100);
        Collection<Interval> intervals = new ArrayList<Interval>();
        intervals.add(interval);
        subfileManager.addAnnotation(file.getId(), "", intervals);
    }

    @Test(expected = MarkESIException.class)
    public void addAnnotationNullFileTest() throws MarkESIException {
        SubFile file = subFileEJB.add(null, "filename", "C:\\UserLocal\\FileTest.java");
        Interval interval = intervalEJB.create(10, 100);
        Collection<Interval> intervals = new ArrayList<Interval>();
        intervals.add(interval);
        subfileManager.addAnnotation(null, "text", intervals);
    }

    @Test(expected = MarkESIException.class)
    public void addAnnotationNoIntervals() throws MarkESIException {
        SubFile file = subFileEJB.add(null, "filename", "C:\\UserLocal\\FileTest.java");
        subfileManager.addAnnotation(null, "text", null);
    }

    @Test(expected = MarkESIException.class)
    public void addAnnotationAllNull() throws MarkESIException {
        subfileManager.addAnnotation(null, null, null);
    }

    @Test(expected = MarkESIException.class)
    public void addAnnotationWithIntersectIntervals() throws MarkESIException {
        SubFile file = subFileEJB.add(null, "filename", "C:\\UserLocal\\FileTest.java");
        Interval interval = intervalEJB.create(10, 100);
        Interval interval2 = intervalEJB.create(11, 90);
        Collection<Interval> intervals = new ArrayList<Interval>();
        intervals.add(interval);
        intervals.add(interval2);
        subfileManager.addAnnotation(null, "text", intervals);
    }

    @Test(expected = MarkESIException.class)
    public void addAnnotationWithGoodIntervals() throws MarkESIException {
        SubFile file = subFileEJB.add(null, "filename", "C:\\UserLocal\\FileTest.java");
        Interval interval = intervalEJB.create(10, 100);
        Interval interval2 = intervalEJB.create(120, 130);
        Collection<Interval> intervals = new ArrayList<Interval>();
        intervals.add(interval);
        intervals.add(interval2);
        subfileManager.addAnnotation(null, "text", intervals);
        assertNotNull(((ArrayList) (file.getAnnotationCollection())).get(0));
    }
}
