/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.facade;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import markesi.entity.Submission;
import markesi.exceptions.MarkESIException;
import markesi.test.SubmissionEJBTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author G35309
 */
public class SubFileManagerTest {
    
    private static EJBContainer container;
    private static SubFileManagerRemote subfileManager;
    
    public SubFileManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        try {
            subfileManager = (SubFileManagerRemote) container.getContext().lookup(
                    "java:global/classes/SubFileManager");            
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
    
    @Test
    public void testAddSubFileToSubmission() throws MarkESIException {
        String subName = "submission_test";                
        Submission result = subfileManager.addSubmission(subName);
        String fileName = "test.java";
        subfileManager.addSubFileToSubmission("content for test", fileName, result);
    }
    
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
}
