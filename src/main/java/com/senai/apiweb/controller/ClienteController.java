
package com.senai.apiweb.controller;

import com.senai.apiweb.service.Mensagem;
import com.senai.apiweb.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class ClienteController {
 
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private Mensagem mensagem;
    
   
    @Operation(summary = "Consulta Cliente PF/PJ por ID",description = "Retorna Cliente PF/PJ por ID)")
    @CrossOrigin(origins = "*")
    @GetMapping("/cliente/{id}")
    public ResponseEntity<Object> consultarCliente(@PathVariable(value = "id") long id)
    {
        mensagem = (Mensagem) clienteService.consultarCliente(id);
        if( mensagem.getObject() instanceof Object ) {
            return new ResponseEntity<>(mensagem.getObject(), HttpStatus.OK);
        } else {            
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        }
    }
    
    @Operation(summary = "Consulta Cliente PF por CPF",description = "Retorna Cliente PF por CPF)")
    @CrossOrigin(origins = "*")
    @GetMapping("/clientepf/{cpf}")
    public ResponseEntity<Object> consultarClienteCpf(@PathVariable(value = "cpf") String cpf)
    {
        mensagem = (Mensagem) clienteService.consultarClienteCpf(cpf);
        if( mensagem.getObject() instanceof Object ) {
            return new ResponseEntity<>(mensagem.getObject(), HttpStatus.OK);
        } else {            
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Consulta Cliente PJ por CNPJ",description = "Retorna Cliente PJ por CNPJ)")
    @CrossOrigin(origins = "*")
    @GetMapping("/clientepj/{cnpj}")
    public ResponseEntity<Object> consultarClienteCnpj(@PathVariable(value = "cnpj") String cnpj)
    {
        mensagem = (Mensagem) clienteService.consultarClienteCnpj(cnpj);
        if( mensagem.getObject() instanceof Object ) {
            return new ResponseEntity<>(mensagem.getObject(), HttpStatus.OK);
        } else {            
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        }
    }

    
    @Operation(summary = "Lista de Clientes Pessoas Físicas classificados por nome ou cpf e paginado",
               description = "Retorna Lista de Clientes Pessoas Físicas classificados por nome ou cpf por Páginas")
    @CrossOrigin(origins = "*")
    @GetMapping("/clientepf/lista/{ordem}/{pagina}")
    public ResponseEntity<Object> consultaClientesOrdenadosPF(@PathVariable(value = "ordem") String ordem,
                                                              @PathVariable(value = "pagina") Integer pagina) {
        if(pagina == null) { pagina = 1; }
        mensagem = (Mensagem) clienteService.listarClientesOrdenadosPF(ordem,pagina);
        if( mensagem.getObject() instanceof List list ){ 
            return new ResponseEntity<>(list,HttpStatus.OK); 
        } else {
            mensagem.setFuncao("Listar Clientes Pessoas Físicas ordenados");
            mensagem.setDescrição("Listar Clientes Pessoas Físicas ordenados por ("+ ordem + ") não encontrados!");
            mensagem.setObject(null);
            return new ResponseEntity<>(mensagem , HttpStatus.NOT_FOUND);            
        }
    }
    
    @Operation(summary = "Lista de Clientes Pessoas Jurídicas classificados por Nome Fantasia ou Razão Social paginado.",
               description = "Retorna Lista de Clientes Pessoas Jurídicas classificados por: Nome Fantasia/Razão Social/CNPJ por Páginas")
    @CrossOrigin(origins = "*")
    @GetMapping("/clientepj/lista/{ordem}/{pagina}")
    public ResponseEntity<Object> consultaClientesOrdenadosPJ(@PathVariable(value = "ordem") String ordem,
                                                              @PathVariable(value = "pagina") Integer pagina) {
        if(pagina == null) { pagina = 1; }
        mensagem = (Mensagem) clienteService.listarClientesOrdenadosPJ(ordem,pagina);
        if( mensagem.getObject() instanceof List list){
            return new ResponseEntity<>(list,HttpStatus.OK);
        } else {
            mensagem.setFuncao("Listar Clientes Pessoas Jurídicas ordenados");
            mensagem.setDescrição("Listar Clientes Pessoas Jurídicas ordenados por ("+ ordem + ") não encontrados!");
            mensagem.setObject(null);
            return new ResponseEntity<>(mensagem , HttpStatus.NOT_FOUND);            
        }
    }
    
    @Operation(summary = "Cadastra novo Cliente", description = "Cadastra novo Cliente")
    @CrossOrigin(origins = "*")
    @PostMapping("/cliente")
    public ResponseEntity<Object> novoCliente(@Valid @RequestBody Object obj) {
    	
        mensagem = (Mensagem) clienteService.incluirCliente(obj);
        if( !(mensagem.getObject() instanceof Long) ){
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(mensagem.getObject(), HttpStatus.OK);
        }
    }
    
    @Operation(summary = "Atualiza Cliente PF/PJ já cadastrado, por ID",
               description = "Atualiza Cliente PF/PJ já cadastrado, por ID")
    @CrossOrigin(origins = "*")
    @PutMapping("/cliente")
    public ResponseEntity<Object> atualizarCliente( @Valid @RequestBody Object atualizaCli )
    {
        mensagem = (Mensagem) clienteService.atualizarCliente(atualizaCli);
        if( (mensagem.getObject() instanceof Long) ){
            return new ResponseEntity<>(mensagem.getObject(), HttpStatus.OK);
        }
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
    }
    
    @Operation(summary = "Exclui Cliente já cadastrado, por ID",
               description = "Exclui Cliente já cadastrado, por ID")
    @CrossOrigin(origins = "*")
    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<Object> excluirCliente(@PathVariable(value = "id") long id){
        
        mensagem = (Mensagem) clienteService.excluirCliente(id);
        if ( mensagem.getObject().equals(true) ){
            mensagem.setObject(null);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } else {
            mensagem.setObject(null);
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        }
    }


}
