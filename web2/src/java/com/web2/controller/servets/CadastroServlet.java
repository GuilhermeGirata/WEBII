
package com.web2.controller.servets;

import com.web2.model.beans.Cliente;
import com.web2.model.beans.LoginBean;
import com.web2.model.exceptions.AccountExistException;
import com.web2.model.exceptions.CadastraClienteException;
import com.web2.model.exceptions.CpfExistException;
import com.web2.model.exceptions.UpdateClienteException;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.web2.model.facade.LogicaCliente;
import com.web2.model.util.ValidaCampo;


@WebServlet(name = "CadastroServlet", urlPatterns = {"/Cadastrar"})
public class CadastroServlet extends HttpServlet {

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
        String cpf, email;
        
        String acao = request.getParameter("acao");
        String atualizar = request.getParameter("atualizar");
        
        HttpSession session = request.getSession(true);
        LoginBean usu = (LoginBean) session.getAttribute("loginBean");
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

        if("cadastrar".equals(acao)){
            LoginBean usua = (LoginBean) session.getAttribute("loginBean");
            Integer flag = 0;

            cpf = ValidaCampo.validaCpf(request.getParameter("cpf"));
            if(cpf == null){
                flag++;
                request.setAttribute("cpfmsg", "Esse CPF é Inválido");
            }
            
            String nome = ValidaCampo.validaString(request.getParameter("nome"), 5, 255);
            if(nome == null){
                flag++;
                request.setAttribute("nomemsg", "Nome Não Pode Estar Vazio");
            }
            
            email = ValidaCampo.validaEmail(request.getParameter("email"));
            if(email == null){
                flag++;
                request.setAttribute("emailmsg", "Email Não Está No Formato Correto");
            }
            
            String telefone = ValidaCampo.validaTelefone(request.getParameter("telefone"));
            if(telefone == null){
                flag++;
                request.setAttribute("telmsg", "Telefone Não Está Na Formatação Correta");
            }
            
            String cep = ValidaCampo.validaCep(request.getParameter("cep"));
            if(cep == null){
                flag++;
                request.setAttribute("cepmsg", "CEP Não Está Na Formatação Correta");
            } 
            
            Integer num = ValidaCampo.validaInteger(request.getParameter("numero"), 7);
            if(num == null){
                flag++;
                request.setAttribute("nummsg", "Número Inválido");
            }
            
            String comp = ValidaCampo.validaString(request.getParameter("complemento"), 0,  255);
            if(comp == null){
                flag++;
                request.setAttribute("compmsg", "Complemento Não Pode Estar Vazio");
            }
            
            if(flag > 0){
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/Cadastro.jsp");
                rd.forward(request, response);
                return;
            }else{
                
                Cliente cl = new Cliente(cpf, email, nome, telefone, cep, num, comp, null); 
                try {
                    if(atualizar == null){
                        LogicaCliente.cadastrarCliente(cl);
                    } else {
                        if (usua != null) {
                            cpf = usua.getCpfCliente();
                            email = usua.getEmail();
                            cl.setCpf(cpf);
                            cl.setEmail(email);
                            LogicaCliente.updateCliente(cl);
                        }
                    }
                    response.sendRedirect("http://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath() + "/");
                    return;
                    
                } catch (CpfExistException ex) {
                    request.setAttribute("cpfmsg", ex.getMessage());
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/Cadastro.jsp");
                    rd.forward(request, response);
                    return;
                    
                } catch (AccountExistException ex) {
                    request.setAttribute("emailmsg", ex.getMessage());
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/Cadastro.jsp");
                    rd.forward(request, response);
                    return;
                    
                } catch (CadastraClienteException | UpdateClienteException ex) {
                    request.setAttribute("msg", ex.getMessage());
                    request.setAttribute("page", "/");
                    request.setAttribute("exception", ex);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/Erro");
                    rd.forward(request, response);
                }             
                
            }
            return;
        }
        
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Cadastro.jsp");
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
