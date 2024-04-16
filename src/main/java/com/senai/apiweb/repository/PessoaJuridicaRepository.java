package com.senai.apiweb.repository;

import com.senai.apiweb.entity.PessoaJuridica;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {
    
      
        @Query(value="select p.id_cliente, p.cnpj, p.razao_social, p.nome_fantasia, q.fone, q.email from pessoa_juridica p, clientes q where p.id_cliente = q.id_cliente order by p.razao_social ASC limit :paginas,:regPaginas", nativeQuery=true)
        List<PessoaJuridicaLista> findAllOrderByRazaoSocial(@Param("paginas") Integer paginas, @Param("regPaginas")int regPaginas);
        
        @Query(value="select p.id_cliente, p.cnpj, p.razao_social, p.nome_fantasia, q.fone, q.email from pessoa_juridica p, clientes q where p.id_cliente = q.id_cliente order by p.nome_fantasia ASC limit :paginas,:regPaginas", nativeQuery=true)
        List<PessoaJuridicaLista> findAllOrderByNomeFantasia(@Param("paginas") Integer paginas, @Param("regPaginas")int regPaginas);

        @Query(value="select p.id_cliente, p.cnpj, p.razao_social, p.nome_fantasia, q.fone, q.email from pessoa_juridica p, clientes q where p.id_cliente = q.id_cliente order by p.cnpj ASC limit :paginas,:regPaginas", nativeQuery=true)
        List<PessoaJuridicaLista> findAllOrderByCnpj(@Param("paginas") Integer paginas, @Param("regPaginas")int regPaginas);
        
        PessoaJuridica findByCnpj(String cnpj);

}

