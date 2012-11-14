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

/**
 *
 * @author LSV
 */
@Stateless
@LocalBean
public class IntervalEJB {
    //TODO: persistance unit a modifier 
    @PersistenceContext(unitName = "MarkESI-PU")
    private EntityManager em;

    public Interval create(int beginLine, int beginChar, int endLine, int endChar) {
        Interval interval = new Interval();
        interval.setBeginLine(beginLine);
        interval.setBeginChar(beginChar);
        interval.setEndLine(endLine);
        interval.setEndChar(endChar);      
        
        em.persist(interval);
        return interval;
    }
    
    public void delete(Interval interval){   
        em.remove(interval);
    }


}
