
package com.web2.model.facade;

import com.web2.model.dao.PedidoDAO;
import com.web2.model.dao.RoupaDAO;
import com.web2.model.exceptions.DAOException;
import com.web2.model.exceptions.StatusManagementException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import com.web2.model.beans.Pedido;
import com.web2.model.beans.Roupa;
import com.web2.model.beans.Endereco;
import com.web2.model.exceptions.InsertPedidoException;
import com.web2.model.exceptions.PrecoTotalException;
import com.web2.model.exceptions.SelectPedidosClienteException;
import com.web2.model.exceptions.ViaCepException;
import java.util.Objects;
import com.web2.model.util.ViaCep;


public class LogicaClientePedido {

    public static final Integer DIAS_EXTRAS = 1;
      
    public static void insertPedido(ArrayList<Roupa> roupas, String idCliente) throws InsertPedidoException{
        try {
            Pedido pd = new Pedido();
            LocalDateTime dtime = LocalDateTime.of(LocalDate.now(), LocalTime.now());
            
            
            //Cria pedido para ser inserido no bd;
            // Id n é necessário inicializar
            pd.setRoupas(roupas);
            pd.setCpf(idCliente);
            pd.setDataAbertura(dtime); //Data de Agr
            pd.setStatus("AB");
            
            //Seta preço total e a roupa com maior tempo limite 
            pd = selectPedidoPrecoTotal(pd);
            pd.setDataPrazo(dtime.plusDays(pd.getDataDifDias() + DIAS_EXTRAS));// data de agr mais o tempo limite + 1 dia de prazo para entrega
            
            //Insere Pedido no BD
            new PedidoDAO().insert(pd);
            
        } catch (PrecoTotalException | DAOException ex) {
           throw new InsertPedidoException(ex.getMessage(), ex);
        }  
    }
    
    public static Pedido selectPedidoPrecoTotal(Pedido pd) throws PrecoTotalException{
        Integer dias = 0;
        try {
            //cria um array de ids das roupas
            ArrayList<Integer> a = new ArrayList();
            for(Roupa rp : pd.getRoupas()){
                a.add(rp.getId());
            }
            //SELECIONA TODAS AS ROUPAS NA LISTA DO BD
            ArrayList<Roupa> list;
            list = new RoupaDAO().selectTempoPrecoFromArray(a);
            
            //SETA O PRECO E PEGA O MAIOR PRAZO E FAZ O CALCULO DE PRECO TOTAL
            for (int i = 0; i < pd.getRoupas().size(); i++) {
                for (Roupa rp2 : list) {
                    if(Objects.equals(pd.getRoupas().get(i).getId(), rp2.getId())){
                        rp2.setQtde(pd.getRoupas().get(i).getQtde());
                        pd.setPreco(pd.getPreco() + rp2.getPrecoQtde());
                        if(rp2.getLimiteDias() > dias){
                            dias = rp2.getLimiteDias();
                        }
                    }
                }
            }
            pd.setDataDifDias(dias);
            pd.setRoupas(list);
            return pd;
        } catch (DAOException ex) {
            throw new PrecoTotalException(ex.getMessage() + " TENTE NOVAMENTE MAIS TARDE", ex);
        }catch(NullPointerException ex){
            throw new PrecoTotalException(ex.getMessage() + " CONTATE O DESENVOLVEDOR", ex);
        }
    }
    
    public static ArrayList<Pedido> selectClientePedidos(String idCliente) throws SelectPedidosClienteException{
        try {
            return new PedidoDAO().selectByUser(idCliente);
        } catch (DAOException ex) {
            throw new SelectPedidosClienteException("NÃO FOI POSSÍVEL SELECIONAR O(S) PEDIDO(S) DO CLIENTE. " + ex.getMessage() + " CONTATE O DESENVOLVEDOR", ex);
        }
    }
    
    public static ArrayList<Pedido> selectClientePedidoAndId(String idCliente, Integer idpedido) throws SelectPedidosClienteException{
        try {
            return new PedidoDAO().selectByUserId(idCliente, idpedido);
        } catch (DAOException ex) {
            throw new SelectPedidosClienteException("NÃO FOI POSSÍVEL SELECIONAR O(S) PEDIDO(S) DO CLIENTE. " + ex.getMessage() + " CONTATE O DESENVOLVEDOR", ex);
        }
    }
    
    public static ArrayList<Pedido> selectByStatus(String status, String idCliente) throws SelectPedidosClienteException{
        try {
            return new PedidoDAO().selectByStatusAndCliente(status, idCliente);
        } catch (DAOException ex) {
            throw new SelectPedidosClienteException("NÃO FOI POSSÍVEL SELECIONAR O(S) PEDIDO(S) DO CLIENTE. " + ex.getMessage() + " CONTATE O DESENVOLVEDOR", ex); 
        }
    }
    
    public static Pedido selectClientePedido(Integer id,String idCliente) throws SelectPedidosClienteException{
        Pedido pd;
        try {
            pd = new PedidoDAO().selectByIdCpf(id, idCliente);
        } catch (DAOException ex) {
            throw new SelectPedidosClienteException("NÃO FOI POSSÍVEL SELECIONAR O PEDIDO. " + ex.getMessage() + " CONTATE O DESENVOLVEDOR", ex);
        }
        try {
            pd.setEndereco(ViaCep.findCep(pd.getCepCliente()));
        } catch (ViaCepException ex) {
            pd.setEndereco(new Endereco("ERRO EM CONECTAR COM A API"));
        }
        return pd;
    }
        
    public static void changeStatusPedido(Integer pdid, String clienteId) throws StatusManagementException, SelectPedidosClienteException{
        try {
            PedidoDAO dao = new PedidoDAO();
            Pedido pd = selectClientePedido(pdid, clienteId);
            LocalDateTime dtime = LocalDateTime.of(LocalDate.now(), LocalTime.now());
            
            if(pd.getStatus().equals("AP") && pd.getId() != null && pd != null){
                dao.updatePagarPedido(pdid, dtime);
                return;
            }
            throw new StatusManagementException("Esse Estado do Pedido Não pode ser Mudado");
        }catch (DAOException ex) {
            throw new SelectPedidosClienteException("NÃO FOI POSSÍVEL PAGAR O PEDIDO. " + ex.getMessage() + " CONTATE O DESENVOLVEDOR", ex);
        }
    }
    
    public static void cancelPedido(Integer pdid, String clienteId) throws StatusManagementException, SelectPedidosClienteException{
        try {
            PedidoDAO dao = new PedidoDAO();
            Pedido pd = selectClientePedido(pdid, clienteId);
            if(pd.getStatus().equals("AB") && pd.getId() != null && pd != null){
                dao.updateStatusPedido(pd.getId(), "CA");
                return;
            }
            throw new StatusManagementException("Esse Estado do Pedido Não pode ser Mudado");
        } catch (DAOException ex) {
            throw new SelectPedidosClienteException("NÃO FOI POSSÍVEL CANCELAR O PEDIDO. " + ex.getMessage() + " CONTATE O DESENVOLVEDOR", ex);
        }
    }
    
}
