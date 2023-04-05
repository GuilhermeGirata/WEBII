/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.web2.model.dao.interfaces;

import com.web2.model.exceptions.DAOException;
import java.util.ArrayList;
import com.web2.model.beans.Cliente;

/**
 *
 * @author jeffe
 */
public interface IClienteDAO extends IDAO<Cliente, String>{
    public ArrayList<Cliente> selectLogin(String login, String senha) throws DAOException;
    
    public ArrayList<Cliente> selectByEmail(String email) throws DAOException;
}
