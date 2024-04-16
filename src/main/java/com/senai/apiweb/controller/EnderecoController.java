
package com.senai.apiweb.controller;

import com.senai.apiweb.entity.Endereco;
import com.senai.apiweb.service.Mensagem;
import com.senai.apiweb.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class EnderecoController {
    
    @Autowired
    private EnderecoService enderecoService;
    
    @Operation(summary = "Lista de Endereços",
               description = "Retorna Lista de Endereços")
    @CrossOrigin(origins = "*")
    @GetMapping("/endereco")
    public ResponseEntity<Object> listarEnderecos(){
         
        List<Endereco> end = enderecoService.listarEnderecos();
        if(end.isEmpty()){
            Mensagem erro = new Mensagem();
            erro.setFuncao("Consulta lista endereços");
            erro.setDescrição("Erro ao consultar lista endereços, não cadastrados/inexistentes!");
            return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(end, HttpStatus.OK);        
    }
    
    @Operation(summary = "Consulta Endereço por ID",
               description = "Retorna endereço único")
    @CrossOrigin(origins = "*")
    @GetMapping("/endereco/{id}")
    public ResponseEntity<Object> consultarEndereco(@PathVariable(value = "id") long id){
         
        Optional<Endereco> end = enderecoService.consultaEndereco(id);

        if(end.isPresent()){
            return new ResponseEntity<>(end.get(), HttpStatus.OK);
        } else {
            Mensagem erro = new Mensagem();
            erro.setFuncao("Consulta endereço por ID");
            erro.setDescrição("Erro ao consultar endereço, não cadastrado/inexistente!");
            return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
        }
    }
    
    @Operation(summary = "Exclui Endereço já cadastrado, por ID",
               description = "Exclui Endereço já cadastrado, por ID")
    @CrossOrigin(origins = "*")
    @DeleteMapping("/endereco/{id}")
    public ResponseEntity<Object> excluirEndereco(@PathVariable(value = "id") long id)
    {        
        if(enderecoService.excluirEndereco(id)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            Mensagem erro = new Mensagem();
            erro.setFuncao("Excluir endereço");
            erro.setDescrição("Erro ao excluir endereço, não cadastrado/inexistente!");
            return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
        }
    }
    
    @Operation(summary = "Cadastra novo Endereço", description = "Cadastra novo Endereço com IdCliente do Cliente")
    @PostMapping("/endereco/{clienteId}")
    public ResponseEntity<Long> cadastrarEndereco(@Valid @RequestBody Endereco end,@PathVariable(value = "clienteId") long id) {
    	Long IDnovo = enderecoService.incluirEndereco(end, id);
        return new ResponseEntity<>(IDnovo, HttpStatus.OK);
    }
    
    @Operation(summary = "Atualiza Endereço existente", description = "Atualiza Endereço existente")
    @PutMapping("/endereco")
    public ResponseEntity<Object> atualizarEndereco(@Valid @RequestBody Endereco end) {
    	if(enderecoService.atualizarEndereco(end)){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            Mensagem erro = new Mensagem();
            erro.setFuncao("Atualizar Endereço");
            erro.setDescrição("Erro ao atualizar endereço, não cadastrado/inexistente!");
            return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
        }
        
    }
    
    @Operation(summary = "Consulta um CEP, via API externa VIACEP", description = "Consultar um CEP, via api externa VIACEP")
    @GetMapping("/endereco/cep/{cep}")
    public ResponseEntity<Object> consultaCEP(@PathVariable(value = "cep") String cep) {
    	
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if( cep != null && cep.length() == 8){
            String cepCon = enderecoService.consultaApiCEP(cep);            
            return new ResponseEntity<> (cepCon,headers,HttpStatus.OK);
        } else {
            Gson gson = new Gson();
            Mensagem erro = new Mensagem();
            erro.setFuncao("Consultar CEP");
            erro.setDescrição("Erro ao consultar um CEP, verifique formato do CEP: xxxxxyyy");
            return new ResponseEntity<>(gson.toJson(erro),headers,HttpStatus.NOT_FOUND);
        }
        
    }
    
    
    
    
    
    
}
