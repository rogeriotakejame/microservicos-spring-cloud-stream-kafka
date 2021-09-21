package br.takejame.ecommerce.payment.listener;

import java.util.UUID;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import br.takejame.ecommerce.checkout.event.CheckoutCreatedEvent;
import br.takejame.ecommerce.payment.event.PaymentCreatedEvent;
import br.takejame.ecommerce.payment.streaming.CheckoutProcessor;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CheckoutCreatedListener {
    
    private final CheckoutProcessor checkoutProcessor;

    @StreamListener(CheckoutProcessor.INPUT)
    public void handler(CheckoutCreatedEvent event){
        //Processa pagamento gateway
        //salvar dados de pagamento
        //enviar o evento do pagamento processado
        final PaymentCreatedEvent paymentCreatedEvent = PaymentCreatedEvent.newBuilder()
            .setCheckoutCode(event.getCheckoutCode())
            .setCheckoutStatus(event.getStatus())
            .setPaymentCode(UUID.randomUUID().toString())
            .build();
        checkoutProcessor.output().send(MessageBuilder.withPayload(paymentCreatedEvent).build());
    }
}
