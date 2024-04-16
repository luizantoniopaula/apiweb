
package com.senai.apiweb.repository;

import com.senai.apiweb.entity.PessoaFisica;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long>{
    

        PessoaFisica findByCpf(String cpf);
      
        @Query(value="select p.id_cliente, p.nome, p.cpf, q.celular, q.email from pessoa_fisica p, clientes q where p.id_cliente = q.id_cliente order by p.nome ASC limit :paginas,:regPaginas", nativeQuery=true)
        List<PessoaFisicaLista> findAllOrderByNome(@Param("paginas") Integer paginas,@Param("regPaginas")int regPaginas);

        @Query(value="select p.id_cliente, p.nome, p.cpf, q.celular, q.email from pessoa_fisica p, clientes q where p.id_cliente = q.id_cliente order by p.cpf ASC limit :paginas,:regPaginas", nativeQuery=true)
        List<PessoaFisicaLista> findAllOrderByCpf(@Param("paginas") Integer paginas,@Param("regPaginas")int regPaginas);
}
