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

    public Annotation createWithIntervals(String test, Collection<Interval> intervals) {
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

    public void addIntervals(Long idAnnotation, Collection<Interval> intervalToAdd) {
        Annotation annot = findById(idAnnotation);
        //on doit vérifier que chaque interval ne chevauche aucun autre...
        for (Interval toAdd : intervalToAdd) {
            System.out.println("Recherche interval, parametres: end="+toAdd.getEnd()+" begin="+toAdd.getBegin()+" id="+idAnnotation);
            Query result = em.createNamedQuery("Interval.intervalOverlap")
                    .setParameter("end", toAdd.getEnd())
                    .setParameter("begin", toAdd.getBegin())
                    .setParameter("id", idAnnotation);
            System.out.println("nombre d'overlap : " + result.getResultList().size());
            int nbAnnot = result.getResultList().size();
            if (nbAnnot == 0) {
                annot.addInterval(toAdd);
                em.persist(annot);
            }
        }
    }
}
