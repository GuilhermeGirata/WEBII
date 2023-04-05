/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.web2.model.dao;

import com.web2.model.dao.interfaces.IFuncionarioDAO;
import com.web2.model.exceptions.BDException;
import com.web2.model.beans.Funcionario;
import com.web2.model.exceptions.DAOException;
import com.web2.model.exceptions.ViolacaoConstraintException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author jeffe
 */
public class FuncionarioDAO implements IFuncionarioDAO {

    private static final String FUNCIONARIO_INSERT = "INSERT INTO FUNCIONARIO (EMAIL, NOME, SENHA) VALUES (?, ?, ?)";
    private static final String FUNCIONARIO_UPDATE = "UPDATE FUNCIONARIO SET EMAIL = ?, NOME = ?, SENHA = ? WHERE ID_FUNCIONARIO = ?";
    private static final String FUNCIONARIO_DELETE = "DELETE FROM FUNCIONARIO WHERE ID_FUNCIONARIO = ?";
    private static final String FUNCIONARIO_SELECT_ID = "SELECT ID_FUNCIONARIO, EMAIL,NOME,SENHA FROM FUNCIONARIO WHERE ID_FUNCIONARIO = ?";
    private static final String FUNCIONARIO_SELECT_EMAIL = "SELECT ID_FUNCIONARIO, EMAIL,NOME,SENHA FROM FUNCIONARIO WHERE EMAIL = ?";
    private static final String FUNCIONARIO_SELECT_ALL = "SELECT ID_FUNCIONARIO, EMAIL,NOME,SENHA FROM FUNCIONARIO ORDER BY NOME";
    private static final String FUNCIONARIO_SELECT_LOGIN = "SELECT ID_FUNCIONARIO, EMAIL,NOME,SENHA FROM FUNCIONARIO WHERE EMAIL = ? AND SENHA = ?";

    
    @Override
    public void insert(Funcionario fc) throws DAOException {
        PreparedStatement consulta;

        try {
            consulta = ConectarBD.conectar().prepareStatement(FUNCIONARIO_INSERT);
            consulta.setString(1, fc.getEmail()); //SETA PARAMETRO COMO 1° DENTRO DO SQL
            consulta.setString(2, fc.getNome());  //SETA PARAMETRO COMO 2° DENTRO DO SQL
            consulta.setString(3, fc.getSenha());

            consulta.execute();
        }catch (java.sql.SQLIntegrityConstraintViolationException e) {
            throw new ViolacaoConstraintException("ERRO NO INSERT DE FUNCIONARIO, EMAIL DUPLICADO", e);
        } catch (SQLException e) {
            throw new DAOException("ERRO NO INSERT DE FUNCIONARIO", e);
        } catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        } finally {
            ConectarBD.desconectar();
        }
    }

    @Override
    public void update(Funcionario fc) throws DAOException {
        PreparedStatement consulta;

        try {
            consulta = ConectarBD.conectar().prepareStatement(FUNCIONARIO_UPDATE);

            consulta.setString(1, fc.getEmail());
            consulta.setString(2, fc.getNome());
            consulta.setString(3, fc.getSenha());
            consulta.setInt(4, fc.getId());

            consulta.execute();
        } catch (SQLException e) {
            throw new DAOException("ERRO NO UPDATE DE FUNCIONARIO", e);
        } catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        }finally {
            ConectarBD.desconectar();
        }
    }

    @Override
    public void delete(Integer id) throws DAOException {
        PreparedStatement consulta;
        try {
            consulta = ConectarBD.conectar().prepareStatement(FUNCIONARIO_DELETE);
            consulta.setInt(1, id);
            
            consulta.execute();
        } catch (SQLException e) {
            throw new DAOException("ERRO NO DELETE DE FUNCIONARIO", e);
        } catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        }finally{
            ConectarBD.desconectar();
        }
    }

    @Override
    public Funcionario selectById(Integer id) throws DAOException {
            PreparedStatement consulta = null;
        ResultSet rs = null;
         
        try {
            consulta = ConectarBD.conectar().prepareStatement(FUNCIONARIO_SELECT_ID);
            consulta.setInt(1, id);
            rs = consulta.executeQuery();

            while(rs.next()){
                Funcionario fc = new Funcionario();
                fc.setId(rs.getInt("ID_FUNCIONARIO"));
                fc.setEmail(rs.getString("EMAIL"));
                fc.setNome(rs.getString("NOME"));
                fc.setSenha(rs.getString("SENHA"));
                
                return fc;
            }
        } catch (SQLException e) {
            throw new DAOException("ERRO NO SELECT DE FUNCIONARIO POR ID", e);
        } catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        }finally{
            ConectarBD.desconectar();
        }
        
        return null; 
    }

    @Override
    public ArrayList<Funcionario> selectAll() throws DAOException {
            PreparedStatement consulta = null;
        ResultSet rs = null;
        ArrayList<Funcionario> lista = new ArrayList<>();
         
        try {
            consulta = ConectarBD.conectar().prepareStatement(FUNCIONARIO_SELECT_ALL);
            rs = consulta.executeQuery();

            while(rs.next()){
                Funcionario fc = new Funcionario();
                fc.setId(rs.getInt("ID_FUNCIONARIO"));
                fc.setEmail(rs.getString("EMAIL"));
                fc.setNome(rs.getString("NOME"));
                fc.setSenha(rs.getString("SENHA"));
                
                lista.add(fc);
            }
        } catch (SQLException e) {
            throw new DAOException("ERRO NO SELECT DE ROUPA", e);
        } catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        }finally{
            ConectarBD.desconectar();
        }
        
        return lista; 
    }

    @Override
    public ArrayList<Funcionario> selectByEmail(String email) throws DAOException {
            PreparedStatement consulta = null;
        ResultSet rs = null;
        ArrayList<Funcionario> lista = new ArrayList<>();
         
        try {
            consulta = ConectarBD.conectar().prepareStatement(FUNCIONARIO_SELECT_EMAIL);
            consulta.setString(1, email);
            rs = consulta.executeQuery();

            while(rs.next()){
                Funcionario fc = new Funcionario();
                fc.setId(rs.getInt("ID_FUNCIONARIO"));
                fc.setEmail(rs.getString("EMAIL"));
                fc.setNome(rs.getString("NOME"));
                fc.setSenha(rs.getString("SENHA"));
                
                lista.add(fc);
            }
        } catch (SQLException e) {
            throw new DAOException("ERRO NO SELECT DE FUNCIONARIO POR EMAIL", e);
        } catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        }finally{
            ConectarBD.desconectar();
        }
        
        return lista; 
    }

    @Override
    public ArrayList<Funcionario> selectLogin(String login, String senha) throws DAOException{
        PreparedStatement consulta = null;
        ResultSet rs = null;
        ArrayList<Funcionario> lista = new ArrayList<>();
         
        try {
            consulta = ConectarBD.conectar().prepareStatement(FUNCIONARIO_SELECT_LOGIN);
            consulta.setString(1, login);
            consulta.setString(2, senha);
            rs = consulta.executeQuery();

            while(rs.next()){
                Funcionario fc = new Funcionario();
                fc.setId(rs.getInt("ID_FUNCIONARIO"));
                fc.setEmail(rs.getString("EMAIL"));
                fc.setNome(rs.getString("NOME"));
                fc.setSenha(rs.getString("SENHA"));
                
                lista.add(fc);
            }
        } catch (SQLException e) {
            throw new DAOException("ERRO NO SELECT DO LOGIN DE FUNCIONARIO", e);
        } catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        }finally{
            ConectarBD.desconectar();
        }
        
        return lista; 
    }
}
