# SD API Gateway

## Descrição

O **SD API Gateway** é o ponto central de entrada para o Sistema de Delivery de Comida. Sua principal responsabilidade é receber as requisições dos clientes e encaminhá-las para os microsserviços apropriados.

O gateway atua como uma camada de abstração entre os consumidores da API e os microsserviços internos, simplificando o acesso aos serviços do sistema.

---

## Arquitetura

O projeto segue uma arquitetura baseada em microsserviços utilizando Service Discovery através do Eureka.

```text
Cliente
   │
   ▼
SD API Gateway
   │
   ├── SD-API-PEDIDO
   ├── MICROSSERVICO-USUARIOS
   ├── SD-API-ENTREGA
   └── SD-NOTIFICACAO
```

O Gateway consulta o Eureka para descobrir dinamicamente os endereços dos microsserviços.

---

## Tecnologias Utilizadas

* Java 21
* Spring Boot 4
* Spring Web MVC
* Spring Cloud Eureka Client
* RestClient
* Maven

---

# Service Discovery (Eureka)

O projeto utiliza o Eureka Server para descoberta de serviços.

Ao invés de utilizar endereços fixos, o Gateway consulta o Eureka para localizar as instâncias disponíveis.

Exemplo:

```java
List<ServiceInstance> instances =
        discoveryClient.getInstances("SD-API-PEDIDO");
```

Após localizar a instância, o Gateway monta dinamicamente a URL do serviço:

```java
String.format(
        "http://%s:%d/pedidos",
        instance.getHost(),
        instance.getPort()
);
```

---

# Comunicação Remota (RPC)

O Gateway utiliza comunicação síncrona baseada em HTTP REST.

Fluxo:

```text
Cliente
   │
   ▼
Gateway
   │
   ▼
Microsserviço
```

O Gateway realiza chamadas remotas utilizando o componente RestClient do Spring.

Exemplo:

```java
return restClient.get()
        .uri(url)
        .retrieve()
        .body(UsuarioResumoDTO.class);
```

---

# Comunicação gRPC

O Gateway não realiza comunicação gRPC diretamente.

A comunicação gRPC é realizada internamente entre os microsserviços do sistema, principalmente entre:

* Serviço de Pedido
* Serviço de Pagamento

O Gateway apenas encaminha as requisições para os serviços responsáveis.

---

# Mensageria e Eventos

O Gateway não publica nem consome eventos diretamente.

A comunicação assíncrona utilizando RabbitMQ ocorre entre os microsserviços internos da arquitetura.

Exemplos:

* Atualização de status de pedidos
* Processamento de notificações
* Eventos de entrega
* Integração entre serviços

O Gateway atua apenas como ponto de entrada para as operações REST.

---

# Estrutura do Projeto

```text
src
└── main
    ├── controller
    │   ├── UsuarioController
    │   ├── PedidoController
    │   ├── EntregaController
    │   └── NotificacaoController
    │
    ├── service
    │   ├── UsuarioClient
    │   ├── PedidoClient
    │   ├── EntregaClient
    │   └── NotificacaoClient
    │
    ├── DTO
    ├── config
    └── model
```

---

# Controllers

## UsuarioController

Responsável por encaminhar operações relacionadas aos usuários.

### Endpoints

| Método | Endpoint              |
| ------ | --------------------- |
| POST   | /usuarios             |
| GET    | /usuarios             |
| GET    | /usuarios/{id}        |
| PUT    | /usuarios/{id}        |
| DELETE | /usuarios/{id}        |
| POST   | /usuarios/login       |
| GET    | /usuarios/{id}/resumo |

---

## PedidoController

Responsável pelas operações de pedidos.

### Endpoints

