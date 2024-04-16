
package com.senai.apiweb.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Table(name = "enderecos")
@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long ID;
      
    @NotBlank(message = "O Logradouro deve ser informado")
    @Column(nullable = false)
    private String logradouro;
    
    @Column(nullable = true)
    private String numero;
    
    @Column(nullable = true)
    private String complemento;
    
    @NotBlank(message = "O Bairro deve ser informado")
    @Column(nullable = false)
    private String bairro;
    
    @NotBlank(message = "A Cidade deve ser informada")
    @Column(nullable = false)
    private String cidade;
    
    @NotBlank(message = "O Estado deve ser informado (2 caracteres/SIGLA)")
    @Column(nullable = false)
    private String estado;
    
    @Column(nullable = true)
    private Integer cep;
    
    @ManyToOne
    @JsonBackReference
    private Cliente cliente;

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Long getID() {
        return ID;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public Integer getCep() {
        return cep;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }
    
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }
    
    
    
    
    
    
    
}
