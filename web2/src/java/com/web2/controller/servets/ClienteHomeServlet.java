/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.web2.controller.servets;

import com.web2.model.beans.LoginBean;
import com.web2.model.beans.Pedido;
import com.web2.model.beans.Roupa;
import com.google.gson.Gson;
import com.web2.model.exceptions.GetAllRoupasException;
import com.web2.model.exceptions.InsertPedidoException;
import com.web2.model.exceptions.SelectPedidosClienteException;
import com.web2.model.exceptions.StatusManagementException;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import com.web2.model.facade.LogicaClientePedido;
import com.web2.model.facade.LogicaRoupa;
import com.web2.model.util.ValidaCampo;

/**
 *
 * @author jeffe
 */
@WebServlet(name = "ClienteHomeServlet", urlPatterns = {"/ClienteHome"})
public class ClienteHomeServlet extends HttpServlet {

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

        String acao = request.getParameter("acao");
        String idCliente;
        
        HttpSession session = request.getSession(true);
        LoginBean usu = (LoginBean) session.getAttribute("loginBean");
        if (usu == null) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/home");
            request.setAttribute("msg", "Usuario Deve se Autenticar para acessar o sistema");
            rd.forward(request, response);
            return;
        }
        
        idCliente = usu.getCpfCliente();
        if(idCliente == null){
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/home");
            request.setAttribute("msg", "Acesso Restrito");
            rd.forward(request, response);
            return;            
        }
        
        try {
            if ("fazerPedido".equals(acao)) { //Executa a ação de inserir o Pedido Feito No BD
                ArrayList<Roupa> rp = new ArrayList<>();
                String[] ids = request.getParameterValues("id");
                String[] qtdes = request.getParameterValues("qtde");
                if (ids == null || qtdes == null) {
                    request.setAttribute("msg", "Pedido Inválido");
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/FazerPedido.jsp");
                    rd.forward(request, response);
                }
                for (int i = 0; i < ids.length; i++) {
                    Integer id = ValidaCampo.validaInteger(ids[i], 3);
                    Integer qtde = ValidaCampo.validaInteger(qtdes[i], 3);
                    if (id == null || qtde == null) {
                        request.setAttribute("msg", "Pedido Inválido");
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/FazerPedido.jsp");
                        rd.forward(request, response);
                        return;
                    }
                    rp.add(new Roupa(id, qtde));
                }
                LogicaClientePedido.insertPedido(rp, idCliente);
                response.sendRedirect("http://" + request.getServerName() + ":" + request.getServerPort()
                        + request.getContextPath() + "/ClienteHome");
                return;

            } else if ("carregaRoupas".equals(acao)) { //Manda a lista de Roupas com suas informações para tela de Fazer Pedido

                ArrayList<Roupa> rps = LogicaRoupa.getAllRoupas();
                request.setAttribute("lista", rps);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/FazerPedido.jsp");
                rd.forward(request, response);

            } else if ("consulta".equals(acao)) { //Abre os detalhes de um Pedido na tela ConsultaPedido
                String id2 = (String) request.getParameter("id");
                Integer id = ValidaCampo.validaInteger(id2, Integer.MAX_VALUE);
                if (id == null) {
                    request.setAttribute("msg", "ID DO PEDIDO É INVÁLIDO");
                    request.setAttribute("page", "/ClienteHome");
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/Erro");
                    rd.forward(request, response);
                    return;
                }

                Pedido pd = LogicaClientePedido.selectClientePedido(id, idCliente);
                if (pd != null) {
                    request.setAttribute("pd", pd);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/ConsultaPedido.jsp");
                    rd.forward(request, response);
                }

            } else if ("pedido".equals(acao)) { //Altera o Estado do Pedido Por Parte do Cliente
                Integer idpd = ValidaCampo.validaInteger((String) request.getParameter("id"), Integer.MAX_VALUE);
                String pdAcao = (String) request.getParameter("status");
                if (pdAcao == null || idpd == null) {
                    return;
                }

                if ("CA".equals(pdAcao)) {
                    LogicaClientePedido.cancelPedido(idpd, idCliente);
                } else if ("PG".equals(pdAcao)) {
                    LogicaClientePedido.changeStatusPedido(idpd, idCliente);
                }
                response.sendRedirect("http://" + request.getServerName() + ":" + request.getServerPort()
                        + request.getContextPath() + "/ClienteHome?acao=consulta&id=" + idpd);
                return;
            }else if("pesquisa".equals(acao)){
                String st = (String) request.getParameter("status");
                if("AB".equals(st) || "CA".equals(st) || "RJ".equals(st) || "RC".equals(st) || "AP".equals(st) || "PG".equals(st) || "FI".equals(st))
                {
                    ArrayList<Pedido> lista = LogicaClientePedido.selectByStatus(st, idCliente);
                    request.setAttribute("lista", lista);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/ClienteHome.jsp");
                    rd.forward(request, response);
                }
            }else if ("pesquisaText".equals(acao)) {

                Integer idpedido = ValidaCampo.validaInteger((String) request.getParameter("texto"), Integer.MAX_VALUE);
                if (idpedido == null) {
                    request.setAttribute("lista", null);
                    request.setAttribute("msgPesq", idpedido);
                    getServletContext().getRequestDispatcher("/ClienteHome.jsp").forward(request, response);
                }
               
                ArrayList<Pedido> lista = LogicaClientePedido.selectClientePedidoAndId(idCliente, idpedido);
                request.setAttribute("msgPesq", idpedido);
                request.setAttribute("lista", lista);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/ClienteHome.jsp");
                rd.forward(request, response);
            }
            
            
        } catch (StatusManagementException | SelectPedidosClienteException | GetAllRoupasException | InsertPedidoException ex) {
            request.setAttribute("msg", ex.getMessage());
            request.setAttribute("page", "/ClienteHome");
            request.setAttribute("exception", ex);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Erro");
            rd.forward(request, response);
        }
        
        
        //Acontece quando n tem nenhuma acao - Abre a Tela Home do Cliente
        try {
            ArrayList<Pedido> lista = LogicaClientePedido.selectClientePedidos(idCliente);
            request.setAttribute("lista", lista);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/ClienteHome.jsp");
            rd.forward(request, response);
        } catch (SelectPedidosClienteException ex) {
            request.setAttribute("msg", ex.getMessage());
            request.setAttribute("page", "/Logout");
            request.setAttribute("exception", ex);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Erro");
            rd.forward(request, response);
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
