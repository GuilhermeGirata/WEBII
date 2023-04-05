/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dao;

import com.web2.model.dao.ConectarBD;
import com.web2.model.exceptions.DAOException;
import java.sql.Connection;
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
public class ConectarBDTest {
    
    public ConectarBDTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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
     * Test of main method, of class ConectarBD.
     */

    @Test
    public void testConectar() throws DAOException {
        ConectarBD.conectar();
        System.out.println(ConectarBD.getStatus());
        assertEquals("CONECTADO!", ConectarBD.getStatus());
        ConectarBD.desconectar();
    }


    
}
