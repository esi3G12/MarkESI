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
import javax.persistence.EmbeddedId;
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
@Table(name = "utilisateur")
public class User implements Serializable {

    @Id
    @Column(name = "username", unique = true, nullable = false)
    private String userName;
    // @OneToMany(cascade = CascadeType.ALL, mappedBy = "submission")
    //private Collection<Submission> submissionCollection;
    @Basic(optional = false)
    @Column(name = "NOM")
    private String nom;
    @Basic(optional = false)
    @Column(name = "PRENOM")
    private String prenom;
    //password doit être stocké en MD5...
    @Basic(optional = false)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @Column(name = "EMAIL")
    private String email;
    /*
     public Collection<Submission> getSubmissionCollection() {
     return submissionCollection;
     }

     public void setSubmissionCollection(Collection<Submission> submissionCollection) {
     this.submissionCollection = submissionCollection;
     }
     * */

    public String getId() {
        return userName;
    }

    public void setId(String id) {
        this.userName = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userName != null ? userName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userName == null && other.userName != null) || (this.userName != null && !this.userName.equals(other.userName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "g12.annotation.entity.User[ id=" + userName + " ]";
    }
}
