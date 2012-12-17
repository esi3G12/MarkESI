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
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import markesi.entity.Annotation;
import markesi.entity.Interval;
import markesi.exceptions.MarkESIException;
import markesi.facade.SubFileManagerRemote;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author g34784
 */
public class JsonController extends HttpServlet {

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
        String fileId = request.getParameter("fileId");
        if (fileId != null && !fileId.equals("")) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = null;
            try {
                writer = response.getWriter();
                JSONObject json = new JSONObject();
                try {
                    json.put("annotations", addAnnotations(fileId));
                } catch (JSONException ex) {
                    Logger.getLogger(JsonController.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(json.toString());
                writer.write(json.toString());
            } finally {
                writer.close();
            }
        }
    }

    private JSONArray addAnnotations(String fileId) throws JSONException {
        Collection<Annotation> annots = subFileManager.getAnnotations(Long.parseLong(fileId));
        JSONArray jsonAnnots = new JSONArray();

        for (Annotation annot : annots) {
            Collection<JSONObject> sels = new ArrayList<JSONObject>();

            for (Interval interval : subFileManager.getIntervals(annot.getId())) {
                JSONObject sel = jsonSelection(interval.getBegin(), interval.getEnd(),
                        interval.getEnd() - interval.getBegin());
                sels.add(sel);
            }

            jsonAnnots.put(jsonAnnotation(annot.getText(), annot.getDate().toString(), sels));
        }

        return jsonAnnots;
    }

    private void postJSON(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String selectionsStr = request.getParameter("json");
        String fileId = request.getParameter("fileId");

        if (selectionsStr != null && !selectionsStr.equals("")) {

            selectionsStr = "{\"selections\":" + selectionsStr + "}";
            System.out.println(selectionsStr);
            try {
                JSONObject selections = new JSONObject(selectionsStr);
                createAnnotation(selections, fileId, request.getParameter("annotation"));
            } catch (JSONException ex) {
                Logger.getLogger(JsonController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        request.getRequestDispatcher("/Front?action=viewFile&fileId=" 
                + fileId).forward(request, response);
    }

    private void createAnnotation(JSONObject selections, String fileId, String text) {
        ArrayList<Interval> list = new ArrayList<Interval>();
        try {
            JSONArray a = selections.getJSONArray("selections");
            for (int i = 0; i < a.length(); i++) {
                Interval interval = new Interval();
                interval.setBegin(((JSONObject) a.get(i)).getInt("start"));
                interval.setEnd(((JSONObject) a.get(i)).getInt("end"));
                list.add(interval);
            }
            subFileManager.addAnnotation(Long.parseLong(fileId), text, list);
        } catch (JSONException ex) {
            System.out.println(ex);
        } catch (MarkESIException ex) {
            System.out.println(ex);
        }
    }
}
