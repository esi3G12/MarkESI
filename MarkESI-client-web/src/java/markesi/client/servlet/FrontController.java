/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.client.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import markesi.entity.SubFile;
import markesi.entity.Submission;
import markesi.exceptions.MarkESIException;
import markesi.facade.SubFileManagerRemote;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author G34784
 */
public class FrontController extends HttpServlet {

    @EJB
    private SubFileManagerRemote subFileManager;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String page = "WEB-INF/index.jsp";

        boolean connected = subFileManager.getUser() != null;
        request.setAttribute("connected", connected);
        try {
            String action = request.getParameter("action");
            if (action != null) {
                if (connected || action.equals("signup") || action.equals("adduser")) {
                    if (action.equals("viewFile")) {
                        viewFile(request, response);
                    } else if (action.equals("uploadFile")) {
                        testUp(request, response);
                    } else if (action.equals("viewTree")) {
                        ArrayList<SubFile> list = new ArrayList<SubFile>(subFileManager.getSubFilesOfSubmission(subFileManager.getSubmissionSingle()));
                        request.setAttribute("files", list);
                        page = "js/connectors/jqueryFileTree.jsp";
                    } else if (action.equals("signup")) {
                        request.setAttribute("isSigningUp", true);
                    } else if (action.equals("adduser")) {
                        addUser(request, response);
                    } else if (action.equals("deco")) {
                        deco(request, response);
                        request.setAttribute("connected", false);
                    }
                } else if (action.equals("connexion")) {
                    page = connect(request, response);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            request.setAttribute("errorType", ex.getClass().getName());
            page = "WEB-INF/error.jsp";
        }

        request.getRequestDispatcher(page).forward(request, response);
    }

    private void viewFile(HttpServletRequest request, HttpServletResponse response)
            throws FileNotFoundException, IOException {

        Long fileId = Long.parseLong(request.getParameter("fileId"));
        String filePath = subFileManager.getFilePath(fileId);

        String fileShortName = getShortFileName(filePath);

        request.setAttribute("fileName", fileShortName);
        request.setAttribute("title", "Fichier : " + fileShortName);

        File file = new File(filePath);

        if (file.exists()) {
            FileInputStream myStream = new FileInputStream(filePath);
            String myString = IOUtils.toString(myStream);

            request.setAttribute("file", StringEscapeUtils.escapeHtml(myString));
        } else {
            throw new FileNotFoundException("Ce fichier n'existe pas !");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String getShortFileName(String fileName) {
        String replace = fileName.replace("\\", "/");
        String[] pathParts = replace.split("/");
        //we take the last part = just the name of the file
        return pathParts[pathParts.length - 1];
    }

    private void testUp(HttpServletRequest request, HttpServletResponse response) {
        // Create a new file upload handler
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
        int yourMaxMemorySize = 512 * 1024 * 8; 
        int yourMaxRequestSize = 1024 * 1024 * 8;
        String yourTempDirectory = "Z:\\Test\\"; 
        fileItemFactory.setSizeThreshold(yourMaxMemorySize);
        upload.setSizeMax(yourMaxRequestSize);
        List items;
        try {
            items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString();
                } 
                else {
                    String fieldName = item.getFieldName();
                    String fileName = item.getName();
                    String contentType = item.getContentType();
                    boolean isInMemory = item.isInMemory();
                    long sizeInBytes = item.getSize();

                    boolean writeToFile = true;
                    if (sizeInBytes > 512 * 1024 * 8) {
                        writeToFile = false;
                    }
                    if ((writeToFile) & (fieldName.equals("source"))) { 
                        File temp = File.createTempFile(fileName, "");
                        temp.deleteOnExit();
                        item.write(temp);
                        String fileContent = FileUtils.readFileToString(temp, "UTF-8");
                        Submission sub = subFileManager.getSubmissionSingle();
                        subFileManager.addSubFileToSubmission(fileContent, fileName, sub);
                    } /*else { // Streaming
                        File uploadedFile = new File(yourTempDirectory + fileName); 
                        // sinon
                        // un	nouveau nom de fichier à la place de fileName
                        InputStream sourceFile;
                        try {
                            sourceFile = item.getInputStream();
                            OutputStream destinationFile;
                            try {
                                destinationFile = new FileOutputStream(uploadedFile);
                                byte buffer[] = new byte[512 * 1024];
                                int nbLecture;
                                while ((nbLecture = sourceFile.read(buffer)) != -1) {
                                    destinationFile.write(buffer, 0, nbLecture);
                                }
                                sourceFile.close();
                            } catch (FileNotFoundException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
*/
                }
            }
        } catch (FileUploadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String connect(HttpServletRequest request, HttpServletResponse response) throws MarkESIException {
        request.setAttribute("title", "Connect");
        
            subFileManager.login(request.getParameter("login"), request.getParameter("pass"));
            request.setAttribute("connected", true);
            return "WEB-INF/index.jsp";
        
    }

    private void deco(HttpServletRequest request, HttpServletResponse response) {
        subFileManager.logout();
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("title", "New user");
        try {
            String email = request.getParameter("email");
            String login = request.getParameter("login");
            String passw = request.getParameter("pass");
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            subFileManager.inscrire(email, login, passw, nom, prenom);
          //  subFileManager.login(login, passw);
          //  request.setAttribute("connected", true);
        } catch (MarkESIException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}