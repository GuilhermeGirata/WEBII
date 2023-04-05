package com.web2.model.beans;

import java.io.Serializable;


public class Cliente implements Serializable{
   private String cpf;
   private String email;
   private String nome;
   private String telefone;
   private String cep;
   private Integer numero;
   private String complemento;
   private String senha;

    public Cliente() {
    }
   
    public Cliente(String cpf, String email, String nome, String telefone, String cep, Integer numero, String complemento, String senha) {
        this.cpf = cpf;
        this.email = email;
        this.nome = nome;
        this.telefone = telefone;
        this.cep = cep;
        this.numero = numero;
        this.complemento = complemento;
        this.senha = senha;
    }
   
   
    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCep() {
        return cep;
    }

    public Integer getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getSenha() {
        return senha;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    
   
}
