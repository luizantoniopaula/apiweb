#           Projeto APIWEB

# Características

O projeto emprega  a tecnologia Java Spring Boot na construção de uma solução de cadastro de Clientes, gerando uma API no padrão MVC RestFull totalmente funcional.
O modelo de dados das Entidades foi desenvolvido em sala de aula, modelado e implementado na solução Spring, utilizando-se do JPA na persistência de dados, tendo-se o Mysql como gerenciador de banco de dados.
Demais APIs eventualmente foram aplicadas no projeto como o Swagger por exemplo, na geração de documentação das APIs, assim como outras bibliotecas, estando todas descritas no contexto.

# Objetivos

A principal fundamentação do projeto é o entendimento da tecnologia Spring Boot e a linguagem Java subjacente, bem como do modelo de programação orientado a objetos, assim como a tecnologia de persistência do JPA - Java Persistence API.
No projeto foi empregado o modelo de segmentação em camadas e responsabilidades das classes, refletindo uma solução MVC - Modelo, Visão e Controle, respeitando-se dentro do possível as orientações e definições da OOP - Programação Orientada a Objetos e as melhores práticas de programação.
Compondo o projeto, as classes JUnit dos serviços (services) também foram desenvolvidas e executadas, as quais permitiram a visualização de erros/bugs na programação, e também mostraram algumas limitações do JPA, quando da implementação do modelo de herança de classes na estratégia JOINED, pois nessa estratégia, há algumas restrições quando da utilização de consultas nativas com paginação, onde empregou-se solução programada em código para resolver o problema.
Também com objetivos didáticos, foi utilizada a tecnologia API DOC para documentação no modelo Swagger a qual gerou toda documentação das APIs do projeto em página própria, ativa e funcional, podendo ser utilizada também para testes e verificações das funcionalidades implementadas.

# Implementações

As seguintes implementações foram aplicadas na solução, na medida que o projeto foi evoluindo em sala de aula e também em home office, apresentando situações de mudança de uso nas APIs, principalmente devido a restrições do JPA, descobertas somente após testes e verificações na documentação.

# Entidades

Entidades com Herança Simples com abordagem Joined (Join) com uma tabela para cada entidade. A Estratégia Joined foi necessária para que atenda a relação de Cliente → Endereço, onde 1 cliente pode ter vários endereços.
Na abordagem Single Table teríamos muitos campos null como CPF para um cliente pessoa jurídica e CNPJ para clientes pessoa física, os quais por restrições de lógica de negócio, não podem ser nulos, mas até únicos (unique) em suas entidades.
Na abordagem Table Per Class, teríamos a mesma abordagem, mas não seria possível a relação de Muitos para Um (ManyToOne), a qual temos no modelo entre a entidade Cliente e Endereço.
Foram utilizadas também as técnicas de Projection e Native Queries nas interfaces JPA, por limitações no retorno seletivo de campos das entidades compostas, como exemplo: Pessoas Físicas e Pessoas Jurídicas, as quais possuem vários atributos, porém nas APIs para listagens de registros se faz necessário o retorno de somente alguns atributos significativos, solução essa possível com o uso das Native Queries, que são consultas nativas, na mesma sintaxe SQL e referências à objetos nativos do banco de dados e não das entidades JPA em Java.
Ao usar Native Queries, por limitação da API, a paginação no retorno das listagens teve que ser empregada via código, pois os objetos do tipo Pageable não funcionam neste tipo de consulta.
Do mesmo modo, tais métodos de consultas nativas nas classes do tipo Repository (repositório), tiveram seus retornos alterados para uma interface de métodos (Projection) contendo somente os Getters necessários para os atributos específicos de cada tipo de listagem (pessoas físicas ou pessoas jurídicas).

# Anotações (Annotations) utilizadas:

