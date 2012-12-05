/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.facade;

import java.util.Collection;
import javax.ejb.Remote;
import markesi.entity.Annotation;
import markesi.entity.Interval;
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
    public Collection<Annotation> getAnnotations(Long subFileId);
    public void addSubFileToSubmission(String subFileContent, 
            String subFileName, Submission submission) throws MarkESIException;
    public void addAnnotation(Long fileId, String text, Collection<Interval> intervals) throws MarkESIException;
}
