package com.web2.model.dao.interfaces;

import com.web2.model.exceptions.DAOException;
import java.time.LocalDate;
import com.web2.model.beans.Cliente;
import com.web2.model.beans.ClienteRelatorioFiel;
import com.web2.model.beans.DataPreco;
import java.util.ArrayList;
import com.web2.model.beans.Pedido;


public interface IRelatorioDAO {
    
    public ArrayList<Cliente> getAllClientes() throws DAOException;

    public ArrayList<ClienteRelatorioFiel> getClientesFieis() throws DAOException;
    
    public ArrayList<Pedido> getPedidosByDates(LocalDate dt1, LocalDate dt2) throws DAOException;

    public ArrayList<DataPreco> getReceitasByDates(LocalDate dt1, LocalDate dt2) throws DAOException;
}
