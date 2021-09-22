# Criando uma solução de e-commerce com microsserviços em Java

Este projeto foi desenvolvido como parte das atividades do bootcamp Capgemini Fullstack Java and Angular. O projeto consiste de dois microsserviços (checkout e payment) implementado com Spring Cloud Streams e Apache Kafka.

## Implementação

O código gerado no Spring Initializr com a última versão estável do Spring Boot (2.5.4), do Spring Cloud Stream (3.1.3) e do Spring Cloud Stream Binder Kafka (3.1.3) não é compatível com o código apresentado ([https://github.com/hatanakadaniel/ecommerce-checkout-api](https://github.com/hatanakadaniel/ecommerce-checkout-api)) sendo necessário o downgrade para as versões apresentadas na aula.

No build.gradle ao invés das configurações geradas no Spring Initializr:
```Gradle
plugins {
    id 'org.springframework.boot' version '2.5.4'
}
ext {
	set('springCloudVersion', "2020.0.3")
}
dependencies {
	implementation 'io.confluent:kafka-avro-serializer:5.5.0'
}
```
Foi usado:
```Gradle
plugins {
	id 'org.springframework.boot' version '2.3.1.RELEASE'
}
ext {
	set('springCloudVersion', "Hoxton.SR6")
}
dependencies {
	implementation group: 'io.confluent', name: 'kafka-avro-serializer', version: '5.5.0'
}
```


Para evitar o erro no final da aula, quando copiar o arquivo application.yml do checkout e colar em payment, não esqueça de alterar as seguintes linhas:

```YAML
bindings:
  checkout-created-input:
    destination: streaming.ecommerce.checkout.created
    contentType: application/*+avro
    group: ${spring.application.name}
    consumer:
      use-native-decoding: true //***** ESTA LINHA *****
  payment-paid-output:
    destination: streaming.ecommerce.payment.paid
    contentType: application/*+avro
    producer:
      use-native-encoding: true //***** E ESTA LINHA *****
```

Caso contrário recerá a seguinte mensagem de erro:

```
org.springframework.messaging.converter.MessageConversionException: Cannot convert from [org.apache.avro.generic.GenericData$Record] to [br.takejame.ecommerce.checkout.event.CheckoutCreatedEvent] for GenericMessage [payload=...
```


O projeto originalmente previa o acesso à API através do método POST e com dados enviados através do body em formato JSON. Porém a tag Form do HTML não envia em formato JSON e sim em application/x-www-form-urlencoded. Portanto foi necessária a seguinte alteração no CheckoutResource.java:

Ao invés de:
```java
public class CheckoutResource {
    
    private final CheckoutService checkoutService;

    @PostMapping("/")
    public ResponseEntity<CheckoutResponse> create(@RequestBody CheckoutRequest checkoutRequest){
		(...)
```

Foi usado:
```java
public class CheckoutResource {
    
    private final CheckoutService checkoutService;

    @PostMapping(path = "/", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<CheckoutResponse> create(CheckoutRequest checkoutRequest){
		(...)
```

## Uso

Para fazer uso dos microserviços é necessário subir o container no Docker. Na raiz do projeto execute:

```
docker-compose up -d
```

Em seguida execute as aplicações CheckoutApplication e PaymentApplication.

Mude para a pasta checkout-frontend e suba o frontend com o comando:

```
ng serve
```

Acesse o frontend através do endereço:

```
http://localhost:4200/
```

Preencha os dados e clique em Comprar. A execução bem sucedida dos microserviços retornará o checkoutCode.