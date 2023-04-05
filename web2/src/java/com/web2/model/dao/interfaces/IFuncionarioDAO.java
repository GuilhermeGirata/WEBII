/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.web2.model.dao.interfaces;

import com.web2.model.exceptions.DAOException;
import java.util.ArrayList;
import com.web2.model.beans.Funcionario;

/**
 *
 * @author jeffe
 */
public interface IFuncionarioDAO extends IDAO<Funcionario, Integer>{
    
    public ArrayList<Funcionario> selectAll() throws DAOException;
    
    public ArrayList<Funcionario> selectByEmail(String email) throws DAOException;
    
    public ArrayList<Funcionario> selectLogin(String login, String senha) throws DAOException;
}
