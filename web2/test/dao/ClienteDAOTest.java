/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dao;

import com.web2.model.dao.ClienteDAO;
import com.web2.model.exceptions.DAOException;
import java.util.ArrayList;
import com.web2.model.beans.Cliente;
import com.web2.model.exceptions.BDException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class ClienteDAOTest {
    
    
    private static Cliente cl = new Cliente();
    private static Cliente cl2 = new Cliente();
    private static Cliente cl3 = new Cliente();
    private static ClienteDAO dao = new ClienteDAO();
    
    public ClienteDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        cl.setCpf("121.111.111-12");
        cl.setEmail("joao1@gmail.com");
        cl.setNome("Joao");
        cl.setTelefone("41111111111");
        cl.setCep("11111111");
        cl.setNumero(25);
        cl.setComplemento("Casa");
        cl.setSenha("1234");
        
        cl2.setCpf("212.222.222-23");
        cl2.setEmail("jose@gmail.com");
        cl2.setNome("Jose");
        cl2.setTelefone("41222222222");
        cl2.setCep("22222222");
        cl2.setNumero(22);
        cl2.setComplemento("Sobrado");
        cl2.setSenha("5678");
        
        cl3.setCpf("313.333.333-36");
        cl3.setEmail("teste@gmail.com");
        cl3.setNome("Joana");
        cl3.setTelefone("41333333333");
        cl3.setCep("33333333");
        cl3.setNumero(33);
        cl3.setComplemento("Apartamento");
        cl3.setSenha("9012");
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
    public void testInsert(){
        
        System.out.println("INSERT");
        try {
            dao.insert(cl);
            dao.insert(cl2);
            dao.insert(cl3);
            
        }catch (BDException ex) {
            Logger.getLogger(ClienteDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DAOException ex) {
            Logger.getLogger(ClienteDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of select method, of class ClienteDAO.
     */
    @Test
    public void testSelect() throws DAOException {
        System.out.println("select");
        String query = "098.843.999-99";
        Cliente result = dao.selectById(query);
            System.out.println(result.getNome());
        
        ////assertEquals(1, result.size());
    }

    /**
     * Test of update method, of class ClienteDAO.
     */
    @Test
    public void testUpdate() throws DAOException {
        System.out.println("update");
        dao.update(cl2);
        String query = "11111111111111";
        Cliente result = dao.selectById(query);
        assertTrue(result.getNumero().equals(11));
    }


    @Test
    public void testDelete() throws DAOException {
        System.out.println("delete");
        String cpf = "11111111111111";
        dao.delete(cpf);
        String query = "SELECT * FROM CLIENTE where CPF = 11111111111111";
        Cliente result = dao.selectById(query);
        
    }
    
}
