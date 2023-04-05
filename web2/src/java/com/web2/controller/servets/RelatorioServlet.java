/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.web2.controller.servets;

import com.web2.model.beans.LoginBean;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.web2.model.util.ValidaCampo;

/**
 *
 * @author jeffe
 */
@WebServlet(name = "RelatorioServlet", urlPatterns = {"/Relatorio"})
public class RelatorioServlet extends HttpServlet {

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
        
        if(usu.getIdFuncionario() == null){
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/home");
            request.setAttribute("msg", "Acesso Restrito");
            rd.forward(request, response);
            return;            
        }
        
        String acao = request.getParameter("acao");

        LocalDate dt1 = ValidaCampo.validaData(request.getParameter("dataInicio"));
        LocalDate dt2 = ValidaCampo.validaData(request.getParameter("dataFim"));

        if (dt1 != null && dt2 != null) {
            if (dt2.compareTo(dt1) > 0) {
                Date data1 = java.sql.Date.valueOf(dt1);
                Date data2 = java.sql.Date.valueOf(dt2);
                request.setAttribute("dt1", data1);
                request.setAttribute("dt2", data2);

                if ("receita".equals(acao) || "pedido".equals(acao)) {
                    request.setAttribute("acao", acao);
                    getServletContext().getRequestDispatcher("/GerarRelatorioServlet").forward(request, response);
                }else if ("todos".equals(acao)) {
                    getServletContext().getRequestDispatcher("/GerarTodosRelatoriosServlet").forward(request, response);
                }
                
            } else {
                request.setAttribute("erro", "A DATA DE INÍCIO NÃO PODE SER MAIOR QUE A DATA FINAL");
                getServletContext().getRequestDispatcher("/GerarRelatorio.jsp").forward(request, response);
                return;
            }
        } else if (dt1 == null && dt2 == null) {
            
            
            try {
                Date data1 = new SimpleDateFormat( "dd/MM/yyyy" ).parse( "01/01/1900" );
                Date data2 = new SimpleDateFormat( "dd/MM/yyyy" ).parse( "01/01/2900" );
                request.setAttribute("dt1", data1);
                request.setAttribute("dt2", data2);
                
                if ("receita".equals(acao) || "pedido".equals(acao)) {
                    request.setAttribute("acao", acao);
                    getServletContext().getRequestDispatcher("/GerarRelatorioServlet").forward(request, response);
                }else if("todos".equals(acao)){
                    getServletContext().getRequestDispatcher("/GerarTodosRelatoriosServlet").forward(request, response);
                }
            } catch (ParseException e) {
                request.setAttribute("msg", e.getMessage());
                request.setAttribute("page", "/FuncionarioHome");
                request.setAttribute("exception", e);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/Erro");
                rd.forward(request, response);
            }
            
        } else if ((dt1 == null && dt2 != null) || (dt1 != null && dt2 == null)) {
            request.setAttribute("erro", "AS DATAS OU DEVEM ESTAR VAZIAS, OU AS DUAS PREENCHIDAS");
            getServletContext().getRequestDispatcher("/GerarRelatorio.jsp").forward(request, response);
            return;
        }
        
        
        if("clienteAll".equals(acao) || "clienteFiel".equals(acao)) { //Todos os Clientes
            request.setAttribute("acao", acao);
            getServletContext().getRequestDispatcher("/GerarRelatorioServlet").forward(request, response);
        }

        getServletContext().getRequestDispatcher("/GerarRelatorio.jsp").forward(request, response);
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
