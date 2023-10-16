# <h1>remittance-service-api</h1>

<p>API responsável por emular um serviço de remessa de forma simples.</p>

<h3><p>Stacks:</p></h3>
</br>
Docker
</br>
Java 17
</br>
SpringBoot
</br>
SpringCloud
</br>
AWS - provido via localStack: AWS DynamoDB
</br>
Docker
</br>
Lombok
</br>
MapStruct
</br>
Maven
</br>

<h4><p> Instruções para executar o projeto</p></h4>
</br>
1 - Abra o cmd, execute ipconfig, copie o endereço ipv4 do adaptador ethernet e 
altere o endereço dos recursos acessados no application.yml
</br>
2 - Na pasta raiz do projeto executar: mvn clean install
</br>
3 - Na pasta recursos-embedded, execute o start-local-stack.sh para subir
a infra provendo os serviços da aws.
</br>
4 - Na pasta raiz do projeto gere a imagem: docker build -t remittanceserviceapi .
</br>
5 - Crie/execute o container com a imagem do projeto: docker run --name remittance-service-api -p 8085:8085 remittanceserviceapi
</br>
6 - Import a collection "remittanceserviceapi.postman_collection.json" da raiz do projeto no postman e consuma os serviços.
</br>
7 - Acessar o banco: http://localhost:8001/
</br>
8 - Acessar todos os serviços provisionados na localstack: https://app.localstack.cloud
</br>
8.1 - Faça o login
</br>
8.2 - Click em "resources"
</br>
8.3 - Vai listar todos os serviços, o que provisionamos foi o DynamoDB
</br>
8.4 - Acesse o serviço provisionado e verifique os dados gerados pela aplicação
</br>
9 - Para executar os testes unitários, utilize sua ide de preferência e acesse o diretório src. Nele existe o diretório test, a partir dele você pode executar toda cadeia de testes unitários ou o nível abaixo que desejar até o nível mínimo acessando uma class específica.

<h4><p> Documentação </p></h4>

 1 - Todas as requisições a api tem o header "transactionId" como obrigatório. 
 É um UUID que é transformado no id do pedido de remessa e utilizado no tracing
 da requisição.
</br>
 2 - O payload de request da criação de remessa, tem o mínimo de campos necessários
 para a criação do pedido, assim, todos os campos são obrigatórios.
</br>
 3 - Para conhecer pagador(user), recebedor(user) e as contas(account) dos mesmos, 
 acesse o banco de dados seguindo as instruções para executar o projeto.
</br> 
 4 - São efetuadas validações físicas e de negócio.
</br>
    4.1 Todos os campos do contrato são obrigatórios.
</br>
 4.2 O pagador deve ser o owner da conta informada no pagador, bem como o 
recebedor deve ser owner da conta informada no recebedor.
 </br>
    4.3 Conta do pagador deve ter uma moeda diferente da conta do recebedor.
 </br>
 4.4 O documento informado é validado conforme quantidade de dígitos e seu tipo.
 </br>
 4.5 Os dados do pagador e recebedor são enriquecidos com base em seus 
 documentos, bem como validado se são owners das respectivas contas informadas.
 <br>
 4.6 O pagador deve ter saldo e limite para criação do pedido de remessa, 
 nela é feita uma reserva do saldo e limite. Em caso de falha do pedido de 
 remessa, as reserva são canceladas.
 </br>
 4.7 Em todas as transações são efetuadas requisições no serviço de cotação.
 Em virtude do serviço não cotar nos finais de semana, é considerado o dia 
 anterior imediato da semana em caso de solicitação de pedido de remessa nesse
 período.
</br>
4.8 A conversão é feita considerando a moeda da conta do pagador para a moeda
da conta do recebedor.
</br>
4.9 Em caso de duplicação do pedido de remessa, o pedido duplicado é rejeitado.
</>
4.10 Pagador, Recebedor e contas devem existir previamente ao pedido de remessa.
</br>
5 - A api emula CQRS e SAGA.
</br>
5.1 O verbo POST na collection(collection: criar pedido de remessa) acessa 
 o serviço exposto como command. Ele respeita a RFC7231 HTTP status code 201 
 e retorna o header "location" com o path para ser requisitado
 (collection: consultar pedido de remessa) o recurso criado.
</br>
5.2 O verbo GET(collection: consultar pedido de remessa) acessa o serviço
 exposto como query.
 </br>
 5.3 Dado que ocorra um erro no pedido de remessa e essa etapa ou anteriores
 necessitem ser desfeitas, a api possue um tratamento para efetuar esse
 desfazimento. Não se trata de um SAGA coreografado ou orquestrado e sim de
 uma emulação do SAGA.
<br>
6 - O Design de software DAT
<br>
6.1 O DAT Domain, Abstraction e Tech, é um design de software fundamentado em
teoria dos conjuntos e tem como pilares DDD e SOLID.