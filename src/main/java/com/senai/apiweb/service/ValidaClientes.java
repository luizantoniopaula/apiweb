
package com.senai.apiweb.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.senai.apiweb.entity.PessoaFisica;
import com.senai.apiweb.entity.PessoaJuridica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidaClientes {
    
    @Autowired
    private Mensagem mensagem;

    private Long idCliente;
    
    public Mensagem validarClientes(Object cliente){
        
        Gson gson = new Gson();
        String cliJson = gson.toJson(cliente);
        JsonObject cliJS = (JsonObject) JsonParser.parseString(cliJson);
        if(cliJS.has("idCliente")){
            idCliente = cliJS.get("idCliente").getAsLong();
        }
        if(cliJS.has("cnpj")){
            String CNPJ = cliJS.get("cnpj").getAsString();
            if(CNPJ.length() != 14 || ! CNPJ.chars().allMatch(Character::isDigit)){
                mensagem.setFuncao("Valida CNPJ");
                mensagem.setDescrição("CNPJ Inválido, Tamanho/Letras incorreto.");
                mensagem.setObject(null);
                return mensagem;
            }
            if( !cliJS.has("razaoSocial") ){
                mensagem.setFuncao("Valida Razão Social");
                mensagem.setDescrição("Razão Social não informada.");
                mensagem.setObject(null);
                return mensagem;
            }
            PessoaJuridica pj = gson.fromJson(cliJson, PessoaJuridica.class);
            pj.setIdCliente(idCliente);
            mensagem.setFuncao("Valida Pessoa Jurídica");
            mensagem.setDescrição("Pessoa Jurídica corretamente informada.");
            mensagem.setObject(pj);
            return mensagem;
        } else if(cliJS.has("cpf")){
            String CPF = cliJS.get("cpf").getAsString();
            if(CPF.length() != 11 || ! CPF.chars().allMatch(Character::isDigit)){
                mensagem.setFuncao("Valida CPF");
                mensagem.setDescrição("CPF Inválido, Tamanho/Letras incorreto.");
                mensagem.setObject(null);
                return mensagem;
            }
            if( !cliJS.has("nome") ){
                mensagem.setFuncao("Valida Nome do Cliente");
                mensagem.setDescrição("Nome do Cliente não informado.");
                mensagem.setObject(null);
                return mensagem;
            }
            PessoaFisica pf = gson.fromJson(cliJson, PessoaFisica.class);
            pf.setIdCliente(idCliente);
            mensagem.setFuncao("Valida Pessoa Física");
            mensagem.setDescrição("Pessoa Física corretamente informada.");
            mensagem.setObject(pf);
            return mensagem;
        }
        mensagem.setFuncao("Validação de Clientes");
        mensagem.setDescrição("Cliente informado sem CNPJ/CPF ou nome/razão social");
        mensagem.setObject(null);
        return mensagem;
        
    }
    
}
