
package com.senai.apiweb.service;

import com.senai.apiweb.entity.Cliente;
import com.senai.apiweb.entity.PessoaFisica;
import com.senai.apiweb.entity.PessoaJuridica;
import com.senai.apiweb.repository.ClienteRepository;
import com.senai.apiweb.repository.PessoaFisicaRepository;
import com.senai.apiweb.repository.PessoaJuridicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;
    
    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;
    
    @Autowired
    private Mensagem mensagem;
    
    @Autowired
    private ValidaClientes validaClientes;
    
    @Value("${apiweb.pagina.reg}")
    private int regPaginas;
    
    
    public Object incluirCliente(Object cli){
        
        mensagem = validaClientes.validarClientes(cli);
        if(mensagem.getObject() == null){
            return mensagem;
        }
        Object obj = mensagem.getObject();        
        switch (obj) {
            case PessoaFisica pf -> {
                mensagem.setFuncao("Incluir novo Cliente PF");
                if(pessoaFisicaRepository.findByCpf(pf.getCpf()) != null){
                    mensagem.setDescrição("Cliente com CPF já Cadastrado!");
                    mensagem.setObject(null);
                } else {
                    mensagem.setDescrição("Incluir novo Cliente PF");
                    mensagem.setObject(pessoaFisicaRepository.save(pf).getIdCliente());
                }
            }
            case PessoaJuridica pj -> {
                mensagem.setFuncao("Incluir novo Cliente PJ");
                if(pessoaJuridicaRepository.findByCnpj(pj.getCnpj()) != null){
                    mensagem.setDescrição("Cliente com CNPJ já Cadastrado!");
                    mensagem.setObject(null);
                } else {
                    mensagem.setDescrição("Incluir novo Cliente PJ");
                    mensagem.setObject(pessoaJuridicaRepository.save(pj).getIdCliente());
                }
            }
            default -> {
                mensagem.setFuncao("Incluir Cliente PJ/PF.");
                mensagem.setDescrição("Objeto Cliente PF ou PJ não definido.");
                mensagem.setObject(null);
            }            
        }
        return mensagem;
    }
  
        
    public Object consultarCliente(Long IdCliente) {
        
        if(IdCliente == null) { return null; }
        Optional<Cliente> cli = clienteRepository.findById(IdCliente);
        mensagem.setFuncao("Consulta Cliente por ID");
        if(cli.isPresent()){
            mensagem.setDescrição("Consulta Cliente PF/PJ com ID: " + IdCliente);
            mensagem.setObject(cli.get());
        } else {
            mensagem.setDescrição("Cliente não cadastrado no sistema. ID: " + IdCliente);
            mensagem.setObject(null);
        }
        return mensagem;
    }
    
    public Object consultarClienteCpf(String cpf) {
        
        if(cpf == null) { return null; }
        PessoaFisica pf = (PessoaFisica) pessoaFisicaRepository.findByCpf(cpf);
        mensagem.setFuncao("Consulta Cliente");
        if( pf != null ){
            mensagem.setDescrição("Consulta Cliente PF por CPF: " + cpf);
            mensagem.setObject(pf);
        } else {
            mensagem.setDescrição("Cliente não cadastrado no sistema com CPF: " + cpf);
            mensagem.setObject(null);
        }
        return mensagem;
    }
    
    public Object consultarClienteCnpj(String cnpj) {
        
        if(cnpj == null) { return null; }
        PessoaJuridica pj = pessoaJuridicaRepository.findByCnpj(cnpj);
        mensagem.setFuncao("Consulta Cliente");
        if( pj != null ){
            mensagem.setDescrição("Consulta Cliente PJ por CNPJ: " + cnpj);
            mensagem.setObject(pj);
        } else {
            mensagem.setDescrição("Cliente não cadastrado no sistema com CNPJ: " + cnpj);
            mensagem.setObject(null);
        }
        return mensagem;
    }
    
    public Object listarClientesOrdenadosPF(String ordem, Integer pagina){            
   
            pagina = (pagina -1)* regPaginas;
            mensagem.setFuncao("Consulta Clientes PF ordenados");
            switch(ordem){
                case("cpf") ->{
                    mensagem.setDescrição("Consulta Clientes PF ordenados por " + ordem);
                    mensagem.setObject(pessoaFisicaRepository.findAllOrderByCpf(pagina,regPaginas));                    
                }
                case("nome") ->{
                    mensagem.setDescrição("Consulta Clientes PF ordenados por " + ordem);
                    mensagem.setObject(pessoaFisicaRepository.findAllOrderByNome(pagina,regPaginas));
                }
                default ->{
                    mensagem.setDescrição("Consulta incorreta. Parâmetros aceitos: cpf ou nome");
                    mensagem.setObject(null);
                }
            }
            return mensagem;
    }
    
    public Object listarClientesOrdenadosPJ(String ordem, Integer pagina){
        
        pagina = (pagina -1) * regPaginas;
        mensagem.setFuncao("Consulta Clientes PJ ordenados");
        switch(ordem){
            case("nomefantasia") -> {
                mensagem.setDescrição("Consulta Clientes PJ ordenados por " + ordem);
                mensagem.setObject(pessoaJuridicaRepository.findAllOrderByNomeFantasia(pagina,regPaginas));
            }
            case("razaosocial") -> {
                mensagem.setDescrição("Consulta Clientes PJ ordenados por " + ordem);
                mensagem.setObject(pessoaJuridicaRepository.findAllOrderByRazaoSocial(pagina,regPaginas));
            }
            case("cnpj") -> {
                mensagem.setDescrição("Consulta Clientes PJ ordenados por " + ordem);
                mensagem.setObject(pessoaJuridicaRepository.findAllOrderByCnpj(pagina,regPaginas));
            }
            default -> {
                mensagem.setDescrição("Consulta incorreta. Parâmetros aceitos: cnpj, nomefantasia, razaosocial.");
                mensagem.setObject(null);
            }        
        }
        return mensagem;
    }
    
    public Object excluirCliente(Long IdCliente){
        
        mensagem.setFuncao("Excluir Cliente PF/PJ cadastrado.");
        try {
            clienteRepository.deleteById(IdCliente);
            mensagem.setDescrição("Cliente excluído com sucesso, ID: " + IdCliente);
            mensagem.setObject(true);
        } catch (Exception ex) {
            mensagem.setDescrição("Erro ao excluir Cliente com ID: " + IdCliente);
            mensagem.setObject(false);
        }
        return mensagem;
    }
    
    public Object atualizarCliente(Object cliente){
        
        mensagem = validaClientes.validarClientes(cliente);
        if(mensagem.getObject() == null){
            return mensagem;
        }
        Object obj = mensagem.getObject();
        if( obj instanceof PessoaFisica pf ){       
                
                PessoaFisica pfatual = pessoaFisicaRepository.getReferenceById(pf.getIdCliente());
                mensagem.setFuncao("Atualizar Cliente PF cadastrado.");
                
                if( pfatual.getIdCliente() == null){
                    mensagem.setDescrição("Cliente PF não Cadastrado!");
                    mensagem.setObject(null);
                } else {
                    mensagem.setDescrição("Atualizar Cliente PF com ID: " + pf.getIdCliente());
                    pfatual.setCelular(pf.getCelular());
                    pfatual.setCpf(pf.getCpf());
                    pfatual.setEmail(pf.getEmail());
                    pfatual.setFone(pf.getFone());
                    pfatual.setDatanas(pf.getDatanas());
                    pfatual.setFiliacao(pf.getFiliacao());
                    pfatual.setNome(pf.getNome());
                    mensagem.setObject(pessoaFisicaRepository.save(pfatual).getIdCliente());
                }
        }
        if( obj instanceof PessoaJuridica pj ){

                PessoaJuridica pjatual = pessoaJuridicaRepository.getReferenceById(pj.getIdCliente());
                mensagem.setFuncao("Atualizar Cliente PJ cadastrado.");                
                
                if( pjatual.getIdCliente() == null){
                    mensagem.setDescrição("Cliente PJ não Cadastrado!");
                    mensagem.setObject(null);
                } else {
                    mensagem.setDescrição("Atualizar Cliente PJ com ID: " + pj.getIdCliente());
                    pjatual.setCelular(pj.getCelular());
                    pjatual.setCnpj(pj.getCnpj());
                    pjatual.setEmail(pj.getEmail());
                    pjatual.setFone(pj.getFone());
                    pjatual.setNomeFantasia(pj.getNomeFantasia());
                    pjatual.setRazaoSocial(pj.getRazaoSocial());
                    pjatual.setRepresentante(pj.getRepresentante());
                    mensagem.setObject(pessoaJuridicaRepository.save(pjatual).getIdCliente());
                }
        } else {
                mensagem.setFuncao("Atualizar Cliente PJ/PF cadastrado.");
                mensagem.setDescrição("Objeto Cliente PF ou PJ não definido.");
                mensagem.setObject(null);
            }
        return mensagem;        
        }
    
}
