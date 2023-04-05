
package com.web2.controller.servets;

import com.web2.model.beans.Roupa;
import com.google.gson.Gson;
import com.web2.model.exceptions.GetAllRoupasException;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import com.web2.model.facade.LogicaRoupa;


@WebServlet(name = "GetRoupaJson", urlPatterns = {"/GetRoupaJson"})
public class GetRoupaJson extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/javascript;charset=UTF-8");
        try {
            ArrayList<Roupa> rps = LogicaRoupa.getAllRoupas();
            String js = " let regraBD = 0; rp = {};" + toJSON(rps);
            byte[] bytes = js.getBytes(StandardCharsets.UTF_8);
            if (bytes != null) {
                // Envia o Js para o Cliente
                OutputStream ops = response.getOutputStream();
                ops.write(bytes);
            }
        } catch (GetAllRoupasException ex) {
            request.setAttribute("msg", ex.getMessage());
            request.setAttribute("page", "/ClienteHome");
            request.setAttribute("exception", ex);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Erro");
            rd.forward(request, response);            
        }
        
    }
    
    
    private String toJSON(ArrayList<Roupa> list) {
        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder();
        for (Roupa roupa : list) {
            sb.append("rp[\"id").append(roupa.getId()).append("\"] = ").append(gson.toJson(roupa)).append(";");
        }
        return sb.toString();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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

}
