
package com.senai.apiweb.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import static jakarta.persistence.DiscriminatorType.STRING;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Table(name = "clientes")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="DISC", discriminatorType=STRING, length=20)
public class Cliente implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long IdCliente;
    
    @Column(nullable = true)
    private Long celular;
    
    @Column(nullable = true)
    private Long fone;
    
    @Column(nullable = true)
    private String email;
    
    @OneToMany(mappedBy="cliente", fetch=FetchType.LAZY, orphanRemoval = true,cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Endereco> endereco; // = new ArrayList<Endereco>();    

    public void setIdCliente(Long IdCliente) {
        this.IdCliente = IdCliente;
    }

    public void setCelular(Long celular) {
        this.celular = celular;
    }

    public void setFone(Long fone) {
        this.fone = fone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEndereco(Set<Endereco> endereco) {
        this.endereco = endereco;
    }

    public Long getIdCliente() {
        return IdCliente;
    }

    public Long getCelular() {
        return celular;
    }

    public Long getFone() {
        return fone;
    }

    public String getEmail() {
        return email;
    }

    public Set<Endereco> getEndereco() {
        return endereco;
    }

    
     
}
