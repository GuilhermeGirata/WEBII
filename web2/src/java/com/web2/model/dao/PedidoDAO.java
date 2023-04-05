
package com.web2.model.dao;


import com.web2.model.dao.interfaces.IPedidoDAO;
import com.web2.model.exceptions.BDException;
import com.web2.model.exceptions.DAOException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import com.web2.model.beans.Pedido;
import com.web2.model.beans.Roupa;
import com.web2.model.exceptions.ViolacaoConstraintException;
import java.time.LocalDateTime;
import java.time.ZoneId;


public class PedidoDAO implements IPedidoDAO{

    private static final String PEDIDO_INSERT = "INSERT INTO pedido (CPF_CLIENTE,PRECO,STATUS_PEDIDO,PRAZO,DATA_ABERTURA)VALUES(?,?,?,?,?)";
    private static final String PEDIDO_UPDATE = "UPDATE PEDIDO SET CPF_CLIENTE = ?, PRECO = ?, STATUS_PEDIDO = ?, PRAZO = ?, DATA_ABERTURA = ? WHERE ID_PEDIDO = ?";
    private static final String PEDIDO_DELETE = "DELETE FROM PEDIDO WHERE ID_PEDIDO = ?";
    
    private static final String PEDIDO_SELECT_ID = "SELECT P.ID_PEDIDO, P.CPF_CLIENTE, P.PRECO, P.STATUS_PEDIDO, P.PRAZO, P.DATA_ABERTURA, C.NOME, P.DATA_PAGAMENTO, C.CEP, C.NUMERO, C.COMPLEMENTO FROM PEDIDO P, CLIENTE C WHERE P.CPF_CLIENTE = C.CPF AND P.ID_PEDIDO = ?";
    
    private static final String PEDIDO_SELECT_ID_CPF = "SELECT P.ID_PEDIDO, P.CPF_CLIENTE, P.PRECO, P.STATUS_PEDIDO, P.PRAZO, P.DATA_ABERTURA, C.NOME, P.DATA_PAGAMENTO, C.CEP, C.NUMERO, C.COMPLEMENTO FROM PEDIDO P, CLIENTE C WHERE P.CPF_CLIENTE = C.CPF AND P.ID_PEDIDO = ? AND P.CPF_CLIENTE = ?";
    
    
    private static final String PEDIDO_SELECT_ALL = "SELECT ID_PEDIDO, CPF_CLIENTE, PRAZO, STATUS_PEDIDO, DATA_ABERTURA, NOME1, QTDE1, NOME2, QTDE2, NOME3, QTDE3, NOME4, QTDE4 FROM GET_PEDIDOS";
    private static final String PEDIDO_SELECT_ALL_BY_USER = PEDIDO_SELECT_ALL + " WHERE CPF_CLIENTE = ? ORDER BY PRAZO ASC";
     private static final String PEDIDO_SELECT_ALL_BY_USER_ID = PEDIDO_SELECT_ALL + " WHERE CPF_CLIENTE = ? AND ID_PEDIDO = ?";
    private static final String PEDIDO_SELECT_ALL_BY_DATE = PEDIDO_SELECT_ALL + " ORDER BY DATA_ABERTURA, PRAZO ASC"; 
    private static final String PEDIDO_SELECT_BETWEEN_DATES = PEDIDO_SELECT_ALL + " WHERE DATE(PRAZO) BETWEEN ? AND ? OR DATE(DATA_ABERTURA) BETWEEN ? AND ? ORDER BY DATA_ABERTURA, PRAZO ASC";

    private static final String PEDIDO_UPDATE_STATUS = "UPDATE PEDIDO SET STATUS_PEDIDO = ? WHERE ID_PEDIDO = ?";    
    private static final String PEDIDO_UPDATE_PAGAR = "UPDATE PEDIDO SET STATUS_PEDIDO = 'PG', DATA_PAGAMENTO = ? WHERE ID_PEDIDO = ?"; 
    
    private static final String PEDIDO_ROUPAS_INSERT = "INSERT INTO ROUPA_PEDIDO(ID_ROUPA, ID_PEDIDO, QTDE) VALUES (?, ?, ?)";
    private static final String PEDIDO_ROUPAS_SELECT_ALL = "SELECT ID, NOME, QTDE FROM GET_ROUPAS_PEDIDO WHERE ID = ?";
    
