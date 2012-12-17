/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.facade;

import java.util.Collection;
import javax.ejb.Remote;
import markesi.entity.Annotation;
import markesi.entity.Interval;
import markesi.entity.SubFile;
import markesi.entity.Submission;
import markesi.entity.User;
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
    public void addAnnotation(Long fileId, String text, 
            Collection<Interval> intervals) throws MarkESIException;
    public String getFilePath(Long fileId);
    public Submission getSubmissionSingle() throws MarkESIException;
    public Collection<SubFile> getSubFilesOfSubmission(Submission sub);    
    public void login(String username, String passwd) throws MarkESIException;    
    public User getUser();    
    public void inscrire(String email, String username, String password, String nom, String prenom)throws MarkESIException;  
    public void logout() ;
}
