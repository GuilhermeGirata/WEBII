package com.web2.model.facade;

import com.web2.model.dao.FuncionarioDAO;
import com.web2.model.exceptions.DAOException;
import java.util.ArrayList;
import com.web2.model.beans.Funcionario;
import com.web2.model.beans.Cliente;
import com.web2.model.dao.ClienteDAO;
import com.web2.model.exceptions.AccountExistException;
import com.web2.model.exceptions.DeleteFuncionarioException;
import com.web2.model.exceptions.InsertFuncionarioException;
import com.web2.model.exceptions.SelectFuncionarioException;
import com.web2.model.exceptions.UpdateFuncionarioException;
import com.web2.model.exceptions.ViolacaoConstraintException;
import com.web2.model.exceptions.WrongLoginException;

public class LogicaFuncionario {

    public static void insertFuncionario(Funcionario fc) throws AccountExistException, InsertFuncionarioException {
        try {
            ArrayList<Cliente> cl = new ClienteDAO().selectByEmail(fc.getEmail());
            if (cl != null) {
                if (!cl.isEmpty()) {
                    throw new AccountExistException("UMA CONTA COM ESSE EMAIL JÁ EXISTE NESSE SITE");
                }
            }
            new FuncionarioDAO().insert(fc);
        } catch (ViolacaoConstraintException ex) {
            throw new AccountExistException("UMA CONTA COM ESSE EMAIL JÁ EXISTE NESSE SITE", ex);
        } catch (DAOException ex) {
            throw new InsertFuncionarioException(ex.getMessage() + " CONTATE O DESENVOLVEDOR.", ex);
        }
    }

    public static void updateFuncionario(Funcionario fc) throws UpdateFuncionarioException, AccountExistException {
        try {
            ArrayList<Cliente> cl = new ClienteDAO().selectByEmail(fc.getEmail());
            if (cl != null) {
                if (!cl.isEmpty()) {
                    throw new AccountExistException("UMA CONTA COM ESSE EMAIL JÁ EXISTE NESSE SITE");
                }
            }

            new FuncionarioDAO().update(fc);
        } catch (ViolacaoConstraintException ex) {
            throw new AccountExistException("UMA CONTA COM ESSE EMAIL JÁ EXISTE NESSE SITE", ex);
        } catch (DAOException ex) {
            throw new UpdateFuncionarioException(ex.getMessage() + " CONTATE O DESENVOLVEDOR.", ex);
        }
    }

    public static void deleteFuncionario(Integer id) throws DeleteFuncionarioException {

        try {
            if (id != 1) {
                new FuncionarioDAO().delete(id);
            }
        } catch (DAOException e) {
            throw new DeleteFuncionarioException(e.getMessage() + " CONTATE O DESENVOLVEDOR.", e);
        }
    }

    public static ArrayList<Funcionario> getAllFuncionarios() throws SelectFuncionarioException {
        try {
            return new FuncionarioDAO().selectAll();
        } catch (DAOException e) {
            throw new SelectFuncionarioException(e.getMessage() + " NÃO FOI POSSÍVEL SELECIONAR A LISTA DE FUNCIONÁRIOS.", e);
        }
    }

    public static Funcionario getFuncionario(Integer id) throws SelectFuncionarioException {
        try {
            return new FuncionarioDAO().selectById(id);
        } catch (DAOException e) {
            throw new SelectFuncionarioException(e.getMessage() + " NÃO FOI POSSÍVEL SELECIONAR O FUNCIONÁRIO.", e);
        }
    }

    public static Funcionario loginFuncionario(String login, String senha) throws DAOException, WrongLoginException {

        ArrayList<Funcionario> cls = new FuncionarioDAO().selectLogin(login, senha);
        if (cls != null) {
            if (!cls.isEmpty()) {
                return cls.get(0);
            }
        }

        throw new WrongLoginException("LOGIN OU SENHA INCORRETOS");
    }
}
