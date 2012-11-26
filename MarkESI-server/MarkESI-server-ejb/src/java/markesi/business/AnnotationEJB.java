/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.business;

import java.util.Collection;
import java.util.Date;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import markesi.entity.Annotation;
import markesi.entity.Interval;
import markesi.entity.SubFile;
import markesi.exception.IntervalOverlapException;

/**
 *
 * @author LSV
 */
@Stateless
@LocalBean
public class AnnotationEJB {

    @PersistenceContext(unitName = "MarkESI-PU")
    private EntityManager em;

    public Annotation create(String text) {
        Annotation annotation = new Annotation();
        annotation.setText(text);
        annotation.setDate(new Date());
        em.persist(annotation);
        return annotation;
    }

    public Annotation createWithIntervals(String test, Collection<Interval> intervals) throws IntervalOverlapException {
        Annotation annot = create(test);
        em.persist(annot);
        addIntervals(annot.getId(), intervals);
        return annot;
    }

    public Annotation findById(Long id) {
        return em.find(Annotation.class, id);
    }

    public void delete(Annotation annotation) {
        em.remove(findById(annotation.getId()));
    }

    public void addIntervals(Long idAnnotation, Collection<Interval> intervalToAdd) throws IntervalOverlapException {
        Annotation annot = findById(idAnnotation);
        for (Interval toAdd : intervalToAdd) {
                annot.addInterval(toAdd);
        }
  //      em.persist(annot);
    }
}
