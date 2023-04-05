/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author jeffe
 */
public class testeRegex {
    public static void main(String[] args) {
        if("000.000.000-00".matches("([0-9]{3}[.][0-9]{3}[.][0-9]{3}[-][0-9]{2})")){
            System.out.println("util.testeRegex.main()");
        }
    }
}
