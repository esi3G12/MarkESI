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

/**
 *
 * @author LSV
 */
@Stateless
@LocalBean
public class AnnotationEJB {

    @PersistenceContext(unitName = "MarkESI-PU")
    private EntityManager em;

    public Annotation add(String text) {
        Annotation interval = new Annotation();
        interval.setText(text);
        interval.setDate(new Date());
        em.persist(interval);
        return interval;
    }
    
    public Annotation findById(Long id){
        return em.find(Annotation.class, id);
    }
    
}
