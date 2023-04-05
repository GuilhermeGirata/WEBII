/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.web2.model.dao;

import com.web2.model.dao.interfaces.IRoupaDAO;
import com.web2.model.exceptions.BDException;
import com.web2.model.exceptions.DAOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.web2.model.beans.Roupa;

/**
 *
 * @author jeffe
 */
public class RoupaDAO implements IRoupaDAO{

    private static final String ROUPA_INSERT = "INSERT INTO ROUPA (NOME, PRECO, TEMPO_LIMITE) VALUES (?, ?, ?)";
    private static final String ROUPA_UPDATE = "UPDATE ROUPA SET NOME = ?, PRECO = ?, TEMPO_LIMITE = ? WHERE ID_PECA = ?";
    private static final String ROUPA_DELETE = "DELETE FROM ROUPA WHERE ID_PECA = ?";
    private static final String ROUPA_SELECT_ID = "SELECT ID_PECA,NOME,PRECO,TEMPO_LIMITE FROM ROUPA WHERE ID_PECA = ?";
    private static final String ROUPA_SELECT_ALL = "SELECT ID_PECA, NOME, PRECO, TEMPO_LIMITE FROM ROUPA ORDER BY NOME";
    
    private static final String ROUPA_SELECT_PRECO = "SELECT ID_PECA, NOME, PRECO, TEMPO_LIMITE FROM ROUPA R WHERE R.ID_PECA IN (";
    
    @Override
    public void insert(Roupa rp) throws DAOException {
        PreparedStatement consulta;
        
        try {
            consulta = ConectarBD.conectar().prepareStatement(ROUPA_INSERT);
            consulta.setString(1, rp.getNome()); //SETA PARAMETRO COMO 1° DENTRO DO SQL
            consulta.setDouble(2, rp.getPreco());  //SETA PARAMETRO COMO 2° DENTRO DO SQL
            consulta.setInt(3, rp.getLimiteDias());
            
            consulta.execute();
        }catch (SQLException e) {
            throw new DAOException("ERRO NO INSERT DE ROUPA", e);
        } catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        }finally{
            ConectarBD.desconectar();
        }

    }

    @Override
    public void update(Roupa rp) throws DAOException {
        PreparedStatement consulta;
        
        try {
            consulta = ConectarBD.conectar().prepareStatement(ROUPA_UPDATE);
            
            consulta.setString(1, rp.getNome());
            consulta.setDouble(2, rp.getPreco());
            consulta.setInt(3, rp.getLimiteDias());
            consulta.setInt(4, rp.getId());
            
            consulta.execute();
        } catch (SQLException e) {
            throw new DAOException("ERRO NO UPDATE DE ROUPA", e);
        } catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        }finally{
            ConectarBD.desconectar();
        }
    }

    @Override
    public void delete(Integer id) throws DAOException {
        PreparedStatement consulta;
        try {
            consulta = ConectarBD.conectar().prepareStatement(ROUPA_DELETE);
            consulta.setInt(1, id);
            
            consulta.execute();
        } catch (SQLException e) {
            throw new DAOException("ERRO NO DELETE DE ROUPA", e);
        } catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        }finally{
            ConectarBD.desconectar();
        } 
    }

    @Override
    public Roupa selectById(Integer id) throws DAOException{
        PreparedStatement consulta = null;
        ResultSet rs = null;
         
        try{
            consulta = ConectarBD.conectar().prepareStatement(ROUPA_SELECT_ID);
            consulta.setInt(1, id);
            rs = consulta.executeQuery();

            while(rs.next()){
                Roupa rp = new Roupa();
                rp.setId(rs.getInt("ID_PECA"));
                rp.setNome(rs.getString("NOME"));
                rp.setPreco(rs.getDouble("PRECO"));
                rp.setLimiteDias(rs.getInt("TEMPO_LIMITE"));
               
                return rp;
            }
        }catch (SQLException e) {
            throw new DAOException("ERRO NO SELECT DE ROUPA", e);
        }catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        }finally{
            ConectarBD.desconectar();
        }
        
        return null;   
    }
    
    @Override
    public ArrayList<Roupa> selectAll() throws DAOException{
        PreparedStatement consulta = null;
        ResultSet rs = null;
        ArrayList<Roupa> lista = new ArrayList<>();
        
        try{
            consulta = ConectarBD.conectar().prepareStatement(ROUPA_SELECT_ALL);
            rs = consulta.executeQuery();

            while(rs.next()){
                Roupa rp = new Roupa();
                rp.setId(rs.getInt("ID_PECA"));
                rp.setNome(rs.getString("NOME"));
                rp.setPreco(rs.getDouble("PRECO"));
                rp.setLimiteDias(rs.getInt("TEMPO_LIMITE"));
                
                
                lista.add(rp);
            }
        }catch (SQLException e) {
            throw new DAOException("ERRO NO SELECT DE TODAS AS ROUPAS", e);
        }catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        }finally{
            ConectarBD.desconectar();
        }
        
        return lista;   
    }

    
    //https://stackoverflow.com/questions/24528337/passing-array-parameter-in-prepare-statement-getting-java-sql-sqlfeaturenotsu
    
    public ArrayList<Roupa> selectTempoPrecoFromArray(ArrayList<Integer> ids) throws DAOException{
        PreparedStatement consulta = null;
        ResultSet rs = null;
        String query = "";
        
        
        for(Integer i : ids){
            query += "," + i.toString();
        }
        query = query.replaceFirst(",", "");
        query += ")";
        query = ROUPA_SELECT_PRECO + query;
        
        try{
            consulta = ConectarBD.conectar().prepareStatement(query);
            ArrayList<Roupa> lista = new ArrayList<>();
            rs = consulta.executeQuery();

            while(rs.next()){
                Roupa rp = new Roupa();
                rp.setId(rs.getInt("ID_PECA"));
                rp.setNome(rs.getString("NOME"));
                rp.setPreco(rs.getDouble("PRECO"));
                rp.setLimiteDias(rs.getInt("TEMPO_LIMITE"));
                
                
                lista.add(rp);
            }
            return lista;
        }catch (SQLException e) {
            throw new DAOException("ERRO NO SELECT DE PRECO DAS ROUPAS", e);
        }catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        }finally{
            ConectarBD.desconectar();
        }   
    }
    
    
}
