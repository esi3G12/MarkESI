/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.entity;

import java.io.Serializable;
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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author G35309
 */
@Entity
@Table(name = "SUBFILE")
@XmlRootElement
@NamedQuery(name = "SubFile.getSubmission", query = "SELECT s.submission FROM SubFile s WHERE s.id = :id")
public class SubFile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SubFile")
    @TableGenerator(name = "SubFile", allocationSize = 1)
    private Long id;
    @Column(name = "FILE_NAME")
    @Basic(optional = false)
    private String fileName;
    @Basic(optional = false)
    @Column(name = "DAT")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @Column(name = "FILE_PATH")
    private String filePath;
    
    @JoinColumn(name = "SUBMISSION", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Submission submission;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subfile")
    private Collection<Annotation> annotationCollection;

    public SubFile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String name) {
        this.fileName = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String path) {
        this.filePath = path;
    }

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    public Collection<Annotation> getAnnotationCollection() {
        return annotationCollection;
    }

    public void setAnnotationCollection(Collection<Annotation> annotationCollection) {
        this.annotationCollection = annotationCollection;
    }

    public void addAnnotation(Annotation toAdd) {
        this.annotationCollection.add(toAdd);
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
        if (!(object instanceof SubFile)) {
            return false;
        }
        SubFile other = (SubFile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "markesi.entity.SubFile[ id=" + id + " ]";
    }
}
