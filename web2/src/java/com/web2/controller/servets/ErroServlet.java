
package com.web2.controller.servets;

import com.web2.model.exceptions.AppException;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name = "Erro", urlPatterns = {"/Erro"})
public class ErroServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String msg = (String) request.getAttribute("msg");
        String page = (String) request.getAttribute("page");
        AppException erro = (AppException) request.getAttribute("exception");
        
        request.setAttribute("msg", msg);
        request.setAttribute("page", page);
        request.setAttribute("erro", erro);
        if(erro != null){
            erro.printStackTrace();
        }
                
        
        if(msg == null || page == null){
            request.setAttribute("msg", "CONTATE O DESENVOLVEDOR");
            request.setAttribute("page", "/");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Erro.jsp");
            rd.forward(request, response);
            return;
        }
        

        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Erro.jsp");
        rd.forward(request, response);
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
