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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import markesi.business.AnnotationEJB;
import markesi.business.SubFileEJB;
import markesi.business.SubmissionEJB;
import markesi.entity.Annotation;
import markesi.entity.SubFile;
import markesi.entity.Submission;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Auguste
 */
public class SubFileEJBTest {

    private static EJBContainer container;
    private static SubFileEJB subFileEJB;
    private static AnnotationEJB AnnotationEJB;
    private static SubmissionEJB submissionEJB;

    public SubFileEJBTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        try {
            subFileEJB = (SubFileEJB) container.getContext().lookup(
                    "java:global/classes/SubFileEJB");
            AnnotationEJB = (AnnotationEJB) container.getContext().lookup(
                    "java:global/classes/AnnotationEJB");
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
    public void addSubFile() throws FileNotFoundException, IOException {
        new File("C:\\UserLocal\\FileTest.java").createNewFile();
        File file = new File("C:\\UserLocal\\FileTest.java");
        FileOutputStream os = new FileOutputStream(file);
        os.write(new String("ceci est un fichier de test").getBytes());
        InputStream input = new FileInputStream(file);

        SubFile subFile = subFileEJB.add(input, "testFile.java", "C:\\UserLocal\\");
        assertEquals(subFile, subFileEJB.getSubFileById(subFile.getId()));
        assertNull(subFileEJB.getSubFileById(13l));
    }

    @Test
    public void attachAnnotation() {
        try {

            FileOutputStream os = null;
            //on commence par creer un fichier
            new File("C:\\UserLocal\\FileTest.java").createNewFile();
            File file = new File("C:\\UserLocal\\FileTest.java");
            os = new FileOutputStream(file);
            os.write(new String("ceci est un fichier de test").getBytes());
            InputStream input = new FileInputStream(file);
            SubFile subFile = subFileEJB.add(input, "testFile.java", "C:\\UserLocal\\");
            SubFile subFile2 = subFileEJB.add(input, "testFile.java", "C:\\UserLocal\\");

            assertNotNull(subFile.getFileName());
            System.out.println("\n\n\n\n\n\n\n\n\n" + subFile.getFileName());
            os.close();
            Annotation annotationReturned = AnnotationEJB.create("test");

            System.out.println("id subfile 1: " + subFile.getId());
            System.out.println("id subfile 2: " + subFile2.getId());

            System.out.println("id annotation: " + annotationReturned.getId());

            subFileEJB.addAnnotation(subFile2, annotationReturned);
            System.out.println("annotation ajoutee" + "\n\n\n\n\n\n\n\n\n\n\n\n\n");

            //on doit réattacher le tout... 
            subFile2 = subFileEJB.getSubFileById(subFile2.getId());
            annotationReturned = AnnotationEJB.findById(annotationReturned.getId());

            assertEquals(1, subFile2.getAnnotationCollection().size());

            assertNotNull(annotationReturned.getSubFile());
        } catch (IOException ex) {
            System.out.println("ERREUR:" + ex.getMessage());
            Logger.getLogger(AnnotationEJBTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
        }
    }
    
    @Test
    public void getSubmission() throws IOException {
        Submission submission = submissionEJB.addSubmission("test");
        new File("C:\\UserLocal\\FileTest.java").createNewFile();
        File file = new File("C:\\UserLocal\\FileTest.java");
        FileOutputStream os = new FileOutputStream(file);
        os.write("ceci est un fichier de test".getBytes());
        InputStream input = new FileInputStream(file);
        
        SubFile subFile = subFileEJB.add(input, "testFile.java", "C:\\UserLocal\\");
        submissionEJB.addSubFile(submission, subFile);
        Submission subFromDB = subFileEJB.getSubmission(subFile);
        assertEquals(submission, subFromDB);
    }
}
