# Criando uma solução de e-commerce com microsserviços em Java

Este projeto foi desenvolvido como parte das atividades do bootcamp Capgemini Fullstack Java and Angular. O projeto consiste de dois microsserviços (checkout e payment) implementado com Spring Cloud Streams e Apache Kafka.

## Implementação

O código gerado no Spring Initializr com a última versão estável do Spring Boot (2.5.4), do Spring Cloud Stream (3.1.3) e do Spring Cloud Stream Binder Kafka (3.1.3) não é compatível com o código apresentado ([https://github.com/hatanakadaniel/ecommerce-checkout-api](https://github.com/hatanakadaniel/ecommerce-checkout-api)) sendo necessário o downgrade para as versões apresentadas na aula.

No build.gradle ao invés das configurações geradas no Spring Initializr:
```
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
```
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


## Uso

Para fazer uso dos microserviços é necessário subir o container no Docker. Na raiz do projeto execute:

```
docker-compose up -d
```

Em seguida execute as aplicações CheckoutApplication e PaymentApplication.

Quando todos os serviços estiverem iniciados, faça um POST request no seguinte endereço:

```
http://localhost:8085/v1/checkout/
```

e com body no seguinte formato:

```
{
	"firstName":"John",
	"lastName":"Doe",
	"email":"john.doe@email.com",
	"address":"Wall St.",
	"complement":"1",
	"country":"USA",
	"state":"NY",
	"cep":"12345",
	"saveAddress":true,
	"saveInfo":true,
	"paymentMethod":"credit card",
	"cardName":"Visa",
	"cardNumber":"1234 5678 9012 3456",
	"cardDate":"12/25",
	"cardCvv":"123",
	"products":[]
}
```

Era esperada a geração do checkout code e a resposta de PaymentApplication, porém assim como no término da apresentação do projeto, a aplicação falha com a seguinte mensagem de erro:

```
org.springframework.messaging.converter.MessageConversionException: Cannot convert from [org.apache.avro.generic.GenericData$Record] to [br.takejame.ecommerce.checkout.event.CheckoutCreatedEvent] for GenericMessage [payload=...
```

Os próximos commits buscarão implementar o código não apresentado em aula e disponível no repositório do instrutor. ([https://github.com/hatanakadaniel/ecommerce-checkout-api](https://github.com/hatanakadaniel/ecommerce-checkout-api) e [https://github.com/hatanakadaniel/ecommerce-payment-api](https://github.com/hatanakadaniel/ecommerce-payment-api))