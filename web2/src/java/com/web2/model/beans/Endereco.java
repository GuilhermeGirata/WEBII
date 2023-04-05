
package com.web2.model.beans;

import java.io.Serializable;


public class Endereco implements Serializable{
    
    private String Estado;
    private String cidade;
    private String cep;
    private String rua;

    @Override
    public String toString() {
        return "Estado " + getEstado() + ", Cidade " + getCidade() + ", Rua " + getRua();
    }

    public Endereco() {
    }

    public Endereco(String estado) {
        this.Estado = estado;
    }
    
    public Endereco(String Estado, String cidade, String cep, String rua) {
        this.Estado = Estado;
        this.cidade = cidade;
        this.cep = cep;
        this.rua = rua;
    }

    public String getEstado() {
        if(Estado == null) return "Desconhecido";
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public String getCidade() {
        if(cidade == null) return "Desconhecida";
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        if(rua == null) return "Desconhecida";
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }
    
    
}
