/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dao;

import com.web2.model.dao.PedidoDAO;
import com.web2.model.exceptions.DAOException;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import com.web2.model.beans.Pedido;
import com.web2.model.beans.Roupa;
import java.time.format.DateTimeFormatter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * Só funciona quando 
 */
public class PedidoDAOTest {
    
    private static Pedido pd = new Pedido();
    private static Pedido pd2 = new Pedido();
    private static PedidoDAO dao = new PedidoDAO();
    
    public PedidoDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
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
        pd.setDataAbertura(LocalDateTime.of(LocalDate.of(2022, 04, 11), LocalTime.of(12, 05)));
        pd.addRoupa(rp1);
        pd.addRoupa(rp2);
        
        pd2.setId(1);
        pd2.setCpf("212.222.222-23");
        pd2.setPreco(22.2);
        pd2.setStatus("PG");
        pd2.setDataPrazo(LocalDateTime.of(LocalDate.of(2022, 05, 11), LocalTime.of(12, 05)));
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
     * Test of insert method, of class PedidoDAO.
     */
    @Test
    public void testLocalDateTime(){
        System.out.println(pd.getDataPrazo().format(DateTimeFormatter.ofPattern("dd/mm/yyyy")));
        
    }
    
    @Test
    public void testInsert() throws DAOException {
        System.out.println("insert");
        dao.insert(pd);
    }

    @Test
    public void testSelect() throws DAOException {
        System.out.println("select");
        Pedido pedido = dao.selectById(1);
            System.out.println(pedido.getCpf());
            System.out.println(pedido.getDataPrazo());
            for (Roupa rp : pedido.getRoupas()) {
                System.out.println(rp.getNome());
            }
       
        //assertEquals(1, result.size());
    }
    
    
    @Test
    public void testSelectOrderByDate() throws DAOException {
        System.out.println("select por data");
        ArrayList<Pedido> result = dao.selectAllOrderByDate();
        for (Pedido pedido : result) {
            System.out.println(pedido.getId());
            System.out.println(pedido.getDataPrazo());
            for (Roupa rp : pedido.getRoupas()) {
                System.out.println(rp.getNome());
            }
        }
    }
    
    @Test
    public void selectByDates() throws DAOException {
        System.out.println("select por data");
        ArrayList<Pedido> result = dao.selectByDates(LocalDate.of(2010, 01, 1), LocalDate.of(2022, 05, 02));
        for (Pedido pedido : result){
            System.out.println(pedido.getId());
            System.out.println(pedido.getDataPrazo());
            for (Roupa rp : pedido.getRoupas()) {
                System.out.println(rp.getNome());
            }
        }
    }
    
    @Test
    public void selectByUser() throws DAOException {
        System.out.println("select por User");
        ArrayList<Pedido> result = dao.selectByUser(pd2.getCpf());
        for (Pedido pedido : result){
            System.out.println(pedido.getId());
            System.out.println(pedido.getDataPrazo());
            for (Roupa rp : pedido.getRoupas()) {
                System.out.println(rp.getNome());
            }
        }
    }
    /**
     * Test of update method, of class PedidoDAO.
     */
    @Test
    public void testUpdate() throws DAOException {
        System.out.println("update");
        dao.update(pd2);
    }

    /**
     * Test of delete method, of class PedidoDAO.
     */
    @Test
    public void testDelete() throws DAOException {
        System.out.println("delete");
        dao.delete(1);
    }
    
}
