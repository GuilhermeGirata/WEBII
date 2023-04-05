/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dao;

import com.web2.model.dao.RoupaDAO;
import java.util.ArrayList;
import com.web2.model.beans.Roupa;
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
public class RoupaDAOTest {
    
    private static Roupa rp = new Roupa();
    private static Roupa rp2 = new Roupa();
    private static Roupa rp3 = new Roupa();
    private static Roupa rp4 = new Roupa();
    private static Roupa rp5 = new Roupa();
    
    private static Roupa rpAlt = new Roupa();
    
    private static RoupaDAO dao = new RoupaDAO();
    
    public RoupaDAOTest() {

    }
    
    @BeforeClass
    public static void setUpClass() {
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
     * Test of insert method, of class RoupaDAO.
     */
    @Test
    public void testInsert() throws Exception {
        System.out.println("insert");
        
        dao.insert(rp);
        dao.insert(rp2);
        dao.insert(rp3);
        dao.insert(rp4);
        dao.insert(rp5);
    }

    /**
     * Test of update method, of class RoupaDAO.
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        dao.update(rp2);
        
    }

    /**
     * Test of delete method, of class RoupaDAO.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        dao.delete(1);
    }

    /**
     * Test of select method, of class RoupaDAO.
     */
    @Test
    public void testSelect() throws Exception {
        System.out.println("select");
        Roupa result = dao.selectById(1);
        ///assertEquals(1, result.size());
    }
    
    @Test
    public void testSelectAll() throws Exception {
        System.out.println("select all\n");
        ArrayList<Roupa> result = dao.selectAll();
        for (Roupa roupa : result) {
            System.out.println(roupa.getNome());
        }
    }
    
}
