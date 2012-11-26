/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 *
 * @author LSV
 */
@Entity
@Table(name = "INTERVAL")
//cette query permet de vérifier qu'un interval ne chevauche aucun autre interval
//sur une même annotation. Si elle renvoit 0, on peut valider l'annotation.
//Si elle renvoit autre 1, on ne peut pas (a priori elle ne devrait jamais renvoyer 2...)


/*@NamedQuery(name="Interval.intervalOverlap", query="SELECT count(inter.beginPos) FROM Interval inter "
                                          + " JOIN  inter.annotation annot"
                                          + " WHERE ((:end BETWEEN inter.beginPos AND inter.endPos)"
                                          + " OR (:begin BETWEEN inter.beginPos AND inter.endPos)"
                                          + " OR (:begin <= inter.beginPos AND :end >= inter.endPos))"
                                          + " AND inter.annotation.id = :id")
                                            // + " GROUP BY inter.annotation")
                                            * */
public class Interval implements Serializable {

    private static final long serialVersionUID = 1L;

    
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Interval")
    @TableGenerator(name = "Interval", allocationSize = 1)
    @Id
    private Long id;
    @Basic(optional = false)
    @Column(name = "beginPos")
    private int beginPos;
    @Basic(optional = false)
    @Column(name = "endPos")
    private int endPos;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "ANNOTATION", referencedColumnName = "ID")
    private Annotation annotation;

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public int getBegin() {
        return beginPos;
    }

    public void setBegin(int begin) {
        this.beginPos = begin;
    }

    public int getEnd() {
        return endPos;
    }

    public void setEnd(int end) {
        this.endPos = end;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Interval)) {
            return false;
        }
        Interval other = (Interval) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "g12.annotation.entity.Interval[ id=" + id + " ]";
    }
}
