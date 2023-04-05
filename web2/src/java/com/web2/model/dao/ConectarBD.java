package com.web2.model.dao;

import com.web2.model.exceptions.BDException;
import com.web2.model.exceptions.DAOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectarBD {

    public static String status = "NAO CONECTADO";
    private static Connection con = null;
    
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String SERVER = "localhost";
    private static final String BD = "webii";
    private static final String USUARIO_BD = "root";
    private static final String SENHA_BD = "1234";
    private static final String URL = "jdbc:mysql://" + SERVER + "/" + BD + 
            "?useTimeZone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false";
    
    
    public static Connection conectar() throws BDException{
        if(con != null){
            return con;
        }
        try{
           Class.forName(DRIVER);//Carrega o JDBC
           con = DriverManager.getConnection(URL, USUARIO_BD, SENHA_BD);//Estabelece a conexão
            if(con != null){
                status = "CONECTADO!";
                return con;
            }else{
                status = "CONEXAO NAO FOI POSSIVEL";
            }    
        }catch(ClassNotFoundException e){
            throw new BDException("DRIVER DO BD NAO ENCONTRADO", e);
        }catch(SQLException e){
            throw new BDException("CONEXAO DO BD NAO FOI POSSIVEL", e);
        }
        
        return null;
    }
    
    public static void desconectar() throws BDException{
        try{
            if(con != null){
                con.close();
            }
            con = null;
        }catch (SQLException e){
            throw new BDException("DESCONECTAR DO BD NAO FOI POSSIVEL", e);
        }
    }

    public static String getStatus() {
        return status;
    }
       
    
}
