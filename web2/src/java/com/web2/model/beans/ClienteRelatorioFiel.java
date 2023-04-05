/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web2.model.beans;

import java.io.Serializable;


public class ClienteRelatorioFiel implements Serializable{
    private String cpf;
    private String nome;
    private String email;
    private String telefone;
    private String cep;
    private Integer num;
    private String complemento;
    private Integer qtdePedido;
    private Double receita;

    public ClienteRelatorioFiel() {
    }
 
    
    public ClienteRelatorioFiel(String cpf, String nome, String email, String telefone, String cep, Integer num, String complemento, Integer qtde, Double preco) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cep = cep;
        this.num = num;
        this.complemento = complemento;
        this.qtdePedido = qtde;
        this.receita = preco;
    }
    
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Integer getQtdePedido() {
        return qtdePedido;
    }

    public void setQtdePedido(Integer qtdePedido) {
        this.qtdePedido = qtdePedido;
    }

    public Double getReceita() {
        return receita;
    }

    public void setReceita(Double receita) {
        this.receita = receita;
    }
    
    
}
