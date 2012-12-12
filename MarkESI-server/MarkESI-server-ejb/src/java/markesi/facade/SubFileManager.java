/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.facade;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import markesi.business.AnnotationEJB;
import markesi.business.IntervalEJB;
import markesi.business.SubFileEJB;
import markesi.business.SubmissionEJB;
import markesi.entity.Annotation;
import markesi.entity.Interval;
import markesi.entity.SubFile;
import markesi.entity.Submission;
import markesi.exceptions.MarkESIException;

/**
 *
 * @author G35309
 */
@Stateless
public class SubFileManager implements SubFileManagerRemote {

    @EJB
    private SubFileEJB subFileEJB;
    @EJB
    private SubmissionEJB submissionEJB;
    @EJB
    private IntervalEJB intervalEJB;
    @EJB
    private AnnotationEJB annotationEJB;

    @Override
    public Submission addSubmission(String name) throws MarkESIException {
        testForLegalName(name);
        Submission sub = submissionEJB.addSubmission(name);
        File file = new File("C:\\UserLocal\\submissions\\" + sub.getId() + "_" + name + "\\");
        file.mkdirs();
        return sub;
    }

    @Override
    public Submission getSubmissionById(Long id) {
        return submissionEJB.getSubmissionById(id);
    }

    @Override
    public Submission getSubmissionSingle() throws MarkESIException {
        Submission sub = submissionEJB.getOneSubmission();
        if(sub == null){
            return this.addSubmission("test");
        }
        return sub;
    }
    
    @Override
    public Collection<SubFile> getSubFilesOfSubmission(Submission sub) {
        Collection<SubFile> c = submissionEJB.getSubFilesOfSubmission(sub);
        c.size();
        return c;
    }
    
    @Override
    public void addSubFileToSubmission(String subFileContent, String subFileName, Submission submission) throws MarkESIException {
        testForLegalName(subFileName);
        InputStream stream = new ByteArrayInputStream(subFileContent.getBytes());
        String path = "C:\\UserLocal\\submissions\\" + submission.getId() + "_" + submission.getName() + "\\";
        SubFile subFile = subFileEJB.add(stream, subFileName, path);
        submissionEJB.addSubFile(submission, subFile);
    }

    private void testForLegalName(String name) throws MarkESIException {
        /* \/:*?<>"| */
        if (name == null || name.isEmpty()) {
            throw new MarkESIException("The name of a submission cannot be null or empty");
        }
        if (name.contains("\\") || name.contains("/") || name.contains(":")
                || name.contains("*") || name.contains("?") || name.contains(">")
                || name.contains("<") || name.contains("\"") || name.contains("|")) {
            throw new MarkESIException("The name of a submission cannot contain these characters: \\/:*?<>\"|");
        }
    }

    @Override
    public Collection<Annotation> getAnnotations(Long subFileId) {
        SubFile file = subFileEJB.getSubFileById(subFileId);
        if (file != null) {
            return subFileEJB.getAnnotations(file);
        } else {
            return null;
        }
    }

    @Override
    public void addAnnotation(Long fileId, String text, Collection<Interval> intervals)
            throws MarkESIException {
        if (fileId == null) {
            throw new MarkESIException("aucun fichier n'est spécifier !");
        } else if (text == null || text.equals("")) {
            throw new MarkESIException("aucun texte n'a été écrit pour l'annotation");
        }
        
        SubFile file = subFileEJB.getSubFileById(fileId);
        
        Annotation annot = createAnnotationWithIntervals(text, intervals);
        subFileEJB.addAnnotation(file, annot);
    }

    private Annotation createAnnotationWithIntervals(String text, Collection<Interval> intervals) throws MarkESIException {
        if (intervals == null || intervals.isEmpty()) {
            throw new MarkESIException("Ajout d'annotation sans sélections impossible");
        }

        boolean isIncorrect = false;
        Iterator<Interval> checkIt = intervals.iterator();
        Interval currInterval = checkIt.next();
        Interval nextInterval;

        while (!isIncorrect && checkIt.hasNext()) {
            nextInterval = checkIt.next();
            isIncorrect = intervalEJB.intersects(currInterval, nextInterval);
            currInterval = nextInterval;
        }

        if (isIncorrect) {
            throw new MarkESIException("Il y a des intersections dans les sélections");
        }
        
        Annotation annot = annotationEJB.create(text);
        annotationEJB.addIntervals(annot.getId(), intervals);
        
        return annotationEJB.findById(annot.getId());
    }
    
    public String getFilePath(Long fileId) {
        return subFileEJB.getSubFileById(fileId).getFilePath() + "/"
                + subFileEJB.getSubFileById(fileId).getFileName();
    }
}
