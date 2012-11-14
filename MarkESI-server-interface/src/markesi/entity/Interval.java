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
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 *
 * @author LSV
 */
@Entity
@Table(name = "INTERVAL")
public class Interval implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Interval")
    @TableGenerator(name = "Interval", allocationSize = 1)
    private Long id;
    @Basic(optional = false)
    @Column(name = "BEGINLINE")
    private int beginLine;
    @Basic(optional = false)
    @Column(name = "BEGINCHAR")
    private int beginChar;
    @Basic(optional = false)
    @Column(name = "ENDLINE")
    private int endLine;
    @Basic(optional = false)
    @Column(name = "ENDCHAR")
    private int endChar;
    @JoinColumn(name = "IDANNOTATION", referencedColumnName = "ID")
   // @ManyToOne(optional = false)
   // private Annotation annotation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBeginLine() {
        return beginLine;
    }

    public void setBeginLine(int begin) {
        this.beginLine = begin;
    }

    public int getBeginChar() {
        return beginChar;
    }

    public void setBeginChar(int begin) {
        this.beginChar = begin;
    }

    public int getEndLine() {
        return beginLine;
    }

    public void setEndLine(int end) {
        this.endLine = end;
    }

    public int getEndChar() {
        return endChar;
    }

    public void setEndChar(int end) {
        this.endChar = end;
    }

    /*
    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public Annotation getAnnotation() {
        return this.annotation;
    }*/

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
