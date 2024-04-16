
package com.senai.apiweb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="idCliente")
public class PessoaFisica extends Cliente {
    
    @Column(nullable = false)
    private String nome;
    
    @Column(length = 11, nullable = false, unique=true)
    private String cpf;
    
    @Column(nullable = true)
    private String datanas;
    
    @Column(nullable = true)
    private String filiacao;

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getDatanas() {
        return datanas;
    }

    public String getFiliacao() {
        return filiacao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setDatanas(String datanas) {
        this.datanas = datanas;
    }

    public void setFiliacao(String filiacao) {
        this.filiacao = filiacao;
    }
    
    
}
