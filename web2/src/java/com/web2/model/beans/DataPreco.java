
package com.web2.model.beans;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;


public class DataPreco implements Serializable{
    private Double preco;
    private LocalDateTime data;

    public DataPreco() {
    }
    
    public void setDataPrazoFromSqlDate(java.sql.Date dt){
        this.data = Instant.ofEpochMilli(dt.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }


    
}
