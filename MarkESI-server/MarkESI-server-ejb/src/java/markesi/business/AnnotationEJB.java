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
import markesi.entity.Annotation;
import markesi.entity.Interval;
import markesi.entity.SubFile;

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

    public Annotation findById(Long id) {
        return em.find(Annotation.class, id);
    }

    public void delete(Annotation annotation) {
        em.remove(findById(annotation.getId()));
    }

    public void addIntervals(Long idAnnotation, Collection<Interval> intervalToAdd) {
        Annotation annot = findById(idAnnotation);
        for (Interval toAdd : intervalToAdd) {
            addInterval(annot, toAdd);
        }
    }
    
    public void addInterval(Long idAnnotation, Interval intervalToAdd) {
        Annotation annot = findById(idAnnotation);
        addInterval(annot, intervalToAdd);
    }
    
    public void addInterval(Annotation annot, Interval intervalToAdd) {
        annot.addInterval(intervalToAdd);
        intervalToAdd.setAnnotation(annot);
        em.merge(annot);
        em.merge(intervalToAdd);
    }

    public Collection<Interval> getIntervals(Annotation annot) {
        return annot.getIntervalCollection();
    }
}
