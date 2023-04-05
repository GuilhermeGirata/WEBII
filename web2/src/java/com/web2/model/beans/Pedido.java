
package com.web2.model.beans;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Pedido implements Serializable{
    private Integer id;
    private String cpf;
    private Double preco;
    private String status;
    private LocalDateTime dataPrazo;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataPagamento;
    private Integer dataDifDias;
    
    private String nomeCliente;
    private String cepCliente;
    private Integer numCliente;
    private String compCliente;
    
    private Endereco endereco;
    
    private ArrayList<Roupa> roupas = new ArrayList<>();

    public Pedido() {
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    
    public String getCompCliente() {
        return compCliente;
    }

    public void setCompCliente(String compCliente) {
        this.compCliente = compCliente;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getCepCliente() {
        return cepCliente;
    }

    public void setCepCliente(String cepCliente) {
        this.cepCliente = cepCliente;
    }

    public Integer getNumCliente() {
        return numCliente;
    }

    public void setNumCliente(Integer numCliente) {
        this.numCliente = numCliente;
    }


    
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getDataDifDias() {
        return dataDifDias;
    }

    public void setDataDifDias(Integer dataDifDias) {
        this.dataDifDias = dataDifDias;
    }
    

    public void setPreco(Double preco) {
        this.preco = preco;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public void setDataPrazo(LocalDateTime dataPrazo) {
        this.dataPrazo = dataPrazo;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Integer getId() {
        return id;
    }
    
    public String getCpf() {
        return cpf;
    }

    public Double getPreco() {
        if(preco == null){
            return 0.0;
        }
        return preco;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getDataPrazo() {
        return dataPrazo;
    }
    
    public String getDataPrazoString(){
        return dataPrazo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public String getDataAberturaString(){
        return dataAbertura.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
    
    public String getDataPagamentoString(){
        return dataPagamento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    
    public ArrayList<Roupa> getRoupas() {
        return roupas;
    }

    public void setRoupas(ArrayList<Roupa> roupas) {
        this.roupas = roupas;
    }

    public void addRoupa(Roupa rp){
        if(rp.getNome() != null){
            this.roupas.add(rp);
        }
    }
    
    public void setDataAberturaFromSqlDate(java.sql.Date dt){
        if(dt == null) return;
        this.dataAbertura = Instant.ofEpochMilli(dt.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    
    public void setDataPrazoFromSqlDate(java.sql.Date dt){
        if(dt == null) return;
        this.dataPrazo = Instant.ofEpochMilli(dt.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    
    public void setDataPagamentoFromSqlDate(java.sql.Date dt){
        if(dt == null) return;
        this.dataPagamento = Instant.ofEpochMilli(dt.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    
    public long getDataPrazoInMili(){
     return dataPrazo.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }   
    
    public long getDataAberturaInMili(){
     return dataAbertura.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }    
}
