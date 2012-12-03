/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.business;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import markesi.entity.Annotation;
import markesi.entity.Interval;
import markesi.exception.EndBeforeBeginException;

/**
 *
 * @author LSV
 */
@Stateless
@LocalBean
public class IntervalEJB {

    @PersistenceContext(unitName = "MarkESI-PU")
    private EntityManager em;

    public Interval create(int beginPos, int endPos) throws EndBeforeBeginException {
        Interval interval = new Interval();
        interval.setBegin(beginPos);
        interval.setEnd(endPos);
        em.persist(interval);
        return interval;
    }

    public Interval findById(Long id) {
        return em.find(Interval.class, id);
    }

    public void delete(Interval interval) {
        em.remove(findById(interval.getId()));
    }
    
    //pas besoin de creer de mÃ©thodes modifier a mon sens, il suffit de supprimer et recrÃ©er;
    //ou plus simplement de bien selectionner (l'interface permet de modifier la selection)
}
