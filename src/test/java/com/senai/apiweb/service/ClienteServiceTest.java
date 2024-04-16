
package com.senai.apiweb.service;

import com.senai.apiweb.entity.PessoaFisica;
import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClienteServiceTest {
    
    @Autowired
    private ClienteService cliSrv;
    
    private final Random random = new Random();
    
    public ClienteServiceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {

    }
    
    @AfterAll
    public static void tearDownClass() {
        System.out.println("\n############ Fim dos Testes ###################\n");
        System.out.println("\n");
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
    public String geraRandomico(){
        int numero = random.nextInt(999);
        String snumero = String.valueOf(numero);
        if(snumero.length() == 2){ snumero = "0" + snumero; }
        if(snumero.length() == 1){ snumero = "00" + snumero; }
        return snumero;
    }

    @Test
    @Order(1)
    public void testIncluirClienteSemCPF() {
        System.out.println("############ Início dos Testes ###################\n");
        PessoaFisica pf = new PessoaFisica();
        pf.setCelular(48999236630l);
        pf.setDatanas("26/09/1999");
        pf.setEmail("jamil@gmail.com");
        pf.setFiliacao("Jamil Father");
        pf.setFone(48999235587l);
        pf.setNome("Jamil Jamal ");
        Mensagem msg = (Mensagem) cliSrv.incluirCliente(pf);
        Long expResult = (Long) msg.getObject();
        Long result = null;
        System.out.println("Caso de Teste #1- Cadastro de novo Cliente Pessoa Física: sem CPF: " + msg.getDescricao());
        assertEquals(expResult, result, "#1 Erro: Cliente cadastrado sem CPF: " + msg.getDescricao());

    }
    @Test
    @Order(2)
    public void testIncluirClienteComCPF() {        
        PessoaFisica pf = new PessoaFisica();     
        String snum = this.geraRandomico();
        pf.setCelular(48999236630l);
        pf.setDatanas("26/09/1999");
        pf.setEmail("jamil." + snum + "@gmail.com");
        pf.setFiliacao("Jamil Father " + snum);
        pf.setFone(48999235587l);
        pf.setNome("Jamil Jamal " + snum);
        pf.setCpf("57109753" + snum);
        Long expResult = null;
        Mensagem msg = (Mensagem) cliSrv.incluirCliente(pf);
        Long result = (Long) msg.getObject();
        System.out.println("Caso de Teste #2- Cadastro de novo Cliente Pessoa Física: campos corretamente preenchidos: " + msg.getDescricao());
        assertNotEquals(expResult, result, "#2 Erro: Cliente PF não cadastrado: " + msg.getDescricao());

    }
    @Test
    @Order(3)
    public void testIncluirClienteComCPFTamanhoErrado() {
        PessoaFisica pf = new PessoaFisica();     
        String snum = this.geraRandomico();
        pf.setCelular(48999236630l);
        pf.setDatanas("26/09/1999");
        pf.setEmail("jamil." + snum + "@gmail.com");
        pf.setFiliacao("Jamil Father " + snum);
        pf.setFone(48999235587l);
        pf.setNome("Jamil Jamal " + snum);
        pf.setCpf("7109753" + snum); //cpf com 10 números
        Long expResult = null;
        Mensagem msg = (Mensagem) cliSrv.incluirCliente(pf);
        Long result = (Long) msg.getObject();
        System.out.println("Caso de Teste #3- Cadastro de novo Cliente Pessoa Física: com CPF com tamanho incoreto: " + msg.getDescricao());
        assertEquals(expResult, result, "#3 Erro: Cliente PF cadastrado com CPF incorreto: " + msg.getDescricao());

    }
    @Test
    @Order(4)
    public void testIncluirClienteComCPFTamanhoErradoAcima11() {
        PessoaFisica pf = new PessoaFisica();     
        String snum = this.geraRandomico();
        pf.setCelular(48999236630l);
        pf.setDatanas("26/09/1999");
        pf.setEmail("jamil." + snum + "@gmail.com");
        pf.setFiliacao("Jamil Father " + snum);
        pf.setFone(48999235587l);
        pf.setNome("Jamil Jamal " + snum);
        pf.setCpf("57109753972" + snum); //cpf com 14 números
        Long expResult = null;
        Mensagem msg = (Mensagem) cliSrv.incluirCliente(pf);
        Long result = (Long) msg.getObject();
        System.out.println("Caso de Teste #4- Cadastro de novo Cliente Pessoa Física: com CPF com 12 ou mais números: " + msg.getDescricao());
        assertEquals(expResult, result, "#4 Erro: Cliente PF cadastrado com CPF acima 11 números: " + msg.getDescricao());

    }
    @Test
    @Order(5)
    public void testIncluirClienteComCPFExistente() {        
        PessoaFisica pf = new PessoaFisica();     
        String snum = this.geraRandomico();
        pf.setCelular(48999236630l);
        pf.setDatanas("26/09/1999");
        pf.setEmail("jamil." + snum + "@gmail.com");
        pf.setFiliacao("Jamil Father " + snum);
        pf.setFone(48999235587l);
        pf.setNome("Jamil Jamal " + snum);
        pf.setCpf("40066805363"); //cpf já existente na base...
        Long expResult = null;
        Mensagem msg = (Mensagem) cliSrv.incluirCliente(pf);
        Long result = (Long) msg.getObject();
        System.out.println("Caso de Teste #5- Cadastro de novo Cliente Pessoa Física: com CPF já cadastrado: " + msg.getDescricao());
        assertEquals(expResult, result, "#5 Erro: Cliente PF cadastrado com CPF já existente: " + msg.getDescricao());
    }
    
    @Test
    @Order(6)
    public void testIncluirClienteComCPFNaoValido() {
        PessoaFisica pf = new PessoaFisica();     
        String snum = this.geraRandomico();
        pf.setCelular(48999236630l);
        pf.setDatanas("26/09/1999");
        pf.setEmail("jamil." + snum + "@gmail.com");
        pf.setFiliacao("Jamil Father " + snum);
        pf.setFone(48999235587l);
        pf.setNome("Jamil Jamal " + snum);
        pf.setCpf("ABCDEFGJ"+snum); //cpf 11 posições porém letras...
        Long expResult = null;
        Mensagem msg = (Mensagem) cliSrv.incluirCliente(pf);
        Long result = (Long) msg.getObject();
        System.out.println("Caso de Teste #6- Cadastro de novo Cliente Pessoa Física: com CPF incorreto/não válido: " + msg.getDescricao());
        assertEquals(expResult, result, "#6 Erro: Cliente PF cadastrado com CPF não válido, com letras: " + msg.getDescricao());

    }
    @Test
    @Order(7)
    public void testIncluirClienteSemNome() {
        PessoaFisica pf = new PessoaFisica();     
        String snum = this.geraRandomico();
        pf.setCelular(48999236630l);
        pf.setDatanas("26/09/1999");
        pf.setEmail("jamil." + snum + "@gmail.com");
        pf.setFiliacao("Jamil Father " + snum);
        pf.setFone(48999235587l);
        //pf.setNome("Jamil Jamal " + snum);  //Cliente Sem Nome pra cadastrar...
        pf.setCpf("57109753"+snum);
        Long expResult = null;
        Mensagem msg = (Mensagem) cliSrv.incluirCliente(pf);
        Long result = (Long) msg.getObject();
        System.out.println("Caso de Teste #7- Cadastro de novo Cliente Pessoa Física: sem nome: " + msg.getDescricao());
        assertEquals(expResult, result, "#7 Erro: Cliente PF cadastrado sem nome: " + msg.getDescricao());
    }
//
//    @Test
//    public void testConsultarClienteSemId() {
//        System.out.println("consultar Cliente sem IdCliente");
//        Long IdCliente = null;
//        Object expResult = null;
//        Object result = cliSrv.consultarCliente(IdCliente);
//        assertEquals(expResult, result, "Sem cliente PF");
//    }
//
//    @Test
//    public void testListarClientesOrdenadosPFNome() {
//        System.out.println("listar Clientes OrdenadosPF por nome");
//        String ordem = "nome";
//        Integer pagina = 1;
//        List<PessoaFisicaLista> expResult = null;
//        List<PessoaFisicaLista> result = cliSrv.listarClientesOrdenadosPF(ordem, pagina);
//        assertNotEquals(expResult, result, "Deve retornar lista ordenada por nome...");
//    }
//
//    @Test
//    public void testListarClientesOrdenadosPJ() {
//        System.out.println("listar Clientes Ordenados PJ por razaoSocial");
//        String ordem = "razaoSocial";
//        Integer pagina = 1;
//        List<PessoaJuridicaLista> expResult = null;
//        List<PessoaJuridicaLista> result = cliSrv.listarClientesOrdenadosPJ(ordem, pagina);
//        assertNotEquals(expResult, result,"Deve listar Clientes PJ por razaoSocial..." + result.size());
//    }
//
//    @Test
//    public void testExcluirCliente() {
//        System.out.println("excluir Cliente sem IdCliente");
//        Long IdCliente = null;
//        Boolean expResult = false;
//        Boolean result = cliSrv.excluirCliente(IdCliente);
//        assertEquals(expResult, result,"Não pode excluir sem IdCliente...");
//        
//    }
//
//    @Test
//    public void testAtualizarCliente() {
//        System.out.println("atualizar Cliente PF ou PJ");
//        Object cliente = null;
//        Long expResult = null;
//        Long result = cliSrv.atualizarCliente(cliente);
//        assertEquals(expResult, result,"Não pode atualizar sem IdCliente e Cliente PF ou PJ");
//    }
    
}
