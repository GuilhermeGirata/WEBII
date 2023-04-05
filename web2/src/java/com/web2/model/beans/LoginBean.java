/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web2.model.beans;

import java.io.Serializable;

/**
 *
 * @author jeffe
 */
public class LoginBean implements Serializable{
    
    private String papel;
    private String email;
    private String nome;
    private String cpfCliente;
    private Integer idFuncionario;

    public LoginBean() {
    }

    public LoginBean(String papel, String email, String nome, Integer idFuncionario) {
        this.papel = papel;
        this.email = email;
        this.nome = nome;
        this.idFuncionario = idFuncionario;
    }

    public LoginBean(String papel, String email, String nome, String cpfCliente) {
        this.papel = papel;
        this.email = email;
        this.nome = nome;
        this.cpfCliente = cpfCliente;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }
    
}
