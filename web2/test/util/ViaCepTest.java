/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package util;

import com.web2.model.beans.Endereco;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static com.web2.model.util.ViaCep.findCep;


public class ViaCepTest {
    
    public ViaCepTest() {
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
     * Test of findCep method, of class ViaCep.
     */
    @Test
    public void testFindCep() throws Exception {
        System.out.println("findCep");
        String CEP = "";
        Map<String, String> expResult = null;
        Endereco dados = findCep("81460315");
         
        System.out.println(dados.getCidade());
    }

    
}
