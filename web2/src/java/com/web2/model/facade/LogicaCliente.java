package com.web2.model.facade;

import com.web2.model.dao.ClienteDAO;
import com.web2.model.dao.FuncionarioDAO;
import com.web2.model.exceptions.AccountExistException;
import com.web2.model.exceptions.DAOException;
import com.web2.model.exceptions.EncryptionException;
import com.web2.model.exceptions.ViolacaoConstraintException;
import com.web2.model.exceptions.WrongLoginException;
import java.util.ArrayList;
import com.web2.model.beans.Funcionario;
import com.web2.model.beans.Cliente;
import com.web2.model.exceptions.BDException;
import com.web2.model.exceptions.CadastraClienteException;
import com.web2.model.exceptions.CpfExistException;
import com.web2.model.exceptions.EmailNotSendException;
import com.web2.model.exceptions.UpdateClienteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.web2.model.util.Encryption;
import com.web2.model.util.EnviarEmail;
import com.web2.model.util.GerarSenhas;

public class LogicaCliente {

    public static void cadastrarCliente(Cliente cl) throws AccountExistException, CpfExistException, CadastraClienteException {
        try {

            String senha = GerarSenhas.generateRandomPassword();
            cl.setSenha(Encryption.cripto(senha));

            ArrayList<Funcionario> fc = new FuncionarioDAO().selectByEmail(cl.getEmail());
            if (fc != null) {
                if (!fc.isEmpty()) {
                    throw new AccountExistException("UMA CONTA COM ESSE EMAIL JÁ EXISTE NESSE SITE");
                }
            }

            ArrayList<Cliente> clist = new ClienteDAO().selectByEmail(cl.getEmail());
            if (clist != null) {
                if (!clist.isEmpty()) {
                    throw new AccountExistException("UMA CONTA COM ESSE EMAIL JÁ EXISTE NESSE SITE");
                }
            }

            try {
                new ClienteDAO().insert(cl);
            } catch (ViolacaoConstraintException ex) {
                throw new CpfExistException("UMA CONTA COM ESSE CPF JÁ EXISTE NESSE SITE", ex);
            }

            EnviarEmail enviar = new EnviarEmail();

            enviar.setNomeDestinatario(cl.getNome());
            enviar.setEmailDestinatario(cl.getEmail());
            enviar.setCpfDestinatario(cl.getCpf());
            enviar.setTelefoneDestinatario(cl.getTelefone());
            enviar.setCepDestinatario(cl.getCep());
            enviar.setNumeroDestinatario(cl.getNumero().toString());
            enviar.setComplementoDestinatario(cl.getComplemento());

            enviar.setSenhaDestinatario(senha);

            enviar.enviarGmail();

        } catch (BDException ex) {
            throw new CadastraClienteException(ex.getMessage() + " TENTE NOVAMENTE EM OUTRO MOMENTO :)", ex);
        } catch (DAOException | EncryptionException | EmailNotSendException ex) {
            throw new CadastraClienteException(ex.getMessage() + " TENTE NOVAMENTE EM OUTRO MOMENTO :)", ex);
        }

    }

    /**
     * Retorna a primary key do cliente para ser Salvo no Session que vai ser
     * usada depois para fazer as operações
     *
     * @return 
    *
     */
    public static Cliente loginCliente(String login, String senha) throws DAOException, WrongLoginException, EncryptionException {
        senha = Encryption.cripto(senha);
        System.out.println(senha);
        ArrayList<Cliente> cls = new ClienteDAO().selectLogin(login, senha);
        if (cls != null) {
            if (!cls.isEmpty()) {
                return cls.get(0);
            }
        }

        throw new WrongLoginException("LOGIN OU SENHA INCORRETOS");
    }

    public static void updateCliente(Cliente cl) throws AccountExistException, CpfExistException, UpdateClienteException {
        try {
            cl.setSenha(Encryption.cripto(cl.getSenha()));

            ArrayList<com.web2.model.beans.Funcionario> fc = new FuncionarioDAO().selectByEmail(cl.getEmail());
            if (fc != null) {
                if (!fc.isEmpty()) {
                    throw new AccountExistException("UMA CONTA COM ESSE EMAIL JÁ EXISTE NESSE SITE");
                }
            }

            try {
                new ClienteDAO().update(cl);
            } catch (ViolacaoConstraintException ex) {
                throw new CpfExistException("UMA CONTA COM ESSE CPF JÁ EXISTE NESSE SITE", ex);
            }

        } catch (EncryptionException | DAOException ex) {
            throw new UpdateClienteException(ex.getMessage() + " TENTE NOVAMENTE EM OUTRO MOMENTO :)", ex);
        }
    }

}
