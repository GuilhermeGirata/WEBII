
package com.web2.model.util;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidaCampo {
 
    public static String validaString(String txt, Integer minsize, Integer maxsize){
        if(txt == null){
            return null;
        }
        if(txt.isEmpty()){
            return null;
        }
        if(txt.length() < minsize){
            return null;
        }else if(txt.length() > maxsize){
            return null;
        }
        
        return txt;
    }
    
    public static Integer validaInteger(String txt, Integer maxLenght){
        if(txt == null){
            return null;
        }
        if(txt.isEmpty()){
            return null;
        }
        if(txt.length() > maxLenght){
            return null;
        }
        
        Integer num;
        
        try {
            num = Integer.valueOf(txt);
            if(num <= 0){
                return null;
            }
            return num;
        } catch (NumberFormatException e) {
            return null;
        }
   
    }   
  
    public static Double validaDouble(String txt){
        if(txt == null) return null;
        try {
           return Double.valueOf(txt);
        } catch (NumberFormatException e) {
            return null;
        }
    }
            
    public static String validaCep(String cep){
        if (cep == null){
            return null;
        }
        cep = cep.replaceAll("[^0-9]", "");
        if(cep.isEmpty()){
            return null;
        }
        if(cep.length() != 8){
            return null;
        }
        return cep;
    }
    
    public static String validaCpf(String cpf){
        if (cpf == null){
            return null;
        }
        if(cpf.isEmpty()){
            return null;
        }
        if (cpf.equals("000.000.000-00") || cpf.equals("111.111.111-11") || cpf.equals("222.222.222-22") || cpf.equals("333.333.333-33") || cpf.equals("444.444.444-44") ||
            cpf.equals("555.555.555-55") || cpf.equals("666.666.666-66") || cpf.equals("777.777.777-77") || cpf.equals("888.888.888-88") || cpf.equals("999.999.999-99") || 
            cpf.length() != 14){
            return null;
        }
        
        if(cpf.matches("([0-9]{3}[.][0-9]{3}[.][0-9]{3}[-][0-9]{2})")){
            return cpf;
        }
        
        return null;
    }   
    
    public static String validaTelefone(String tel){
         if (tel == null){
            return null;
        }
        tel = tel.replaceAll("[^0-9]", "");
        if(tel.isEmpty()){
            return null;
        }
        
        if(tel.matches("([0-9]{11})")){
            return tel;
        }
        return null;
    }
    
    public static String validaEmail(String email){
         if (email == null){
            return null;
        }
        if(email.isEmpty() || email.length() <= 2){
            return null;
        }
        
        return email;

    }
  
    public static LocalDate validaData(String dt){
        if(dt == null) return null;
        if(dt.isEmpty() || dt.length() != 10) return null;
        
        if(dt.matches("[0-9]{2}[/][0-9]{2}[/][0-9]{4}")){
           dt = convertDateFormat(dt);
        }
        
        if(dt.matches("([0-9]{4}[-][0-1][0-9][-][0-3][0-9])")){
            try {
                return LocalDate.parse(dt);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
    

    public static String convertDateFormat(String date) {
        Pattern pattern = Pattern.compile("(\\d{2})/(\\d{2})/(\\d{4})");
        Matcher matcher = pattern.matcher(date);
        if (matcher.matches()) {
            String day = matcher.group(1);
            String month = matcher.group(2);
            String year = matcher.group(3);
            return year + "-" + month + "-" + day;
        } else {
            return null;
        }
    }

}

