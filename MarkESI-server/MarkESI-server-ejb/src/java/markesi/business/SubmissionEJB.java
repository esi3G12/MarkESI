/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.business;

import java.util.Date;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import markesi.entity.SubFile;
import markesi.entity.Submission;

/**
 *
 * @author g34871
 */
@Stateless
@LocalBean
public class SubmissionEJB {
    @PersistenceContext(unitName = "MarkESI-PU")
    private EntityManager em;

   public Submission getSubmissionById(Long id) {
       return em.find(Submission.class, id);
   }
   
   public Submission addSubmission(String name) {
       Submission submission = new Submission();
       submission.setDate(new Date());
       submission.setName(name);
       
       em.persist(submission);
       return submission;
   }

   public void addSubFile(Submission submission, SubFile subFile) {
       submission.getSubFiles().add(subFile);
       subFile.setSubmission(submission);
       em.merge(submission);
       em.merge(subFile);
   }
}
