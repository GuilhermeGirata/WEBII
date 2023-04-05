/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web2.model.dao;

import com.web2.model.dao.interfaces.IRelatorioDAO;
import com.web2.model.exceptions.BDException;
import com.web2.model.exceptions.DAOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import com.web2.model.beans.Cliente;
import com.web2.model.beans.ClienteRelatorioFiel;
import com.web2.model.beans.DataPreco;
import com.web2.model.beans.Pedido;
import com.web2.model.beans.Roupa;

/**
 *
 * @author jeff
 */
public class RelatorioDAO implements IRelatorioDAO{

    private final String SELECT_ALL_CLIENTE = "SELECT CPF,EMAIL,NOME,TELEFONE,CEP,NUMERO,COMPLEMENTO,SENHA FROM CLIENTE"; 
    private final String SELECT_CLIENTE_FIES = "SELECT P.CPF_CLIENTE, C.NOME, C.EMAIL, C.TELEFONE, C.CEP, C.NUMERO, C.COMPLEMENTO, COUNT(P.ID_PEDIDO) QTDE ,SUM(P.PRECO) PRECO FROM PEDIDO P, CLIENTE C WHERE P.CPF_CLIENTE = C.CPF ORDER BY SUM(P.PRECO) ASC LIMIT 5";
    private final String SELECT_RECEITA_BY_DATES = "SELECT PRECO, PRAZO FROM vw_receitas WHERE ( 'PRAZO' BETWEEN ? AND ?) AND 'STATUS_PEDIDO' = 'FI'";
    
    @Override
    public ArrayList<Cliente> getAllClientes() throws DAOException {
        PreparedStatement consulta = null;
        ResultSet rs = null;
        ArrayList<Cliente> lista = new ArrayList<>();
        try {
            consulta = ConectarBD.conectar().prepareStatement(SELECT_ALL_CLIENTE);
            rs = consulta.executeQuery();

            while(rs.next()){
                Cliente cl = new Cliente();
                cl.setCpf(rs.getString("CPF"));
                cl.setEmail(rs.getString("EMAIL"));
                cl.setNome(rs.getString("NOME"));
                cl.setNumero(rs.getInt("NUMERO"));
                cl.setCep(rs.getString("CEP"));
                cl.setComplemento(rs.getString("COMPLEMENTO"));
                cl.setTelefone(rs.getString("TELEFONE"));                              
                cl.setSenha(rs.getString("SENHA"));
                
                
                lista.add(cl);
            }
        } catch (SQLException e) {
            throw new DAOException("ERRO NO SELECT DE TODOS OS CLIENTES PARA GERAR RELATÓRIO.", e);
        } catch (BDException e){
            throw new BDException("ERRO NA CONEXÃO DO BD", e); 
        }finally{
            ConectarBD.desconectar();
        }
        
        return lista;  
    }

    @Override
    public ArrayList<Pedido> getPedidosByDates(LocalDate dt1, LocalDate dt2) throws DAOException {
        return new PedidoDAO().selectByDates(dt1, dt2);
    }

    @Override
    public ArrayList<ClienteRelatorioFiel> getClientesFieis() throws DAOException {
        PreparedStatement consulta = null;
        ResultSet rs = null;
        ArrayList<ClienteRelatorioFiel> lista = new ArrayList<>();
        try {
            consulta = ConectarBD.conectar().prepareStatement(SELECT_CLIENTE_FIES);
            rs = consulta.executeQuery();

            while(rs.next()){
                ClienteRelatorioFiel cl = new ClienteRelatorioFiel();
                cl.setCpf(rs.getString("CPF_CLIENTE"));
                cl.setNome(rs.getString("NOME"));
                cl.setEmail(rs.getString("EMAIL"));
                cl.setTelefone(rs.getString("TELEFONE")); 
                cl.setCep(rs.getString("CEP"));
                cl.setNum(rs.getInt("NUMERO"));
                cl.setComplemento(rs.getString("COMPLEMENTO"));
                cl.setQtdePedido(rs.getInt("QTDE"));
                cl.setReceita(rs.getDouble("PRECO"));
                
                lista.add(cl);
            }
        }catch (SQLException e) {
            throw new DAOException("ERRO NO SELECT DE CLIENTES FIÉIS PARA GERAR RELATÓRIO.", e);
        }catch (BDException e){
            throw new BDException("ERRO NA CONEXÃO DO BD.", e); 
        }finally{
            ConectarBD.desconectar();
        }
        
        return lista;  
    }

    @Override
    public ArrayList<DataPreco> getReceitasByDates(LocalDate dt1, LocalDate dt2) throws DAOException {
        PreparedStatement consulta;
        ResultSet rs = null;
        ArrayList<DataPreco> lista = new ArrayList<>();
        
        try {
            consulta = ConectarBD.conectar().prepareStatement(SELECT_RECEITA_BY_DATES);
            consulta.setString(1, dt1.toString());
            consulta.setString(2, dt2.toString());
            rs = consulta.executeQuery();

            while(rs.next()){
                DataPreco pd = new DataPreco();
                pd.setPreco(rs.getDouble("PRECO"));
                pd.setDataPrazoFromSqlDate(rs.getDate("PRAZO"));
                    
                lista.add(pd);
            }
        } catch (SQLException e){
            throw new DAOException("ERRO NO SELECT DA RECEITA AGRUPADA POR DIA PARA GERAR RELATÓRIO.", e);
        } catch (BDException e){
            throw new BDException("ERRO NA CONEXÃO DO BD.", e); 
        }finally{
            ConectarBD.desconectar();
        }
        
        return lista;          
    }
    
    
}
