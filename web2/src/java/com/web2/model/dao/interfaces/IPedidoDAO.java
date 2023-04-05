
package com.web2.model.dao.interfaces;

import com.web2.model.exceptions.DAOException;
import java.time.LocalDate;
import java.util.ArrayList;
import com.web2.model.beans.Pedido;
import java.time.LocalDateTime;


public interface IPedidoDAO extends IDAO<Pedido, Integer>{
    
    public ArrayList<Pedido> selectAllOrderByDate() throws DAOException;
    
    public ArrayList<Pedido> selectByDates(LocalDate dt1, LocalDate dt2) throws DAOException;
    
    public ArrayList<Pedido> selectByUser(String cpf) throws DAOException;

    public void updateStatusPedido(Integer idPedido, String status) throws DAOException;
    
    public void updatePagarPedido(Integer idPedido, LocalDateTime date) throws DAOException;
    
    public Pedido selectByIdCpf(Integer id, String cpf) throws DAOException;
    
    public ArrayList<Pedido> selectByUserId(String cpf, Integer idpedido) throws DAOException;

    public ArrayList<Pedido> selectByStatusAndCliente(String st, String idCliente) throws DAOException;
    
    public ArrayList<Pedido> selectAllByStatus(String st) throws DAOException;
   
}
