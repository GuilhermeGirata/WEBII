/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dao;

import com.web2.model.dao.FuncionarioDAO;
import java.util.ArrayList;
import com.web2.model.beans.Funcionario;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jeffe
 */
public class FuncionarioDAOTest {
    
    private static Funcionario fc = new Funcionario();
    private static Funcionario fc2 = new Funcionario();
    private static FuncionarioDAO dao = new FuncionarioDAO();
    
    public FuncionarioDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        fc.setId(1);
        fc.setEmail("maria@gmail.com");
        fc.setNome("Maria");
        fc.setSenha("1234");
        
        fc2.setId(2);
        fc2.setEmail("mario@gmail.com");
        fc2.setNome("Mario");
        fc2.setSenha("5678");
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
     * Test of insert method, of class FuncionarioDAO.
     */
    @Test
    public void testInsert() throws Exception {
        System.out.println("Insert");
        
        dao.insert(fc);
        dao.insert(fc2);
    }

    /**
     * Test of update method, of class FuncionarioDAO.
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        dao.update(fc2);
    }

    /**
     * Test of delete method, of class FuncionarioDAO.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        dao.delete(1);
    }

    /**
     * Test of selectById method, of class FuncionarioDAO.
     */
    @Test
    public void testSelectById() throws Exception {
        System.out.println("selectById");
        Funcionario result = dao.selectById(1);
        //assertEquals(1, result.size());
    }
    
    @Test
    public void testSelectAll() throws Exception {
        System.out.println("select All");
        ArrayList<Funcionario> result = dao.selectAll();
        for (Funcionario fc : result) {
            System.out.println(fc.getNome());
        }
    }
}
