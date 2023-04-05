/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web2.model.facade;

import com.web2.model.beans.Endereco;
import com.web2.model.dao.PedidoDAO;
import com.web2.model.exceptions.DAOException;
import com.web2.model.exceptions.StatusManagementException;
import java.time.LocalDate;
import java.util.ArrayList;
import com.web2.model.beans.Pedido;
import com.web2.model.exceptions.ChangeStatusErrorException;
import com.web2.model.exceptions.SelectPedidosFuncionarioException;
import com.web2.model.exceptions.ViaCepException;
import com.web2.model.util.ViaCep;

/**
 *
 * @author jeffe
 */
public class LogicaFuncionarioPedido {
    
    public static Pedido selectPedidoById(Integer id) throws SelectPedidosFuncionarioException{
        Pedido pd;
        try {
            pd = new PedidoDAO().selectById(id);
        } catch (DAOException ex) {
            throw new SelectPedidosFuncionarioException(ex.getMessage() + " CONTATE O DESENVOLVEDOR", ex);
        }
        
        try {
            pd.setEndereco(ViaCep.findCep(pd.getCepCliente()));
        } catch (ViaCepException ex) {
            pd.setEndereco(new Endereco("ERRO EM CONECTAR COM A API"));
        }
        
        return pd;
    }
    
    public static void changeStatusPedido(Integer id) throws StatusManagementException, ChangeStatusErrorException{
        PedidoDAO dao = new PedidoDAO();
         try{
            Pedido pd = dao.selectById(id);
            String st = "";
            switch (pd.getStatus()) {
                case "AB":
                    st = "RC";
                    break;
                case "RC":
                    st = "AP";
                    break;
                case "PG":
                    st = "FI";
                    break;
                default:
                    throw new StatusManagementException("ESSE ESTADO DO PEDIDO NÃO PODE SER MUDADO");
            }

            dao.updateStatusPedido(pd.getId(), st);
        } catch (DAOException e) {
            throw new ChangeStatusErrorException(e.getMessage() + " CONTATE O DESENVOLVEDOR", e);
        }
    }

    public static void rejectPedido(Integer id) throws StatusManagementException, ChangeStatusErrorException{
        PedidoDAO dao = new PedidoDAO();
        try {
            Pedido pd = dao.selectById(id);
            if (pd.getStatus().equals("AB") && pd.getId() != null && pd != null) {
                dao.updateStatusPedido(pd.getId(), "RJ");
                return;
            }
            throw new StatusManagementException("ESSE ESTADO DO PEDIDO NÃO PODE SER MUDADO");
        } catch (DAOException ex) {
            throw new ChangeStatusErrorException(ex.getMessage() + " CONTATE O DESENVOLVEDOR", ex);
        }
    }

    public static ArrayList<Pedido> selectAllPedidosAberto() throws SelectPedidosFuncionarioException{
        try {
            return new PedidoDAO().selectAllByStatus("AB");
        } catch (DAOException e) {
            throw new SelectPedidosFuncionarioException(e.getMessage() + " CONTATE O DESENVOLVEDOR", e);
        }
        
    }
    
    public static ArrayList<Pedido> selectAllPedidos() throws SelectPedidosFuncionarioException{
           try {
            return new PedidoDAO().selectAllOrderByDate();
        } catch (DAOException e) {
            throw new SelectPedidosFuncionarioException(e.getMessage() + " CONTATE O DESENVOLVEDOR", e);
        } 
    }
    
    public static ArrayList<Pedido> selectAllPedidosByDate(LocalDate dt1, LocalDate dt2) throws SelectPedidosFuncionarioException  {
        try {
            return new PedidoDAO().selectByDates(dt1, dt2);
        } catch (DAOException e) {
            throw new SelectPedidosFuncionarioException(e.getMessage() + " CONTATE O DESENVOLVEDOR", e);
        }
    }
    
    public static ArrayList<Pedido> selectAllPedidosHoje() throws SelectPedidosFuncionarioException{
        try {
            LocalDate dt = LocalDate.now();
            System.out.println(dt);
            return new PedidoDAO().selectByDates(dt, dt);
        } catch (DAOException e) {
            throw new SelectPedidosFuncionarioException(e.getMessage() + " CONTATE O DESENVOLVEDOR", e);
        }
    }
 
}
