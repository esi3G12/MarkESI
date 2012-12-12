/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.business;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
   
   public Submission getOneSubmission() {
       Query query = em.createNamedQuery("Submission.getAll");
       List<Submission> list = query.getResultList();
       if(list.isEmpty()) {
           return null;           
       }
       return list.get(0);
   }
   
   public Collection<SubFile> getSubFilesOfSubmission(Submission sub) {
       sub = em.find(Submission.class, sub.getId());
       return sub.getSubFiles();
   }
   
   public Submission addSubmission(String name) {
       Submission submission = new Submission();
       submission.setDate(new Date());
       submission.setName(name);
       
       em.persist(submission);
       return submission;
   }

   public void addSubFile(Submission submission, SubFile subFile) {
       submission = em.find(Submission.class, submission.getId());
       subFile = em.find(SubFile.class, subFile.getId());
       submission.getSubFiles().add(subFile);
       subFile.setSubmission(submission);
       em.merge(submission);
       em.merge(subFile);
   }
}
