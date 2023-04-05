/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.web2.model.beans;

import java.io.Serializable;

/**
 *
 * @author jeffe
 */
public class Roupa implements Serializable{

    private Integer id;
    private String nome;
    private Double preco;
    private Integer limiteDias;
    private Integer qtde;

    public Roupa() {
    }

    public Roupa(Integer id, Integer qtde) {
        this.id = id;
        this.qtde = qtde;
    }

    public Roupa(Integer id,String nome, Double preco, Integer limiteDias) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.limiteDias = limiteDias;
    }

    public Roupa(String nome, Double preco, Integer limiteDias) {
        this.nome = nome;
        this.preco = preco;
        this.limiteDias = limiteDias;
    }
    
    
    
    
    
    
    public Integer getId(){
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getLimiteDias() {
        return limiteDias;
    }

    public void setLimiteDias(Integer limiteDias) {
        this.limiteDias = limiteDias;
    }

    public Integer getQtde() {
        return qtde;
    }

    public void setQtde(Integer qtde) {
        this.qtde = qtde;
    }
    
    public Double getPrecoQtde(){
        if(preco == null || qtde == null){
            return null;
        }
        return(preco*qtde);
    }
    
}
