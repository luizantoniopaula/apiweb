
package com.senai.apiweb.repository;

import com.senai.apiweb.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    
}