@Table(name = "clientes") → Define nome da tabela no banco de dados
@Entity  → Define uma Entidade para o JPA
@Inheritance(strategy = InheritanceType.JOINED) → Define a estratégia de herança como JOINED
@DiscriminatorColumn(name="DISC",
discriminatorType=STRING,
length=20) →Define o campo que discrimina no banco de dados os  tipos de entidades de cada registro (PF ou PJ)
@OneToMany(mappedBy="cliente", fetch=FetchType.LAZY,
orphanRemoval = true,
cascade = CascadeType.ALL) →Define o mapeamento de Um para Muitos, o modo de busca (LAZY) dos registros (endereco), o tipo de tratamento para remoção em cascata (ALL).
@JsonManagedReference → Define que o JsonManagedReference gerencia as referências de			objetos Json encadeados, evitando retorno em Loop de registros
Clientes e Endereços
private Set<Endereco> endereco; → Atributo que armazena a Lista de Endereços (objetos)
@PrimaryKeyJoinColumn(name="idCliente") → Define o Atributo que será Chave Primária nas
    classes filhas (PessoaFisica e PessoaJuridica)
@Id → Identifica qual atributo será utilizado como Identificador único no banco de dados.
@GeneratedValue(strategy = GenerationType.AUTO) → Tipo de geração dos Id
@Column(nullable = false) → Define que um atributo ou Coluna no banco de dados não pode ser nula
private Long ID → Atributo objeto das anotações acima 
@ManyToOne → Define o mapeamento Muitos (endereços) para um (Cliente)
@JsonBackReference → Define que a referência para Cliente (back), seja gerenciada pelo 
    Json, evitando-se o retorno em Loop de Endereços e Clientes
private Cliente cliente; → Atributo referência ao objeto cliente ( no banco de dados fica como 			    cliente_id_cliente, tudo definido pelo JPA)
Importante:
O JPA não irá mudar as configurações das Tabelas no banco de dados, caso existam registros gravados e que não atendam às mudanças em implementação.
Por exemplo, se um Atributo (coluna no banco de dados) for definido como Nullable=false, mas já existirem registros nulos nesse atributo, o JPA não configura a restrição. Essa regra vale também para qualquer outra restrição, como a unique (único).
Para resolver esse problema, deve-se primeiro excluir os registros que não atendam as restrições, ou então remover todos os registros e depois, recompilar o projeto e executar na IDE, onde então o JPA irá implementar todas as restrições e configurações das tabelas conforme o mapeamentos nas Entidades.

# Repositórios