    private static final String PEDIDO_SELECT_ALL_BY_STATUS = PEDIDO_SELECT_ALL + " WHERE STATUS_PEDIDO = ? ";
    private static final String PEDIDO_SELECT_ALL_BY_STATUS_AND_USER = PEDIDO_SELECT_ALL_BY_STATUS + " AND CPF_CLIENTE = ?";
    
    @Override
    public void insert(Pedido pd) throws DAOException{
        PreparedStatement consulta;
        PreparedStatement insertRoupa;
        try {
            consulta = ConectarBD.conectar().prepareStatement(PEDIDO_INSERT, Statement.RETURN_GENERATED_KEYS);
            consulta.setString(1, pd.getCpf()); //SETA PARAMETRO COMO 1° DENTRO DO SQL
            consulta.setDouble(2, pd.getPreco());
            consulta.setString(3, String.valueOf(pd.getStatus()));
            consulta.setDate(4, new Date(pd.getDataPrazoInMili()));
            consulta.setDate(5, new Date(pd.getDataAberturaInMili()));
            
            consulta.execute(); 
            
            ResultSet rs = consulta.getGeneratedKeys();
            while (rs.next()) {
                insertRoupa = ConectarBD.conectar().prepareStatement(PEDIDO_ROUPAS_INSERT);
                for(Roupa roupa: pd.getRoupas()){
                    insertRoupa.setInt(1, roupa.getId());
                    insertRoupa.setInt(2, rs.getInt(1));
                    insertRoupa.setInt(3, roupa.getQtde());
                    insertRoupa.addBatch();
                }
                
                insertRoupa.executeBatch();               
            }           
        } catch (SQLException e) {
            throw new DAOException("ERRO NO INSERT DO PEDIDO", e);     
        } catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        }
        finally{
            ConectarBD.desconectar();
        } 
    }
    
    @Override
    public Pedido selectById(Integer id) throws DAOException{
        PreparedStatement consulta;
        PreparedStatement consultaRoupas;
        ResultSet rs = null;
         
        try {
            consulta = ConectarBD.conectar().prepareStatement(PEDIDO_SELECT_ID);
            consulta.setInt(1, id);
            rs = consulta.executeQuery();

            while(rs.next()){
                Pedido pd = new Pedido();
                pd.setId(rs.getInt("ID_PEDIDO"));
                pd.setCpf(rs.getString("CPF_CLIENTE"));
                pd.setPreco(rs.getDouble("PRECO"));
                pd.setStatus(rs.getString("STATUS_PEDIDO"));
                pd.setDataPrazoFromSqlDate(rs.getDate("PRAZO"));
                pd.setDataAberturaFromSqlDate(rs.getDate("DATA_ABERTURA"));
                pd.setNomeCliente(rs.getString("NOME"));
                pd.setCepCliente(rs.getString("CEP"));
                pd.setNumCliente(rs.getInt("NUMERO"));
                pd.setCompCliente(rs.getString("COMPLEMENTO"));

                consultaRoupas = ConectarBD.conectar().prepareStatement(PEDIDO_ROUPAS_SELECT_ALL);
                consultaRoupas.setInt(1, rs.getInt("ID_PEDIDO"));
                ResultSet res2 = consultaRoupas.executeQuery();
                while(res2.next()){
                    Roupa rp = new Roupa();
                    rp.setId(res2.getInt("ID"));
                    rp.setNome(res2.getString("NOME"));
                    rp.setQtde(res2.getInt("QTDE"));
                    pd.addRoupa(rp);
                }
                
                return pd;
            }
        } catch (SQLException e) {
            throw new DAOException("ERRO NO SELECT DO PEDIDO", e);
        } catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        }finally{
            ConectarBD.desconectar();
        }
        
        return null;      
    }
    
    @Override
    public void update(Pedido pd) throws DAOException{
        PreparedStatement consulta;
        
        try {
            consulta = ConectarBD.conectar().prepareStatement(PEDIDO_UPDATE);
            
            consulta.setString(1, pd.getCpf());
            consulta.setDouble(2, pd.getPreco());
            consulta.setString(3, String.valueOf(pd.getStatus()));
            consulta.setDate(4, new Date(pd.getDataPrazoInMili()));
            consulta.setDate(5, new Date(pd.getDataAberturaInMili()));
            consulta.setInt(6, pd.getId());
            
            consulta.execute();
        } catch (SQLException e) {
            throw new DAOException("ERRO NO UPDATE DO PEDIDO", e);
        } catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        }finally{
            ConectarBD.desconectar();
        } 
    }
    
    @Override
    public void delete(Integer id) throws DAOException{
        PreparedStatement consulta;
        
        try {
            consulta = ConectarBD.conectar().prepareStatement(PEDIDO_DELETE);
            consulta.setInt(1, id);
            
            consulta.execute();
        }catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        }catch (SQLException e) {
            throw new DAOException("ERRO NO DELETE DO PEDIDO", e);
        }finally{
            ConectarBD.desconectar();
        } 
    }

    @Override
    public ArrayList<Pedido> selectAllOrderByDate() throws DAOException{
        PreparedStatement consulta;
        ResultSet rs = null;
        ArrayList<Pedido> lista = new ArrayList<>();
        
        try {
            consulta = ConectarBD.conectar().prepareStatement(PEDIDO_SELECT_ALL_BY_DATE);
            rs = consulta.executeQuery();

            while(rs.next()){
                Pedido pd = new Pedido();
                pd.setId(rs.getInt("ID_PEDIDO"));
                pd.setStatus(rs.getString("STATUS_PEDIDO"));
                pd.setCpf(rs.getString("CPF_CLIENTE"));
                pd.setDataAberturaFromSqlDate(rs.getDate("DATA_ABERTURA"));
                pd.setDataPrazoFromSqlDate(rs.getDate("PRAZO"));
                    
                for(int i=1; i <= 4; i++){
                    if (rs.getString("NOME" + i) == null) {
                        break;
                    }
                    Roupa rp = new Roupa();
                    rp.setNome(rs.getString("NOME" + i));
                    rp.setQtde(rs.getInt("QTDE" + i));
                    pd.addRoupa(rp);
                }
                
                lista.add(pd);
            }
        } catch (SQLException e) {
            throw new DAOException("ERRO NO SELECT DOS PEDIDOS DA TELA INICIAL", e);
        }catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        } finally{
            ConectarBD.desconectar();
        }
        
        return lista;
    }
    
    @Override
    public ArrayList<Pedido> selectByDates(LocalDate dt1, LocalDate dt2) throws DAOException{
        PreparedStatement consulta;
        ResultSet rs = null;
        ArrayList<Pedido> lista = new ArrayList<>();
        
        try {
            consulta = ConectarBD.conectar().prepareStatement(PEDIDO_SELECT_BETWEEN_DATES);
            consulta.setString(1, dt1.toString());
            consulta.setString(2, dt2.toString());
            consulta.setString(3, dt1.toString());
            consulta.setString(4, dt2.toString());
            rs = consulta.executeQuery();

            while(rs.next()){
                Pedido pd = new Pedido();
                pd.setId(rs.getInt("ID_PEDIDO"));
                pd.setStatus(rs.getString("STATUS_PEDIDO"));
                pd.setCpf(rs.getString("CPF_CLIENTE"));
                pd.setDataPrazoFromSqlDate(rs.getDate("PRAZO"));
                pd.setDataPrazoFromSqlDate(rs.getDate("DATA_ABERTURA"));
                    
                for(int i=1; i <= 4; i++){
                    if (rs.getString("NOME" + i) == null) {
                        break;
                    }
                    Roupa rp = new Roupa();
                    rp.setNome(rs.getString("NOME" + i));
                    rp.setQtde(rs.getInt("QTDE" + i));
                    pd.addRoupa(rp);
                }
                
                lista.add(pd);
            }
        } catch (SQLException e){
            throw new DAOException("ERRO NO SELECT DOS PEDIDOS DA TELA INICIAL", e);
        } catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        }finally{
            ConectarBD.desconectar();
        }
        
        return lista;      
    }
    
    @Override
    public ArrayList<Pedido> selectByUser(String cpf) throws DAOException{
        PreparedStatement consulta;
        ResultSet rs = null;
        ArrayList<Pedido> lista = new ArrayList<>();
        
        try {
            consulta = ConectarBD.conectar().prepareStatement(PEDIDO_SELECT_ALL_BY_USER);
            consulta.setString(1, cpf);
            rs = consulta.executeQuery();
            
            while(rs.next()){
                Pedido pd = new Pedido();
                pd.setId(rs.getInt("ID_PEDIDO"));
                pd.setStatus(rs.getString("STATUS_PEDIDO"));
                pd.setCpf(rs.getString("CPF_CLIENTE"));
                pd.setDataAberturaFromSqlDate(rs.getDate("DATA_ABERTURA"));
                pd.setDataPrazoFromSqlDate(rs.getDate("PRAZO"));
                    
                for(int i=1; i <= 4; i++){
                    if (rs.getString("NOME" + i) == null) {
                        break;
                    }
                    Roupa rp = new Roupa();
                    rp.setNome(rs.getString("NOME" + i));
                    rp.setQtde(rs.getInt("QTDE" + i));
                    pd.addRoupa(rp);
                }
                
                lista.add(pd);
            }
        } catch (SQLException e) {
            throw new DAOException("ERRO NO SELECT DOS PEDIDOS DA TELA INICIAL", e);
        }catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        } finally{
            ConectarBD.desconectar();
        }
        
        return lista;      
    }

    @Override
    public ArrayList<Pedido> selectByUserId(String cpf, Integer idpedido) throws DAOException{
        PreparedStatement consulta;
        ResultSet rs = null;
        ArrayList<Pedido> lista = new ArrayList<>();
        
        try {
            consulta = ConectarBD.conectar().prepareStatement(PEDIDO_SELECT_ALL_BY_USER_ID);
            consulta.setString(1, cpf);
            consulta.setInt(2, idpedido);
            rs = consulta.executeQuery();
            
            while(rs.next()){
                Pedido pd = new Pedido();
                pd.setId(rs.getInt("ID_PEDIDO"));
                pd.setStatus(rs.getString("STATUS_PEDIDO"));
                pd.setCpf(rs.getString("CPF_CLIENTE"));
                pd.setDataAberturaFromSqlDate(rs.getDate("DATA_ABERTURA"));
                pd.setDataPrazoFromSqlDate(rs.getDate("PRAZO"));
                    
                for(int i=1; i <= 4; i++){
                    if (rs.getString("NOME" + i) == null) {
                        break;
                    }
                    Roupa rp = new Roupa();
                    rp.setNome(rs.getString("NOME" + i));
                    rp.setQtde(rs.getInt("QTDE" + i));
                    pd.addRoupa(rp);
                }
                
                lista.add(pd);
            }
        } catch (SQLException e) {
            throw new DAOException("ERRO NO SELECT DOS PEDIDOS DA TELA INICIAL", e);
        }catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        } finally{
            ConectarBD.desconectar();
        }
        
        return lista;      
    }
    
    @Override
    public void updateStatusPedido(Integer idPedido, String status) throws DAOException {
         PreparedStatement consulta;
        
        try {
            consulta = ConectarBD.conectar().prepareStatement(PEDIDO_UPDATE_STATUS);
            
            consulta.setString(1, status);
            consulta.setInt(2, idPedido);
            
            consulta.execute();
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            throw new ViolacaoConstraintException("ERRO NO UPDATE DO STATUS DO PEDIDO, CONSTRAINT VIOLADA", e);
        }catch (SQLException e) {
            throw new DAOException("ERRO NO UPDATE DO STATUS DO PEDIDO", e);
        } catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        }finally{
            ConectarBD.desconectar();
        } 
    }
    
    @Override
    public void updatePagarPedido(Integer idPedido, LocalDateTime date) throws DAOException {
         PreparedStatement consulta;
        
        try {
            consulta = ConectarBD.conectar().prepareStatement(PEDIDO_UPDATE_PAGAR);
            
            consulta.setDate(1, new Date(date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            consulta.setInt(2, idPedido);
            
            consulta.execute();
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            throw new ViolacaoConstraintException("ERRO NO UPDATE DO STATUS DO PEDIDO, CONSTRAINT VIOLADA", e);
        }catch (SQLException e) {
            throw new DAOException("ERRO NO UPDATE DO STATUS DO PEDIDO", e);
        } catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        }finally{
            ConectarBD.desconectar();
        } 
    }
    
    @Override
    public Pedido selectByIdCpf(Integer id, String cpf) throws DAOException{
        PreparedStatement consulta;
        PreparedStatement consultaRoupas;
        ResultSet rs = null;
         
        try {
            consulta = ConectarBD.conectar().prepareStatement(PEDIDO_SELECT_ID_CPF);
            consulta.setInt(1, id);
            consulta.setString(2, cpf);
            rs = consulta.executeQuery();

            while(rs.next()){
                Pedido pd = new Pedido();
                pd.setId(rs.getInt("ID_PEDIDO"));
                pd.setCpf(rs.getString("CPF_CLIENTE"));
                pd.setPreco(rs.getDouble("PRECO"));
                pd.setStatus(rs.getString("STATUS_PEDIDO"));
                pd.setDataPrazoFromSqlDate(rs.getDate("PRAZO"));
                pd.setDataAberturaFromSqlDate(rs.getDate("DATA_ABERTURA"));
                pd.setDataPagamentoFromSqlDate(rs.getDate("DATA_PAGAMENTO"));
                pd.setNomeCliente(rs.getString("NOME"));
                pd.setCepCliente(rs.getString("CEP"));
                pd.setNumCliente(rs.getInt("NUMERO"));
                pd.setCompCliente(rs.getString("COMPLEMENTO"));

                consultaRoupas = ConectarBD.conectar().prepareStatement(PEDIDO_ROUPAS_SELECT_ALL);
                consultaRoupas.setInt(1, rs.getInt("ID_PEDIDO"));
                ResultSet res2 = consultaRoupas.executeQuery();
                while(res2.next()){
                    Roupa rp = new Roupa();
                    rp.setId(res2.getInt("ID"));
                    rp.setNome(res2.getString("NOME"));
                    rp.setQtde(res2.getInt("QTDE"));
                    pd.addRoupa(rp);
                    
                    
                }
                return pd;
            }
        } catch (SQLException e) {
            throw new DAOException("ERRO NO SELECT DO PEDIDO POR ID E CLIENTE ", e);
        } catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        }finally{
            ConectarBD.desconectar();
        }
        
        return null;      
    }

    @Override
    public ArrayList<Pedido> selectByStatusAndCliente(String st, String idCliente) throws DAOException{
        PreparedStatement consulta;
        ResultSet rs = null;
        ArrayList<Pedido> lista = new ArrayList<>();
        
        try {
            consulta = ConectarBD.conectar().prepareStatement(PEDIDO_SELECT_ALL_BY_STATUS_AND_USER);
            consulta.setString(1, st);
            consulta.setString(2, idCliente);
            rs = consulta.executeQuery();

            while(rs.next()){
                Pedido pd = new Pedido();
                pd.setId(rs.getInt("ID_PEDIDO"));
                pd.setStatus(rs.getString("STATUS_PEDIDO"));
                pd.setCpf(rs.getString("CPF_CLIENTE"));
                pd.setDataAberturaFromSqlDate(rs.getDate("DATA_ABERTURA"));
                pd.setDataPrazoFromSqlDate(rs.getDate("PRAZO"));
                    
                for(int i=1; i <= 4; i++){
                    if (rs.getString("NOME" + i) == null) {
                        break;
                    }
                    Roupa rp = new Roupa();
                    rp.setNome(rs.getString("NOME" + i));
                    rp.setQtde(rs.getInt("QTDE" + i));
                    pd.addRoupa(rp);
                }
                
                lista.add(pd);
            }
        } catch (SQLException e) {
            throw new DAOException("ERRO NO SELECT DOS PEDIDOS ABERTOS.", e);
        }catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        } finally{
            ConectarBD.desconectar();
        }
        
        return lista;
    }


    @Override
    public ArrayList<Pedido> selectAllByStatus(String st) throws DAOException{
        PreparedStatement consulta;
        ResultSet rs = null;
        ArrayList<Pedido> lista = new ArrayList<>();
        
        try {
            consulta = ConectarBD.conectar().prepareStatement(PEDIDO_SELECT_ALL_BY_STATUS);
            consulta.setString(1, st);
            rs = consulta.executeQuery();

            while(rs.next()){
                Pedido pd = new Pedido();
                pd.setId(rs.getInt("ID_PEDIDO"));
                pd.setStatus(rs.getString("STATUS_PEDIDO"));
                pd.setCpf(rs.getString("CPF_CLIENTE"));
                pd.setDataAberturaFromSqlDate(rs.getDate("DATA_ABERTURA"));
                pd.setDataPrazoFromSqlDate(rs.getDate("PRAZO"));
                    
                for(int i=1; i <= 4; i++){
                    if (rs.getString("NOME" + i) == null) {
                        break;
                    }
                    Roupa rp = new Roupa();
                    rp.setNome(rs.getString("NOME" + i));
                    rp.setQtde(rs.getInt("QTDE" + i));
                    pd.addRoupa(rp);
                }
                
                lista.add(pd);
            }
        } catch (SQLException e) {
            throw new DAOException("ERRO NO SELECT DOS PEDIDOS ABERTOS.", e);
        }catch (BDException e){
            throw new BDException("ERRO NA CONEXAO DO BD", e); 
        } finally{
            ConectarBD.desconectar();
        }
        
        return lista;
    }
}
