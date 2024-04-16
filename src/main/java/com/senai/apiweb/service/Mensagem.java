
package com.senai.apiweb.service;

import org.springframework.stereotype.Service;

@Service
public class Mensagem {
    
    private String funcao;
    private String descrição;
    private Object object;

    public String getFuncao() {
        return funcao;
    }

    public String getDescricao() {
        return descrição;
    }
    public Object getObject(){
        return object;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public void setDescrição(String descrição) {
        this.descrição = descrição;
    }
    
    public void setObject(Object obj){
        this.object = obj;
    }
    
}