| Método | Endpoint                        |
| ------ | ------------------------------- |
| POST   | /pedidos                        |
| GET    | /pedidos                        |
| GET    | /pedidos/{id}                   |
| PUT    | /pedidos/{id}                   |
| DELETE | /pedidos/{id}                   |
| PUT    | /pedidos/{id}/pagar             |
| PUT    | /pedidos/{id}/estornar          |
| PUT    | /pedidos/{id}/iniciar-preparo   |
| PUT    | /pedidos/{id}/finalizar-preparo |
| GET    | /pedidos/pagamentos/saldo       |
| GET    | /pedidos/pagamentos/transacoes  |

---

## EntregaController

Responsável pelas operações de entrega.

### Endpoints

| Método | Endpoint                          |
| ------ | --------------------------------- |
| POST   | /entregas                         |
| GET    | /entregas                         |
| GET    | /entregas/{id}                    |
| PUT    | /entregas/{id}/status             |
| PUT    | /entregas/{id}/confirmar          |
| GET    | /entregas/em-entrega/lista        |
| GET    | /entregas/em-entrega/estatisticas |
| GET    | /entregas/{id}/em-entrega         |
| POST   | /entregas/em-entrega/sincronizar  |

---

## NotificacaoController

Responsável pelas consultas e gerenciamento de notificações.

### Endpoints

| Método | Endpoint                                     |
| ------ | -------------------------------------------- |
| GET    | /api/v1/notifications                        |
| GET    | /api/v1/notifications/{id}                   |
| GET    | /api/v1/notifications/order/{orderId}        |
| GET    | /api/v1/notifications/customer/{email}       |
| GET    | /api/v1/notifications/status/{status}        |
| GET    | /api/v1/notifications/history                |
| GET    | /api/v1/notifications/stats/pending          |
| GET    | /api/v1/notifications/stats/customer/{email} |
| PUT    | /api/v1/notifications/{id}/mark-as-sent      |
| PUT    | /api/v1/notifications/{id}/mark-as-failed    |

---

# Services

## UsuarioClient

Cliente responsável por consumir o microsserviço de usuários através do Eureka.

Funções principais:

* Cadastro de usuários
* Login
* Consulta por ID
* Atualização
* Exclusão
* Resumo do usuário

---

## PedidoClient

Cliente responsável pela comunicação com o microsserviço de pedidos.

Funções principais:

* Criação de pedidos
* Pagamento
* Estorno
* Controle de preparo
* Consulta de saldo
* Consulta de transações

---

## EntregaClient

Cliente responsável pela comunicação com o microsserviço de entrega.

Funções principais:

* Cadastro de entregas
* Atualização de status
* Consulta de entregas
* Sincronização de pedidos em entrega

---

## NotificacaoClient

Cliente responsável pela comunicação com o microsserviço de notificações.

Funções principais:

* Consulta de notificações
* Estatísticas
* Histórico
* Atualização de status

---

# DTOs

O projeto utiliza DTOs para transferência de dados entre o Gateway e os microsserviços.

Principais DTOs:

### Usuários

* UsuarioCadastroDTO
* UsuarioResumoDTO
* LoginDTO

### Pedidos

* PedidoCreateDTO
* PedidoResponseDTO
* SaldoDTO
* ListaTransacoesDTO

### Entregas

* PedidoEntregaRequestDTO
* PedidoEntregaResponseDTO
* AtualizarStatusDTO

### Notificações

* NotificationResponseDto
* PageResponse

---

# Como Executar

## Pré-requisitos

* Java 21
* Maven
* Eureka Server em execução
* Microsserviços registrados no Eureka

## Executando

```bash
mvn clean install
mvn spring-boot:run
```

ou

```bash
java -jar target/sd-api-gateway-1.0.0.jar
```

---

# Responsabilidade do Gateway

O Gateway possui as seguintes responsabilidades:

* Centralizar o acesso aos microsserviços.
* Descobrir serviços através do Eureka.
* Encaminhar requisições REST.
* Simplificar a comunicação entre cliente e microsserviços.
* Reduzir o acoplamento entre clientes externos e serviços internos.
