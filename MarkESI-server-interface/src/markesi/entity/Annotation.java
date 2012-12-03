/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author LSV
 */
@Entity
@Table(name = "ANNOTATION")
public class Annotation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Annotation")
    @TableGenerator(name = "Annotation", allocationSize = 1)
    private Long id;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "annotation")
    private Collection<Interval> intervalCollection;
    
    @Basic(optional = false)
    @Column(name = "TEXT")
    private String text;
    @Column(name = "DATEANNOT")
    @Temporal(TemporalType.DATE)
    private Date dateAnnot;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "SUBFILE", referencedColumnName = "ID")
    private SubFile subfile;

    public Annotation() {
        this.intervalCollection = new ArrayList<Interval>();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return dateAnnot;
    }

    public void setDate(Date date) {
        this.dateAnnot = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Collection<Interval> getIntervalCollection() {
        return intervalCollection;
    }

    public void setIntervalCollection(Collection<Interval> intervalCollection) {
        this.intervalCollection = intervalCollection;
    }

    public void addInterval(Interval toAdd) {
        this.intervalCollection.add(toAdd);
    }

    public void setSubFile(SubFile subfile) {
        this.subfile = subfile;
    }

    public SubFile getSubFile() {
        return this.subfile;
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
        if (!(object instanceof Annotation)) {
            return false;
        }
        Annotation other = (Annotation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "g12.annotation.entity.Annotation[ id=" + id + " ]";
    }
}
