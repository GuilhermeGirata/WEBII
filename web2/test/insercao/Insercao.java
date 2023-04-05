/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit3TestClass.java to edit this template
 */
package insercao;

import com.web2.model.exceptions.DAOException;
import com.web2.model.beans.Cliente;
import com.web2.model.beans.Funcionario;
import com.web2.model.beans.Pedido;
import com.web2.model.beans.Roupa;
import com.web2.model.dao.ClienteDAO;
import com.web2.model.exceptions.AccountExistException;
import com.web2.model.exceptions.CadastraClienteException;
import com.web2.model.exceptions.CpfExistException;
import com.web2.model.exceptions.EncryptionException;
import com.web2.model.exceptions.InsertFuncionarioException;
import com.web2.model.exceptions.InsertRoupaException;
import com.web2.model.exceptions.ViolacaoConstraintException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import com.web2.model.facade.LogicaFuncionario;
import com.web2.model.facade.LogicaRoupa;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.web2.model.util.Encryption;

/**
 *
 * @author jeffe
 */
public class Insercao{
    
    private static Cliente cl = new Cliente();
    private static Cliente cl2 = new Cliente();
    private static Cliente cl3 = new Cliente();
    
    private static Funcionario fc = new Funcionario();
    private static Funcionario fc2 = new Funcionario();
    
    private static Roupa rp = new Roupa();
    private static Roupa rp2 = new Roupa();
    private static Roupa rp3 = new Roupa();
    private static Roupa rp4 = new Roupa();
    private static Roupa rp5 = new Roupa();
    private static Pedido pd = new Pedido();
    private static Pedido pd2 = new Pedido();
    
    public Insercao() {
    }
    
    
    
    @BeforeClass
    public static void setUpClass() throws EncryptionException {
        cl.setCpf("121.111.111-12");
        cl.setEmail("joao1@gmail.com");
        cl.setNome("Joao");
        cl.setTelefone("41111111111");
        cl.setCep("11111111");
        cl.setNumero(25);
        cl.setComplemento("Casa");
        cl.setSenha(Encryption.cripto("1234"));
        
        cl2.setCpf("212.222.222-23");
        cl2.setEmail("jose@gmail.com");
        cl2.setNome("Jose");
        cl2.setTelefone("41222222222");
        cl2.setCep("22222222");
        cl2.setNumero(22);
        cl2.setComplemento("Sobrado");
        cl2.setSenha(Encryption.cripto("1234"));
        
        cl3.setCpf("313.333.333-36");
        cl3.setEmail("teste@gmail.com");
        cl3.setNome("Joana");
        cl3.setTelefone("41333333333");
        cl3.setCep("33333333");
        cl3.setNumero(33);
        cl3.setComplemento("Apartamento");
        cl3.setSenha(Encryption.cripto("1234"));
        
        fc.setId(1);
        fc.setEmail("maria@gmail.com");
        fc.setNome("Maria");
        fc.setSenha("1234");
        
        
        fc2.setId(2);
        fc2.setEmail("mario@gmail.com");
        fc2.setNome("Mario");
        fc2.setSenha("1234");
        
        rp.setId(1);
        rp.setLimiteDias(2);
        rp.setNome("Calca");
        rp.setPreco(2.50);
        
        rp2.setId(2);
        rp2.setLimiteDias(1);
        rp2.setNome("Camisa");
        rp2.setPreco(5.50);       
        
        rp3.setId(3);
        rp3.setLimiteDias(2);
        rp3.setNome("Camiseta");
        rp3.setPreco(6.50);
        
        rp4.setId(4);
        rp4.setLimiteDias(1);
        rp4.setNome("Meia");
        rp4.setPreco(2.50);
        
        rp5.setId(5);
        rp5.setLimiteDias(1);
        rp5.setNome("Cueca");
        rp5.setPreco(3.50);
        
        Roupa rp1 = new Roupa();
        rp1.setId(1);
        rp1.setQtde(3);
        
        Roupa rp2 = new Roupa();
        rp2.setId(2);
        rp2.setQtde(5);
        
        pd.setId(4);
        pd.setCpf("121.111.111-12");
        pd.setPreco(20.1);
        pd.setStatus("AB");
        pd.setDataPrazo(LocalDateTime.of(LocalDate.of(2022, 05, 11), LocalTime.of(12, 05)));
        //pd.setDataPagamento(LocalDateTime.of(LocalDate.of(2022, 05, 11), LocalTime.of(12, 05)));
        pd.setDataAbertura(LocalDateTime.of(LocalDate.of(2022, 04, 11), LocalTime.of(12, 05)));
        pd.addRoupa(rp1);
        pd.addRoupa(rp2);
        
        pd2.setId(1);
        pd2.setCpf("212.222.222-23");
        pd2.setPreco(22.2);
        pd2.setStatus("PG");
        pd2.setDataPrazo(LocalDateTime.of(LocalDate.of(2022, 05, 11), LocalTime.of(12, 05)));
        pd2.setDataPagamento(LocalDateTime.of(LocalDate.of(2022, 05, 11), LocalTime.of(12, 05)));
        pd2.setDataAbertura(LocalDateTime.of(LocalDate.of(2022, 04, 11), LocalTime.of(12, 05)));
        pd2.addRoupa(rp1);
        pd2.addRoupa(rp2);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of insert method, of class ClienteDAO.
     */
    @Test
    public void InsertCliente() throws AccountExistException, CpfExistException, CadastraClienteException, DAOException{
        ClienteDAO dao = new ClienteDAO();
        dao.insert(cl);
        dao.insert(cl2);
        dao.insert(cl3);
    }

    @Test
    public void InsertFuncionario() throws AccountExistException, InsertFuncionarioException, InsertFuncionarioException{
        LogicaFuncionario.insertFuncionario(fc);
        LogicaFuncionario.insertFuncionario(fc2);
        
    }
    
    @Test
    public void InsertRoupa() throws ViolacaoConstraintException, InsertRoupaException {
        LogicaRoupa.insertRoupa(rp);
        LogicaRoupa.insertRoupa(rp2);
        LogicaRoupa.insertRoupa(rp3);
        LogicaRoupa.insertRoupa(rp4);
        LogicaRoupa.insertRoupa(rp5);
        
    }    
    
    
}