Repositórios são Interfaces (classes abstratas) que fazem a relação direta entre a manipulação das Entidades e o banco de dados. As versões atuais da JPA permitem uma diversidade de formas para realizar persistências de objetos, gravando e recuperando dados com um mínimo de configuração necessária. A exemplo, nem mesmo a criação de tabelas é preciso fazer, pois basta ter o banco de dados criado e executar uma compilação e execução do projeto, que o JPA irá criar toda a estrutura de tabelas necessárias para atendimento ao modelo de Entidades definido no projeto.
Como já relatado, algumas inconsistências foram detectadas no JPA e precisaram ser resolvidas com alguma programação em java, que seguem:
JPA em consultas findAll retorna todos os atributos de uma Entidade, não permitindo a escolha de quais atributos desejamos. Para resolver esse problema, foi utilizado o conceito de Projections, no qual se utiliza de uma Interface contendo somente os métodos Get para os atributos que desejamos da Entidade e então usamos como Tipo de Objeto de retorno de uma Native Query. Segue exemplo utilizado no projeto:
@Query(value="select p.id_cliente, p.nome, p.cpf, … from pessoa_fisica p, clientes q where … nativeQuery=true)
List<PessoaFisicaLista> findAllOrderByCpf(@Param("paginas")...
Native Query não suporta a paginação por Pageable, então a solução é bem simples, basta parametrizar por parâmetros o comando “limit” do SQL, indicando a página, no primeiro parâmetro e o Offset, ou seja, o tamanho de registros por páginas no segundo. Veja exemplo:
@Query(value="select p.id_cliente, p.nome,... from … where …. order by p.cpf ASC limit :paginas,:regPaginas", nativeQuery=true)
List<PessoaFisicaLista> findAllOrderByCpf(@Param("paginas") Integer paginas,@Param("regPaginas")int regPaginas);
Utilização do conceito de QueryByExample, onde o JPA permite criar consultas específicas para o nosso projeto, atendendo nossos requisitos. Esse tipo de liberdade é uma grande vantagem do JPA, pois permite a flexibilidade necessária em projetos complexos, com vários tipos de Entidades, relacionamentos, heranças, composições, etc. Veja exemplo:
PessoaFisica findByCpf(String cpf);  → Não existe nativamente, é construída na execução.
List<PessoaFisicaLista> findAllOrderByCpf(@Param("paginas")... igual anterior.
Deve-se respeitar o formato do nome do Atributo no nome da função. Exemplo do CPF, no qual o atributo em banco é “cpf” mas no método passa a ser “Cpf”. (padrão do JPA)

# Anotações utilizadas nos Repositórios:

@Repository → Indica ao Spring Boot que a Interface é um Repositório e será gerenciado no Spring.
@Query → Indica que será usada uma Query Manual, podendo ser JPQL ou Native Query.
@Param → Utilizada para passagem de parâmetros para as Queries Manuais (@Query).


# Services

Services são classes de serviços e executam funções diversas. Pensando-se num modelo MVC, as classes de serviço são os Controladores, pois fazem de fato a ligação entre as entidades (entity ou modelo) e as classe de interface com os usuários (visão). Nesse caso típico do RestFull, não há interfaces com usuários, mas sim interfaces do tipo HTTP, com recebimento e devolução de objetos do tipo HTTPRequest, HTTPResponse, ResponseBody, RequestBody, etc. Quem então realiza o papel de Visão no modelo de API RestFull são as classes Controller.

As classes de serviço então se responsabilizam por tratar suas respectivas entidades. Assim a classe ClienteService administra as Entidades PessoaFisica e PessoaJuridica, implementando todos os métodos necessários para que as Entidades envolvidas possam realizar os requisitos de negócio do projeto.
Podem haver classes de serviço que atendem outras classes, ou seja, classes genéricas. Elas são criadas para atender requisitos de projeto, objetivando desacoplar as demais classes, mas mantendo-se a coesão entre elas. Exemplo dessas classes genéricas, é a classe ValidaClientes, a qual realiza a validação dos objetos do tipo Cliente, que são na verdade objetos do tipo PessoaFisica e PessoaJuridica, pois estes são objetos que herdam da classe Cliente.
Demais classes de serviço podem ser criadas conforme a necessidade. Um exemplo seria fazer mais alguns processos de refatoração do projeto, dividindo-se a classe ClienteService em duas ou mais classes, por exemplo, PessoaFisicaService e PessoaJuridicaService.
O refatoramento é um processo de melhoria no código e trata de várias questões que por ventura existam no código e que devem ser evitadas, reduzidas, ou mesmo eliminadas/trocadas por outras alternativas, desde que a linguagem permita. 
Exemplos de atividade de refatoração em códigos Java:
Eliminação/redução de IFs aninhados, convertendo em switch case ou mudando-se a lógica do código;
Eliminação/redução de métodos com objetivos semelhantes, podendo-se, por exemplo, implementar algum padrão de projeto (design pattern) que substitua a lógica envolvida;
Reduzir/evitar as comparações com nulos (null), procurando-se sempre utilizar os métodos nativos do Java que já fazem a comparação, retornando valores boolean e podendo ser utilizados dentro dos IFs. Exemplos:
object.equals(null)
string.contain(null);
Object.isNull(outroObjeto);
Optional<Object> obj  → if(obj.isPresent()) se verdadeiro obj não é nulo.
Dividindo-se classes com muito métodos em duas ou mais classes, reduzindo-se assim o acoplamento, como por exemplo, dividir a classe ClienteService em duas como PessoaFisicaService e PessoaJuridicaService. Isso vai facilitar a manutenção e não haverá perdas na coesão dessas classes, pois cooperam juntas em tempo real para tratar de seus objetos.

# Anotações utilizadas nas classes Service

@Service → Indica que a Classe é um Service e será controlada pelo Spring Boot.
@Autowired →Anotação de uso geral em atributos de Objetos de outras classes, permitindo acesso		 direto sem a necessidade de criação de objeto.
@Value("${url.viacep}") → Utilizado para atribuir um valor em um atributo, no exemplo, lê o valor de uma
propriedade denominada url.viacep registra no atributo anotado.

