
package com.web2.model.facade;

import com.web2.model.exceptions.DAOException;
import com.web2.model.exceptions.WrongLoginException;
import com.web2.model.beans.Cliente;
import com.web2.model.beans.Funcionario;
import com.web2.model.beans.LoginBean;
import com.web2.model.exceptions.EncryptionException;
import com.web2.model.exceptions.LogicaLoginException;

public class LogicaLogin {

    /**
     * Retorna a LoginBean para ser Salvo no Session
     * que vai ser usada depois para fazer as operações
    **/
    public static LoginBean login(String login, String senha) throws WrongLoginException, LogicaLoginException{  
        LoginBean lb = new LoginBean();
        
        try {
            Cliente cl = LogicaCliente.loginCliente(login, senha);
            lb.setCpfCliente(cl.getCpf());
            lb.setEmail(cl.getEmail());
            lb.setNome(cl.getNome());
            lb.setPapel("cl");
            return lb;
        } catch (WrongLoginException ex) {
            //Não achou email na tb cliente vai procurar na tb funcionario no prox try
            System.err.println("WrongLoginException, na Classe LogicaLogin pelo Cliente");
        } catch (EncryptionException | DAOException ex) {
            throw new LogicaLoginException(ex.getMessage() + " TENTE NOVAMENTE EM OUTRO MOMENTO :)", ex);
        }
        
 
        try {
            Funcionario fc = LogicaFuncionario.loginFuncionario(login, senha);
            lb.setEmail(fc.getEmail());
            lb.setNome(fc.getNome());
            lb.setIdFuncionario(fc.getId());
            lb.setPapel("fc");
            return lb;
        } catch (WrongLoginException ex) {
            throw new WrongLoginException("CONTA INEXISTENTE");
        } catch (DAOException ex) {
            throw new LogicaLoginException(ex.getMessage() + " TENTE NOVAMENTE EM OUTRO MOMENTO :)", ex);
        }
               
    }
    
}
