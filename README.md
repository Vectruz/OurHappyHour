# OurHappyHour

Autor: Vinícius Schadeck
Ano: 2017

OurHappyHour foi um projeto desenvolvido para cumprir o desafio solicitado pela SoftExpert.

Iniciar o projeto.

Configurando o banco de dados
1 - Inicie um postgresql pelo 'localhost:5432' usuário 'postgres' e senha 'password'.
2 - Crie um banco de dados chamado 'ohhbd'.
3 - Rode o arquivo 'ohhdb.sql' contido na pasta raiz deste repositório.
4 - Verifique se criou 4 ('user', 'event', 'event_user' e 'user_transaction_historic') tabelas no schema public do banco de dados.

Deploy do war no Tomcat
1 - Copie o arquivo OurHappyHour.war e cole dentro da pasta webapps do Tomcat.
2 - Inicie o Tomcat localhost na porta 8080

Acessando o projeto
1 - Com um navegador (Testando no Chrome 56.0.2924.87 e FireFox52) acesse 'http://localhost:8080/OurHappyHour' para acessa o projeto.

Sobre o projeto
O back-end executa uma API Restful utilizando Jsersey, para simplificar a criação dos end-points com annotations. O Jersey é mantido pela oracle como a referencia de implementação da especificação JSR-311 que define como é para utilizar WebService com Restful. Utilizei o postgres conforme solicitado e utilizei o driver para a conexão.
Para o front-end o projeto conta com angular e bootstrap, busquei uma aparencia mais limpa e simples para facil leitura. O angular torna o projeto singlepage e permite manipulação rápida do conteudo com tags proprias, o que ajuda na velocidade do desenvolvimento do front. Já o bootstrap cria uma estrutura de layout rápida permitindo a padronização de estilo com seu tema. Adicionei alguns components do angular para facilitar o desenvolvimento, o angular-route para manipulação das rotas, o angular-resource para conectar com o back-end, o angular-animate é necessário para criar efeitos visuais que nesse caso foram utilizados pelo angular-toastr o qual cria toasts de modo simples e com código elegante.
O front e o back-end se comunicam com objetos json. Foi implementado no back-end o Gson para facilitar nessa transição dos dados.
