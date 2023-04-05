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
import java.util.Date;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author guilh
 */
@WebServlet(urlPatterns = {"/GerarRelatorioServlet"})
public class GerarRelatorioServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        
        HashMap params = new HashMap();
        String acao = (String)request.getAttribute("acao");
        Date dt1 = (Date)request.getAttribute("dt1");
        Date dt2 = (Date)request.getAttribute("dt2");        
        
        String nomePagina = null; //Nome do arquivoo .jasper
        
        if ("clienteAll".equals(acao)) {
            nomePagina = "/Clientes.jasper";
        } else if ("clienteFiel".equals(acao)) {
            nomePagina = "/ClientesFieis.jasper";
        } else if ("receita".equals(acao)) {
            params.put("dt1", dt1);
            params.put("dt2", dt2);
            nomePagina = "/Receitas.jasper";
        } else if("pedido".equals(acao)){
            params.put("dt1", dt1);
            params.put("dt2", dt2);
            nomePagina = "/Pedidos.jasper";
        }
        
        if(nomePagina == null){
            getServletContext().getRequestDispatcher("/GerarRelatorio.jsp").forward(request, response);
        }
        
        try {
            String nm = "attachment; filename=" + acao + ".pdf";
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", nm);

            // Caminho contextualizado do relatório compilado
            String jasper = request.getContextPath() + nomePagina;
            
            
            // Host onde o servlet esta executando 
            String host = "http://" + request.getServerName() + ":" + request.getServerPort();
            
            // URL para acesso ao relatório
            URL jasperURL = new URL(host + jasper);
            // Geração do relatório

            byte[] bytes = JasperRunManager.runReportToPdf(jasperURL.openStream(), params, ConectarBD.conectar());
            if (bytes != null) {// A página será mostrada em PDF
                // Envia o PDF para o Cliente
                OutputStream ops = response.getOutputStream();
                ops.write(bytes);
            }
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
