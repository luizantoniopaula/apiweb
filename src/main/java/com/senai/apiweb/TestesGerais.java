
package com.senai.apiweb;

import java.util.Random;

public class TestesGerais {

    public static void main(String[] args) {
        
        Random random = new Random();
        // gerar um número aleatório de 0 a 3
        int number = random.nextInt(999);
        System.out.println("Número gerado....: " + number);
        
        String CPF = "5710975397A";
        if(CPF.chars().allMatch(Character::isDigit)){
            System.out.println("Somente números..");            
        } else {
            System.out.println("Há letras no meio...");
        }        
        
    }
    
}
