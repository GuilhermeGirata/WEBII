
package com.web2.model.util;

import com.web2.model.exceptions.EmailNotSendException;
import java.util.Properties;

public class EnviarEmail {
    
    private String nomeDestinatario;
    private String emailDestinatario;
    private String senhaDestinatario;
    private String cpfDestinatario;
    private String telefoneDestinatario;
    private String cepDestinatario;
    private String numeroDestinatario;
    private String complementoDestinatario;

    //----------------nome destinatário-----------------
    public String getNomeDestinatario() {
        return nomeDestinatario;
    }

    public void setNomeDestinatario(String nomeDestinatario) {
        this.nomeDestinatario = nomeDestinatario;
    }
    
    //----------------email destinatário-----------------
    public String getEmailDestinatario() {
        return emailDestinatario;
    }

    public void setEmailDestinatario(String emailDestinatario) {
        this.emailDestinatario = emailDestinatario;
    }
    
    //----------------senha destinatário-----------------
    public String getSenhaDestinatario() {
        return senhaDestinatario;
    }

    public void setSenhaDestinatario(String senhaDestinatario) {
        this.senhaDestinatario = senhaDestinatario;
    }
    //----------------cpf destinatário-----------------
    public String getCpfDestinatario() {
        return cpfDestinatario;
    }

    public void setCpfDestinatario(String cpfDestinatario) {
        this.cpfDestinatario = cpfDestinatario;
    }
    
    //----------------telefone destinatário-----------------
    public String getTelefoneDestinatario() {
        return telefoneDestinatario;
    }

    public void setTelefoneDestinatario(String telefoneDestinatario) {
        this.telefoneDestinatario = telefoneDestinatario;
    }
    //----------------cep destinatário-----------------
    public String getCepDestinatario() {
        return cepDestinatario;
    }

    public void setCepDestinatario(String cepDestinatario) {
        this.cepDestinatario = cepDestinatario;
    }
    //----------------numero destinatário-----------------
    public String getNumeroDestinatario() {
        return numeroDestinatario;
    }

    public void setNumeroDestinatario(String numeroDestinatario) {
        this.numeroDestinatario = numeroDestinatario;
    }
     //----------------complemento destinatário-----------------
    public String getComplementoDestinatario() {
        return complementoDestinatario;
    }

    public void setComplementoDestinatario(String complementoDestinatario) {
        this.complementoDestinatario = complementoDestinatario;
    }
    
    public void enviarGmail() throws EmailNotSendException {
              
        final String username = "melhorlavanderiadomundo@gmail.com";
        final String password = "hkkoxxfwnnniycmg";
        
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.setProperty("mail.smtp.starttls.enable", "true");
        prop.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
        jakarta.mail.Session session = jakarta.mail.Session.getInstance(prop,
                new jakarta.mail.Authenticator() {
                    @Override
                    protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new jakarta.mail.PasswordAuthentication(username, password);
                    }
                });

        try {
            jakarta.mail.Message message = new jakarta.mail.internet.MimeMessage(session);
            message.setFrom(new jakarta.mail.internet.InternetAddress("melhorlavanderiadomundo@gmail.com"));
            message.setRecipients(
                    jakarta.mail.Message.RecipientType.TO,
                    jakarta.mail.internet.InternetAddress.parse(emailDestinatario)
            );
            
            message.setSubject("Login e senha LOL Lavanderia");
            message.setText("Olá " + nomeDestinatario + "!\n\nSegue abaixo seu login e senha para acesso ao nosso sistema:\n\nLogin: " + emailDestinatario 
                    + "\nSenha: " + senhaDestinatario + "\n\nConfirme seus dados:\n\nCPF: " + cpfDestinatario + "\nTelefone: " + telefoneDestinatario
                    + "\nCEP: " + cepDestinatario + "\nNúmero: " + numeroDestinatario + "\nComplemento: " + complementoDestinatario); 
            jakarta.mail.Transport.send(message);
            
        } catch (jakarta.mail.MessagingException e) {
            throw new EmailNotSendException("NÃO FOI POSSÍVEL ENVIAR O EMAIL COM AS SUAS INFORMAÇÕES, CONTATE O DESENVOLVEDOR. ");
        }
        
    }
}
