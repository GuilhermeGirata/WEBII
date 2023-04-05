/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.web2.controller.servets;

import com.web2.model.beans.Funcionario;
import com.web2.model.beans.LoginBean;
import com.web2.model.exceptions.AccountExistException;
import com.web2.model.exceptions.DeleteFuncionarioException;
import com.web2.model.exceptions.InsertFuncionarioException;
import com.web2.model.exceptions.SelectFuncionarioException;
import com.web2.model.exceptions.UpdateFuncionarioException;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import com.web2.model.facade.LogicaFuncionario;
import com.web2.model.util.ValidaCampo;

/**
 *
 * @author jeffe
 */
@WebServlet(name = "FuncionarioServlet", urlPatterns = {"/Funcionario"})
public class FuncionarioServlet extends HttpServlet {

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
        Integer id = ValidaCampo.validaInteger(request.getParameter("id"), Integer.MAX_VALUE);

        try {
            if ("inserirBD".equals(acao) || "updateBD".equals(acao)) {
                Integer flag = 0;
                if (id == null) {
                    if (!"inserirBD".equals(acao)) {
                        return;
                    }
                }

                String nome = ValidaCampo.validaString(request.getParameter("nome"), 3, 255);
                if (nome == null) {
                    flag++;
                    request.setAttribute("nomeMsg", "NOME INVÁLIDO");
                }

                String senha = ValidaCampo.validaString(request.getParameter("senha"), 3, 255);
                if (senha == null) {
                    flag++;
                    request.setAttribute("senhaMsg", "NOME INVÁLIDO");
                }

                String email = ValidaCampo.validaString(request.getParameter("email"), 3, 255);
                if (email == null) {
                    flag++;
                    request.setAttribute("emailMsg", "NOME INVÁLIDO");
                }
                if (flag > 0) {
                    if ("updateBD".equals(acao)) {
                        Funcionario fc;
                        fc = LogicaFuncionario.getFuncionario(id);
                        request.setAttribute("up", ".");
                        request.setAttribute("fc", fc);
                    }
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/ModFuncionario.jsp");
                    rd.forward(request, response);
                    return;

                } else {
                    try {
                        if ("inserirBD".equals(acao)) {
                            LogicaFuncionario.insertFuncionario(new Funcionario(nome, senha, email));
                        } else {
                            LogicaFuncionario.updateFuncionario(new Funcionario(id, nome, senha, email));
                        }
                        response.sendRedirect("http://" + request.getServerName() + ":" + request.getServerPort()
                                + request.getContextPath() + "/Funcionario");
                        return;
                    } catch (AccountExistException ex) {
                        request.setAttribute("emailMsg", "ESSE EMAIL JÁ ESTÁ EM USO");
                        getServletContext().getRequestDispatcher("/ModFuncionario.jsp").forward(request, response);
                    }
                }
            } else if ("excluir".equals(acao)) {
                if (id == null) {
                    return;
                }
                LogicaFuncionario.deleteFuncionario(id);
                response.sendRedirect("http://" + request.getServerName() + ":" + request.getServerPort()
                        + request.getContextPath() + "/Funcionario");
                return;

            } else if ("editar".equals(acao)) { //Carrega a tela de edição
                if (id == null) {
                    return;
                }
                Funcionario fc;
                fc = LogicaFuncionario.getFuncionario(id);
                request.setAttribute("up", ".");
                request.setAttribute("fc", fc);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/ModFuncionario.jsp");
                rd.forward(request, response);

            } else if ("inserir".equals(acao)) { //Carrega a tela de Inserção          
                getServletContext().getRequestDispatcher("/ModFuncionario.jsp").forward(request, response);
            }
            
        } catch (SelectFuncionarioException | DeleteFuncionarioException | InsertFuncionarioException | UpdateFuncionarioException ex) {
            request.setAttribute("msg", ex.getMessage() + " CONTATE O DESENVOLVEDOR");
            request.setAttribute("page", "/FuncionarioHome");
            request.setAttribute("exception", ex);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Erro");
            rd.forward(request, response);
            return;
        }

        //Acontece sem nenhuma condição
        try {
            ArrayList<Funcionario> lista = LogicaFuncionario.getAllFuncionarios();
            request.setAttribute("lista", lista);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/ListaFuncionario.jsp");
            rd.forward(request, response);
        } catch (SelectFuncionarioException e) {
            request.setAttribute("msg", e.getMessage());
            request.setAttribute("page", "/FuncionarioHome");
            request.setAttribute("exception", e);
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
