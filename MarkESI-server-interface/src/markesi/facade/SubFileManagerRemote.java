/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.facade;

import javax.ejb.Remote;
import markesi.entity.Submission;
import markesi.exceptions.MarkESIException;

/**
 *
 * @author G35309
 */
@Remote
public interface SubFileManagerRemote {
    
    public Submission addSubmission(String name) throws MarkESIException;
    public Submission getSubmissionById(Long id);
    public void addSubFileToSubmission(String subFileContent, 
            String subFileName, Submission submission) throws MarkESIException;
}
