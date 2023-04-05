package com.web2.controller.servets;

import com.web2.model.beans.LoginBean;
import com.web2.model.beans.Pedido;
import com.web2.model.exceptions.ChangeStatusErrorException;
import com.web2.model.exceptions.SelectPedidosFuncionarioException;
import com.web2.model.exceptions.StatusManagementException;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import com.web2.model.facade.LogicaFuncionarioPedido;
import com.web2.model.util.ValidaCampo;

@WebServlet(name = "FuncionarioHomeServlet", urlPatterns = {"/FuncionarioHome"})
public class FuncionarioHomeServlet extends HttpServlet {

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

        try {
            if ("consulta".equals(acao)) { //Abre os detalhes de um Pedido na tela ConsultaPedido
                String id2 = (String) request.getParameter("id");
                Integer id = ValidaCampo.validaInteger(id2, Integer.MAX_VALUE);
                if (id == null) {
                    request.setAttribute("msg", "ID DO PEDIDO É INVÁLIDO");
                    request.setAttribute("page", "/FuncionarioHome");
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/Erro");
                    rd.forward(request, response);
                    return;
                }

                Pedido pd = LogicaFuncionarioPedido.selectPedidoById(id);
                if (pd != null) {
                    request.setAttribute("funcionario", ".");
                    request.setAttribute("pd", pd);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/ConsultaPedido.jsp");
                    rd.forward(request, response);
                }

            } else if ("pedido".equals(acao)) { //Altera o Estado do Pedido Por Parte do Funcionario
                Integer idpd = ValidaCampo.validaInteger((String) request.getParameter("id"), Integer.MAX_VALUE);
                String pdAcao = (String) request.getParameter("status");
                String pagina = (String) request.getParameter("pg");
                
                if (pdAcao == null || idpd == null) {
                    return;
                }
                
                if ("RJ".equals(pdAcao)) {
                    LogicaFuncionarioPedido.rejectPedido(idpd);
                } else {
                    LogicaFuncionarioPedido.changeStatusPedido(idpd);
                }
                
                String tela = "/FuncionarioHome?acao=consulta&id=" + idpd;
                if(pagina != null){
                    tela = "/FuncionarioHome?acao=todos";
                }
                
                response.sendRedirect("http://" + request.getServerName() + ":" + request.getServerPort()
                        + request.getContextPath() + tela);
                return;

            } else if ("hoje".equals(acao) || "todos".equals(acao)) {//seleciona os pedidos de hoje ou todos os pedidos
                ArrayList<Pedido> lista = null;
                if ("hoje".equals(acao)) {
                    lista = LogicaFuncionarioPedido.selectAllPedidosHoje();
                } else {
                    lista = LogicaFuncionarioPedido.selectAllPedidos();
                }
                request.setAttribute("lista", lista);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/FuncionarioHome.jsp");
                rd.forward(request, response);

            } else if ("data".equals(acao)) {
                LocalDate dt1 = ValidaCampo.validaData(request.getParameter("dataInicio"));
                LocalDate dt2 = ValidaCampo.validaData(request.getParameter("dataFim"));
                if (dt1 != null && dt2 != null) {
                    if (dt2.compareTo(dt1) > 0) {

                        ArrayList<Pedido> lista = LogicaFuncionarioPedido.selectAllPedidosByDate(dt1, dt2);
                        request.setAttribute("lista", lista);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/FuncionarioHome.jsp");
                        rd.forward(request, response);

                    } else {
                        request.setAttribute("erro", "A DATA DE INÍCIO NÃO PODE SER MAIOR QUE A DATA FINAL");
                    }
                } else {
                    request.setAttribute("erro", "AS DATAS NÃO PODEM ESTAR VAZIAS");
                }
                //Depois disso manda pra tela inicial com a msg de erro
            }

        } catch (SelectPedidosFuncionarioException | ChangeStatusErrorException | StatusManagementException ex) {
            request.setAttribute("msg", ex.getMessage());
            request.setAttribute("page", "/FuncionarioHome");
            request.setAttribute("exception", ex);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Erro");
            rd.forward(request, response);
        }

        //Acontece quando n tem nenhuma acao - Abre a Tela Home do Funcionario
        try {
            ArrayList<Pedido> lista = LogicaFuncionarioPedido.selectAllPedidosAberto();
            request.setAttribute("lista", lista);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/FuncionarioHome.jsp");
            rd.forward(request, response);
        } catch (SelectPedidosFuncionarioException ex) {
            request.setAttribute("msg", ex.getMessage() + ". DESLOGANDO DE SUA CONTA...");
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
