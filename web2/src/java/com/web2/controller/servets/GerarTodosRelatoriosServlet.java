/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.web2.controller.servets;

import com.web2.model.beans.LoginBean;
import com.web2.model.dao.ConectarBD;
import com.web2.model.exceptions.DAOException;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.OutputStream;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

/**
 *
 * @author jeffe
 */
@WebServlet(name = "GerarTodosRelatoriosServlet", urlPatterns = {"/GerarTodosRelatoriosServlet"})
public class GerarTodosRelatoriosServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        LoginBean usu = (LoginBean) session.getAttribute("loginBean");
        if (usu == null) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/home");
            request.setAttribute("msg", "Usuario Deve se Autenticar para acessar o sistema");
            rd.forward(request, response);
            return;
        }

        if (usu.getIdFuncionario() == null) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/home");
            request.setAttribute("msg", "Acesso Restrito");
            rd.forward(request, response);
            return;
        }
        
        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "attachment; filename=TodosOsRelatorios.pdf");

        
        HashMap params = new HashMap();
        Date dt1 = (Date) request.getAttribute("dt1");
        Date dt2 = (Date) request.getAttribute("dt2");
        params.put("dt1", dt1);
        params.put("dt2", dt2);
        
        String host = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

        try {
            java.sql.Connection conn = ConectarBD.conectar();
            List<JasperPrint> list = new ArrayList<>();
            JasperPrint jp1 = JasperFillManager.fillReport(new URL(host + "/Receitas.jasper").openStream(), params, conn);
            JasperPrint jp2 = JasperFillManager.fillReport(new URL(host + "/Pedidos.jasper").openStream(), params, conn);
            JasperPrint jp3 = JasperFillManager.fillReport(new URL(host + "/ClientesFieis.jasper").openStream(), null, conn);
            JasperPrint jp4 = JasperFillManager.fillReport(new URL(host + "/Clientes.jasper").openStream(), null, conn);
            
            list.add(jp1);
            list.add(jp2);
            list.add(jp3);
            list.add(jp4);
            
            OutputStream ops = response.getOutputStream();
            JRPdfExporter pdfExporter = new JRPdfExporter();
            pdfExporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, list);
            pdfExporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, ops);
            pdfExporter.exportReport();
  
        } // Fechamento do try
        catch (DAOException e) {
            request.setAttribute("msg", "Erro de DAO : " + e.getMessage());
            request.setAttribute("page", "/Relatorio");
            request.getRequestDispatcher("/Erro").forward(request, response);
        } catch (JRException e) {
            request.setAttribute("msg", "Erro no Jasper : " + e.getMessage());
            request.setAttribute("page", "/Relatorio");
            request.getRequestDispatcher("/Erro").forward(request, response);
        }
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
