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
import markesi.business.SubFileEJB;
import markesi.entity.SubFile;
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
            
    public SubFileEJBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        try {
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
    
}
