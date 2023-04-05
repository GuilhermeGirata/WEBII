package com.web2.model.util;

import com.web2.model.exceptions.EncryptionException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
    
    public static String cripto(String senha) throws EncryptionException{
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte messageDigest[] = md.digest(senha.getBytes("UTF-8"));
            
            
            StringBuilder sb = new StringBuilder();
            
            for(byte b : messageDigest){
                sb.append(String.format("%02X", 0xFF & b));
            }
            String SenhaHex = sb.toString();
            return SenhaHex;
        } catch (NoSuchAlgorithmException ex) {
            throw new EncryptionException("O ALGORITMO DE ENCRIPTAÇÃO NÃO ESTÁ DISPONÍVEL", ex);
        } catch (UnsupportedEncodingException ex) {
            throw new EncryptionException("ESSA CODIFICAÇÃO NÃO É SUPORTADA", ex);
        }
    }
}
