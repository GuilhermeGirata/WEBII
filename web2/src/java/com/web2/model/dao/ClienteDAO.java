
package com.web2.model.dao;

import com.web2.model.dao.interfaces.IClienteDAO;
import com.web2.model.exceptions.BDException;
import com.web2.model.exceptions.DAOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.web2.model.beans.Cliente;
import com.web2.model.exceptions.ViolacaoConstraintException;


public class ClienteDAO implements IClienteDAO{

    private static final String CLIENTE_INSERT = "INSERT INTO CLIENTE (CPF,EMAIL,NOME,TELEFONE,CEP, NUMERO,COMPLEMENTO,SENHA) VALUE (?, ?, ?, ?, ?, ?, ?, ?)";
    public final static String CLIENTE_DELETE = "DELETE FROM CLIENTE WHERE CPF=?";  
    public final static String CLIENTE_UPDATE = "UPDATE CLIENTE SET EMAIL =  ?, NOME =  ?, TELEFONE =  ?, CEP =  ?, NUMERO = ?, COMPLEMENTO = ? WHERE CPF = ?";
    private static final String CLIENTE_SELECT_ID = "SELECT CPF,EMAIL,NOME,TELEFONE,CEP,NUMERO,COMPLEMENTO,SENHA FROM CLIENTE WHERE CPF = ?";
        private static final String CLIENTE_SELECT_EMAIL = "SELECT CPF,EMAIL,NOME,TELEFONE,CEP,NUMERO,COMPLEMENTO,SENHA FROM CLIENTE WHERE EMAIL = ?";

    private static final String CLIENTE_SELECT_LOGIN = "SELECT CPF,EMAIL,NOME,TELEFONE,CEP,NUMERO,COMPLEMENTO,SENHA FROM CLIENTE WHERE EMAIL = ? AND SENHA = ?";
    
    
    @Override
    public void insert(Cliente cl) throws DAOException{
        PreparedStatement consulta;
        try {
            consulta = ConectarBD.conectar().prepareStatement(CLIENTE_INSERT);
            consulta.setString(1, cl.getCpf()); //SETA PARAMETRO COMO 1° DENTRO DO SQL
            consulta.setString(2, cl.getEmail());  //SETA PARAMETRO COMO 2° DENTRO DO SQL
            consulta.setString(3, cl.getNome());
            consulta.setString(4, cl.getTelefone());
            consulta.setString(5, cl.getCep());
            consulta.setInt(6, cl.getNumero());
            consulta.setString(7, cl.getComplemento());
            consulta.setString(8, cl.getSenha());
            
            consulta.execute();
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            throw new ViolacaoConstraintException("ERRO NO INSERT DE CLIENTE, CHAVE DUPLICADA", e);
        } catch (SQLException e) {
            throw new DAOException("ERRO NO INSERT DE CLIENTE", e);
        } catch (BDException e){
            throw new BDException("ERRO NA CONEXÃO DO BD", e); 
        }finally{
            ConectarBD.desconectar();
        } 
    }
     
    @Override
    public Cliente selectById(String id) throws DAOException{
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = ConectarBD.conectar().prepareStatement(CLIENTE_SELECT_ID);
            consulta.setString(1, id);
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
                
                return cl;
            }
        } catch (SQLException e) {
            throw new DAOException("ERRO NO SELECT DE CLIENTE", e);
        } catch (BDException e){
            throw new BDException("ERRO NA CONEXÃO DO BD", e); 
        }finally{
            ConectarBD.desconectar();
        }
        
        return null;      
    }
    
    @Override
    public void update(Cliente cl) throws DAOException{
        PreparedStatement consulta;
          
        try {
            consulta = ConectarBD.conectar().prepareStatement(CLIENTE_UPDATE);
            
            consulta.setString(1, cl.getEmail());  //SETA PARAMETRO COMO 1° DENTRO DO SQL
            consulta.setString(2, cl.getNome());
            consulta.setString(3, cl.getTelefone());
            consulta.setString(4, cl.getCep());
            consulta.setInt(5, cl.getNumero());
            consulta.setString(6, cl.getComplemento());
            consulta.setString(7, cl.getCpf());
            
            consulta.execute();
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            throw new ViolacaoConstraintException("ERRO NO UPDATE DE CLIENTE, CONSTRAINT VIOLADA", e);
        } catch (SQLException e) {
            throw new DAOException("ERRO NO UPDATE DE CLIENTE", e);
        } catch (BDException e){
            throw new BDException("ERRO NA CONEXÃO DO BD", e); 
        }finally{
            ConectarBD.desconectar();
        } 
    }
    
    @Override
    public void delete(String cpf) throws DAOException{
        PreparedStatement consulta;
        
        try {
            consulta = ConectarBD.conectar().prepareStatement(CLIENTE_DELETE);
            consulta.setString(1, cpf);
            
            consulta.execute();
        } catch(SQLException e) {
            throw new DAOException("ERRO NO DELETE DE CLIENTE", e);
        } catch (BDException e){
            throw new BDException("ERRO NA CONEXÃO DO BD", e); 
        }finally{
            ConectarBD.desconectar();
        }
    }

    @Override
    public ArrayList<Cliente> selectLogin(String login, String senha) throws DAOException{
        PreparedStatement consulta = null;
        ResultSet rs = null;
        ArrayList<Cliente> lista = new ArrayList<>();
        try {
            consulta = ConectarBD.conectar().prepareStatement(CLIENTE_SELECT_LOGIN);
            consulta.setString(1, login);
            consulta.setString(2, senha);
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
            throw new DAOException("ERRO NO SELECT DO LOGIN DO CLIENTE", e);
        } catch (BDException e){
            throw new BDException("ERRO NA CONEXÃO DO BD", e); 
        }finally{
            ConectarBD.desconectar();
        }
        
        return lista;      
    }

    @Override
    public ArrayList<Cliente> selectByEmail(String email) throws DAOException{
        PreparedStatement consulta = null;
        ResultSet rs = null;
        ArrayList<Cliente> lista = new ArrayList<>();
        try {
            consulta = ConectarBD.conectar().prepareStatement(CLIENTE_SELECT_EMAIL);
            consulta.setString(1, email);
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
            throw new DAOException("ERRO NO SELECT DE CLIENTE POR EMAIL", e);
        } catch (BDException e){
            throw new BDException("ERRO NA CONEXÃO DO BD", e); 
        }finally{
            ConectarBD.desconectar();
        }
        
        return lista;      
    }


}
