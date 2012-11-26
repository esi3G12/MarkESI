/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.business;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import markesi.entity.Annotation;
import markesi.entity.SubFile;

/**
 *
 * @author Auguste
 */
@Stateless
@LocalBean
public class SubFileEJB {

    @EJB
    private AnnotationEJB annotationEJB;
    private static final int TAILLE_TAMPON = 10240;
    @PersistenceContext(unitName = "MarkESI-PU")
    private EntityManager em;

    public SubFile getSubFileById(Long id) {
        return em.find(SubFile.class, id);
    }

    public SubFile add(InputStream content, String fileName, String path) {
        SubFile subFile = new SubFile();
        subFile.setDate(new Date());
        subFile.setFileName(fileName);
        subFile.setFilePath(path);
        save(content, path, fileName);
        em.persist(subFile);
        return subFile;
    }

    private void save(InputStream content, String path, String fileName) {
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        try {
            entree = new BufferedInputStream(content, TAILLE_TAMPON);
            sortie = new BufferedOutputStream(new FileOutputStream(new File(path + fileName)),
                    TAILLE_TAMPON);

            byte[] tampon = new byte[TAILLE_TAMPON];
            int longueur = 0;
            while ((longueur = entree.read(tampon)) > 0) {
                sortie.write(tampon, 0, longueur);
            }
        } catch (IOException ex) {
            Logger.getLogger(SubFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                sortie.close();
            } catch (IOException ignore) {
            }
            try {
                entree.close();
            } catch (IOException ignore) {
            }
        }
    }

    public void setAnnotation(SubFile fileIn, Annotation annot) {
        //si le fichier est déja persisté, il faut le récupérer car il pourrait
        // etre détaché..
        if (fileIn.getId() != 0) {
            fileIn = getSubFileById(fileIn.getId());
        }
        //si l'annotation est déja persistée, il faut la récupérer (car elle est 
        //détachée du gestionnaire d'entité)..
        if (annot.getId() != 0) {
            annot = annotationEJB.findById(annot.getId());
        }

        fileIn.addAnnotation(annot);
        em.persist(fileIn);
        
        //on provoque le chargement de ce qui n'est pas automatiquement attaché
        annot.getSubFile().getFileName();
        fileIn.getAnnotationCollection().size();
    }
}
