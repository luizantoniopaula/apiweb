
package com.senai.apiweb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="idCliente")
public class PessoaJuridica extends Cliente {
    
    @Column(length = 14, nullable = false, unique=true)
    private String cnpj;
    
    @Column(nullable = true)
    private String nomeFantasia;
    
    @Column(nullable = false)
    private String razaoSocial;
    
    @Column(nullable = true)
    private String representante;

    public String getCnpj() {
        return cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }
    
    
    
    
}
