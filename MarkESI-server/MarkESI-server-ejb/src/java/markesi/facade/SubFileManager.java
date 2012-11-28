/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.facade;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import markesi.business.SubFileEJB;
import markesi.business.SubmissionEJB;
import markesi.entity.SubFile;
import markesi.entity.Submission;
import markesi.exceptions.MarkESIException;

/**
 *
 * @author G35309
 */
@Stateless
public class SubFileManager implements SubFileManagerRemote {

    @EJB
    private SubFileEJB subfileEJB;
    
    @EJB
    private SubmissionEJB submissionEJB;

    @Override
    public Submission addSubmission(String name) throws MarkESIException{
        testForLegalName(name);        
        Submission sub = submissionEJB.addSubmission(name);
        File file = new File("C:\\UserLocal\\submissions\\"+ sub.getId() + "_" + name + "\\");
        file.mkdirs();
        return sub;
    }

    @Override
    public Submission getSubmissionById(Long id) {
        return submissionEJB.getSubmissionById(id);
    }
    
    
    @Override
    public void addSubFileToSubmission(String subFileContent, String subFileName, Submission submission) throws MarkESIException {
        testForLegalName(subFileName);
        InputStream stream = new ByteArrayInputStream(subFileContent.getBytes());
        String path = "C:\\UserLocal\\submissions\\" + submission.getId() + "_" + submission.getName() + "\\";
        SubFile subFile = subfileEJB.add(stream, subFileName, path);
        submissionEJB.addSubFile(submission, subFile);
    }

    private void testForLegalName(String name) throws MarkESIException {
        /* \/:*?<>"| */
        if (name == null || name.isEmpty()) {
            throw new MarkESIException("The name of a submission cannot be null or empty");
        }
        if (name.contains("\\") || name.contains("/") || name.contains(":") ||
                name.contains("*") || name.contains("?") || name.contains(">") ||
                name.contains("<") || name.contains("\"") || name.contains("|")){
            throw new MarkESIException("The name of a submission cannot contain these characters: \\/:*?<>\"|");
        }
    }

}
