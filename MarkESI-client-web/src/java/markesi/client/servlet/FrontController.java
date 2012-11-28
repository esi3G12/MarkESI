/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.client.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * 
 * @author G34784
 */
public class FrontController extends HttpServlet {

    private static final String PREFIX = "views/";

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

        try {
            String action = request.getParameter("action");
            if (action != null) {
                if (action.equals("viewFile")) {
                    viewFile(request, response);
                } else if (action.equals("manageFile")) {
                    manageFile(request, response);
                } else if (action.equals("exploreFile")) {
                    exploreFile(request, response);
                } else if (action.equals("uploadFile")) {
                    uploadFile(request, response);
                }
            } else {
                //No action = just index page
                indexPage(request, response);
            }
        } catch (Exception ex) {
            request.setAttribute("error", ex.getMessage());
            request.setAttribute("errorType", ex.getClass().getName());
            page = "WEB-INF/error.jsp";
        }

        request.getRequestDispatcher(page).forward(request, response);
    }

    private void viewFile(HttpServletRequest request, HttpServletResponse response)
            throws FileNotFoundException, IOException {
        String fileName = request.getParameter("fileName");
        
        String fileShortName = getShortFileName(fileName);
        
        request.setAttribute("fileName", fileShortName);
        request.setAttribute("title", "Fichier : " + fileShortName);
        
        File file = new File(fileName);

        if (file.exists()) {
            FileInputStream myStream = new FileInputStream(fileName);
            String myString = IOUtils.toString(myStream);
            
            request.setAttribute("file", StringEscapeUtils.escapeHtml(myString));

            List<String> viewsList = Arrays.asList("file-view.jsp");
            setViewsAttribute(request, viewsList);
        } else {
            throw new FileNotFoundException("Le fichier n'existe pas !");
        }
    }

    private void manageFile(HttpServletRequest request, HttpServletResponse response) 
            throws FileNotFoundException, IOException {
        viewFile(request, response);
        
        request.setAttribute("title", "Ajout d'annotation");
        List<String> viewsList = Arrays.asList("file-view.jsp", "add-annotation-view.jsp");
        setViewsAttribute(request, viewsList);
    }
    
    private void exploreFile(HttpServletRequest request, HttpServletResponse response) 
            throws FileNotFoundException, IOException {
        viewFile(request, response);
        request.setAttribute("title", "Vue des annontations");
        List<String> viewsList = Arrays.asList("file-view.jsp", "annotation-view.jsp");
        setViewsAttribute(request, viewsList);
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

    private void indexPage(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("title", "Acceuil");
        List<String> viewsList = Arrays.asList("menu-view.jsp");
        setViewsAttribute(request, viewsList);
    }

    private void setViewsAttribute(HttpServletRequest request, List<String> viewsList) {
        for (int i = 0; i < viewsList.size(); i++) {
            //on rajoute le prefix du chemin à chaque vue
            viewsList.set(i, PREFIX + viewsList.get(i));
        }
        request.setAttribute("views", viewsList);
    }

    private String getShortFileName(String fileName) {
        String replace = fileName.replace("\\", "/");
        String[] pathParts = replace.split("/");
        //we take the last part = just the name of the file
        return pathParts[pathParts.length-1];
    }
    
    private void uploadFile(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("title", "Upload Fichier");
        List<String> viewsList = Arrays.asList("jtreeView.jsp","add-file-view.jsp");
        setViewsAttribute(request, viewsList);
    }
}