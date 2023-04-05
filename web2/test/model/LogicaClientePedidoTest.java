/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package model;

import com.web2.model.facade.LogicaClientePedido;
import java.util.ArrayList;
import com.web2.model.beans.Pedido;
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
public class LogicaClientePedidoTest {
    
    public LogicaClientePedidoTest() {
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
     * Test of insertPedido method, of class LogicaClientePedido.
     */
    @Test
    public void testInsertPedido() throws Exception {
        Pedido pd = new Pedido();
        
        ArrayList<Roupa> rps = new ArrayList<>();
        
        Roupa rp1 = new Roupa();
        rp1.setId(1);
        rp1.setQtde(3);
        
        Roupa rp2 = new Roupa();
        rp2.setId(2);
        rp2.setQtde(5);
        
        rps.add(rp1);
        rps.add(rp2);
        

        LogicaClientePedido.insertPedido(rps, "11111111111111");
    }

    /**
     * Test of selectPedidoPrecoTotal method, of class LogicaClientePedido.
     */
    public void testSelectPedidoPrecoTotal() throws Exception {
        System.out.println("selectPedidoPrecoTotal");
        Pedido pd = null;
        Pedido expResult = null;
        Pedido result = LogicaClientePedido.selectPedidoPrecoTotal(pd);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of selectClientePedidos method, of class LogicaClientePedido.
     */
    @Test
    public void testSelectClientePedidos() throws Exception {
        System.out.println("selectClientePedidos");
        String idCliente = "11111111111111";
        ArrayList<Pedido> expResult = null;
        ArrayList<Pedido> result = LogicaClientePedido.selectClientePedidos(idCliente);
  
            for (Roupa roupa : result.get(1).getRoupas()) {
                System.out.println(roupa.getNome());
            }
        
    }

    /**
     * Test of changeStatusPedido method, of class LogicaClientePedido.
     */
    public void testChangeStatusPedido() throws Exception {
        System.out.println("changeStatusPedido");
        Pedido pd = null;
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cancelPedido method, of class LogicaClientePedido.
     */
    public void testCancelPedido() throws Exception {
        System.out.println("cancelPedido");
        Pedido pd = null;
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
