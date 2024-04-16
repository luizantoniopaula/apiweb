
package com.senai.apiweb.controller;


import static com.senai.apiweb.service.StringToUTF8.strUTF8;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ManipuladorExcecoes {
    
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {
        
        ProblemDetail errorDetail = null;
        boolean erroOk = false;
        
        if (exception instanceof NoSuchElementException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), exception.getMessage());
            errorDetail.setProperty(strUTF8("Descrição"), "Usuário não encontrado!");
            erroOk = true;
        }
        if (exception instanceof HttpRequestMethodNotSupportedException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), exception.getMessage());
            errorDetail.setProperty(strUTF8("Descrição"), "Método HTTP de consulta Incorreto!");
            erroOk = true;
        }
        if (exception instanceof NoResourceFoundException ||
            exception instanceof MethodArgumentTypeMismatchException ) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), exception.getMessage());
            errorDetail.setProperty(strUTF8("Descrição"), "Chamada para API Incoreta verifique url e parâmetros!");
            erroOk = true;
        }
        
        exception.printStackTrace();   // Só ative se necessário...
        System.out.println("Encontrou erro: " + erroOk + "  " + exception.getMessage());
        return errorDetail;
        
    }
        
    @ExceptionHandler(value = AccessDeniedException.class)
    public void accessDeniedExceptionHandler(Exception e) {
         throw new AccessDeniedException(e.getMessage());
    }
 
}