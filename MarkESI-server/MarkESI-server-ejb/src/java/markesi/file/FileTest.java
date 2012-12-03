/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.file;

import javax.ejb.EJB;
import markesi.business.AnnotationEJB;
import markesi.facade.SubFileManager;

/**
 *
 * @author G35309
 */
public class FileTest {
    
    @EJB
    private static SubFileManager facade;
    
    @EJB
    private static AnnotationEJB annot;
    
    public static void main(String[] args) {
        
    }
}
