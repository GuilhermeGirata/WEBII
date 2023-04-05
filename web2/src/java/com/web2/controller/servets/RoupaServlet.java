package com.web2.controller.servets;

import com.web2.model.beans.LoginBean;
import com.web2.model.beans.Roupa;
import com.web2.model.exceptions.DeleteRoupaException;
import com.web2.model.exceptions.GetAllRoupasException;
import com.web2.model.exceptions.GetRoupaException;
import com.web2.model.exceptions.InsertRoupaException;
import com.web2.model.exceptions.UpdateRoupaException;
import com.web2.model.exceptions.ViolacaoConstraintException;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import com.web2.model.facade.LogicaRoupa;
import com.web2.model.util.ValidaCampo;

@WebServlet(name = "RoupaServlet", urlPatterns = {"/Roupa"})
public class RoupaServlet extends HttpServlet {

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

                Double preco = ValidaCampo.validaDouble(request.getParameter("preco"));
                if (preco == null) {
                    flag++;
                    request.setAttribute("precoMsg", "PREÇO INVÁLIDO");
                }

                Integer qtdeDias = ValidaCampo.validaInteger(request.getParameter("qtde"), 3);
                if (qtdeDias == null) {
                    flag++;
                    request.setAttribute("qtdeMsg", "LIMITE INVÁLIDO");
                }

                if (flag > 0) {
                    if ("updateBD".equals(acao)) {
                        Roupa rp;
                        rp = LogicaRoupa.getRoupa(id);
                        request.setAttribute("up", ".");
                        request.setAttribute("rp", rp);
                    }
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/ModRoupa.jsp");
                    rd.forward(request, response);
                    return;

                } else {
                    try {
                        if ("inserirBD".equals(acao)) {
                            LogicaRoupa.insertRoupa(new Roupa(nome, preco, qtdeDias));
                        } else {
                            LogicaRoupa.updateRoupa(new Roupa(id, nome, preco, qtdeDias));
                        }
                        response.sendRedirect("http://" + request.getServerName() + ":" + request.getServerPort()
                                + request.getContextPath() + "/Roupa");
                        return;
                    } catch (ViolacaoConstraintException ex) {
                        request.setAttribute("nomeMsg", "ESSE NOME JÁ ESTÁ EM USO");
                        getServletContext().getRequestDispatcher("/ModRoupa.jsp").forward(request, response);
                    }
                }
            } else if ("excluir".equals(acao)) {
                if (id == null) {
                    return;
                }
                try {
                    LogicaRoupa.deleteRoupa(id);
                    response.sendRedirect("http://" + request.getServerName() + ":" + request.getServerPort()
                            + request.getContextPath() + "/Roupa");
                    return;
                } catch (DeleteRoupaException ex) {
                    request.setAttribute("msg", ex.getMessage() + ". PROVAVELMENTE AINDA EXISTE UM PEDIDO REFERENCIANDO ESSA PEÇA NO BD");
                    request.setAttribute("page", "/FuncionarioHome");
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/Erro");
                    rd.forward(request, response);
                }
            } else if ("editar".equals(acao)) { //Carrega a tela de edição
                if (id == null) {return;}
                Roupa rp;
                rp = LogicaRoupa.getRoupa(id);
                request.setAttribute("up", ".");
                request.setAttribute("rp", rp);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/ModRoupa.jsp");
                rd.forward(request, response);
            } else if ("inserir".equals(acao)) { //Carrega a tela de Inserção          
                getServletContext().getRequestDispatcher("/ModRoupa.jsp").forward(request, response);
            }
            
        } catch (GetRoupaException | InsertRoupaException | UpdateRoupaException ex) {
            request.setAttribute("msg", ex.getMessage());
            request.setAttribute("page", "/FuncionarioHome");
            request.setAttribute("exception", ex);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Erro");
            rd.forward(request, response);
            return;
        }

        //Acontece sem nenhuma condição
        try {
            ArrayList<Roupa> lista = LogicaRoupa.getAllRoupas();
            request.setAttribute("lista", lista);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/ListaRoupa.jsp");
            rd.forward(request, response);
        } catch (GetAllRoupasException e) {
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
