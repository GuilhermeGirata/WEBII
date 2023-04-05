/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.web2.controller.servets;

import com.web2.model.beans.Funcionario;
import com.web2.model.beans.LoginBean;
import com.web2.model.exceptions.AccountExistException;
import com.web2.model.exceptions.InsertFuncionarioException;
import com.web2.model.exceptions.LogicaLoginException;
import com.web2.model.exceptions.WrongLoginException;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.web2.model.facade.LogicaFuncionario;
import com.web2.model.facade.LogicaLogin;

/**
 *
 * @author jeffe
 */
@WebServlet(urlPatterns = {"/login", "/home"})
public class HomeServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        try {
            LogicaFuncionario.insertFuncionario(new Funcionario(null, "root", "root", "root@root.com"));
        } catch (AccountExistException | InsertFuncionarioException ex) {
            System.out.println("Conta Root Já Existe!");
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String acao = request.getParameter("acao");
        String mensagem = (String) request.getAttribute("msg");
 
        HttpSession session2 = request.getSession(true);
        LoginBean usu = (LoginBean) session2.getAttribute("loginBean");
        if (usu != null) {
            String path = "/";
            if (usu.getIdFuncionario() != null) {
                path = "/FuncionarioHome";
            } else if (usu.getCpfCliente() != null) {
                path = "/ClienteHome";
            }

            response.sendRedirect("http://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath() + path);
            return;
        }
        
        if("gologin".equals(acao)){
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
            rd.forward(request, response);
        }else 
        if("login".equals(acao)){
            String login = request.getParameter("login");
            String senha = request.getParameter("senha");
            
            if(login == null || senha == null){
                request.setAttribute("msg","USUÁRIO/SENHA NÃO PODEM ESTAR VAZIAS");
                RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                rd.forward(request, response);            
            }
            
            try {      
                LoginBean bean = LogicaLogin.login(login,senha);
                HttpSession session = request.getSession(true);
                session.setAttribute("loginBean", bean);
                String path = "/";
                if(bean.getIdFuncionario() != null){
                    //Vai pra página inicial do funcionario
                    path = "/FuncionarioHome";
                }else if(bean.getCpfCliente() != null){
                    path = "/ClienteHome";
                }                           

                response.sendRedirect("http://" + request.getServerName() + ":" + request.getServerPort()
                        + request.getContextPath() + path);
                return;
            
            }catch (WrongLoginException ex) {
                request.setAttribute("msg","USUÁRIO/SENHA ESTÃO INCORRETOS");
                RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                rd.forward(request, response);
                
            } catch (LogicaLoginException ex) {
                request.setAttribute("msg", ex.getMessage());
                request.setAttribute("page", "/");
                request.setAttribute("exception", ex);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/Erro");
                rd.forward(request, response);
            }
        }

       if(mensagem != null){request.setAttribute("msg", mensagem);}
       RequestDispatcher rd = getServletContext().getRequestDispatcher("/Index.jsp");
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
