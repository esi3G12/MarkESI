/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import markesi.business.SubFileEJB;
import markesi.business.SubmissionEJB;
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
 * @author g34871
 */
public class SubmissionEJBTest {

    private static EJBContainer container;
    
    private static SubmissionEJB submissionEJB;
    private static SubFileEJB subfileEJB;

    public SubmissionEJBTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        try {
            submissionEJB = (SubmissionEJB) container.getContext().lookup(
                    "java:global/classes/SubmissionEJB");
            subfileEJB = (SubFileEJB) container.getContext().lookup(
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
    public void addSubmission() {
        Submission submission = submissionEJB.addSubmission("test");
        assertEquals(submission, submissionEJB.getSubmissionById(submission.getId()));
        assertNull(submissionEJB.getSubmissionById(13l));
    }

    @Test
    public void addFileToSubmission() throws IOException{
        Submission submission = submissionEJB.addSubmission("test");
        new File("C:\\UserLocal\\FileTest.java").createNewFile();
        File file = new File("C:\\UserLocal\\FileTest.java");
        FileOutputStream os = new FileOutputStream(file);
        os.write(new String("ceci est un fichier de test").getBytes());
        InputStream input = new FileInputStream(file);
        
        SubFile subFile = subfileEJB.add(input, "testFile.java", "C:\\UserLocal\\");
        submissionEJB.addSubFile(submission, subFile);
        ArrayList<SubFile> l = new ArrayList<SubFile>(submission.getSubFiles());
        assertEquals(subFile, l.get(0));
    }
     
}
