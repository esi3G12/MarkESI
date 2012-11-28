/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.client.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author g34784
 */
public class JsonController extends HttpServlet {

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
        String action = request.getParameter("action");

        if (action != null && !action.equals("")) {
            if (action.equals("get")) {
                getJSON(request, response);
            } else if (action.equals("post")) {
                postJSON(request, response);
            }
        }
    }

    public JSONObject jsonAnnotation(String text, String date, Collection<JSONObject> sels)
            throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("text", text);
        obj.put("date", date);
        obj.put("selections", sels);
        return obj;
    }

    public JSONObject jsonSelection(int start, int end, int length) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("start", start);
        obj.put("end", end);
        obj.put("length", length);
        return obj;
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

    private void getJSON(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = request.getParameter("fileName");
        if (fileName != null && !fileName.equals("")) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = null;
            try {
                writer = response.getWriter();
                JSONObject json = new JSONObject();
                try {
                    JSONArray annots = addAnnotations();
                    json.put("annotations", annots);

                } catch (JSONException ex) {
                    Logger.getLogger(JsonController.class.getName()).log(Level.SEVERE, null, ex);
                }
                writer.write(json.toString());
            } finally {
                writer.close();
            }
        }
    }

    private JSONArray addAnnotations() throws JSONException {
        JSONArray annots = new JSONArray();
        Collection<JSONObject> sels = new ArrayList<JSONObject>();
        sels.add(jsonSelection(10, 15, 5));
        sels.add(jsonSelection(20, 21, 1));
        sels.add(jsonSelection(45, 55, 10));
        sels.add(jsonSelection(68, 85, 17));
        annots.put(jsonAnnotation("texte annot 1", "17/11/2012", sels));
        sels = new ArrayList<JSONObject>();
        sels.add(jsonSelection(35, 67, 32));
        sels.add(jsonSelection(85, 104, 19));
        annots.put(jsonAnnotation("texte annot 2", "17/11/2012", sels));
        return annots;
    }

    private void postJSON(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        String selectionsStr = request.getParameter("selections");
        
        if (selectionsStr == null || selectionsStr.equals("")) {
            response.getWriter().write("error");
            return;
        }
        
        // enlève les [] au début et à la fin :
        selectionsStr = selectionsStr.substring(1, selectionsStr.length() - 1);
        try {
            JSONObject selections = new JSONObject(selectionsStr);
            response.getWriter().write("success");
            //TODO change this return for error
        } catch (JSONException ex) {
            response.getWriter().write("error");
        }
        //traitement des selections
    }
}
