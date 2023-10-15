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
6 - Import a collection no postman e consuma os serviços.
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
