package br.takejame.ecommerce.checkout.listener;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import br.takejame.ecommerce.checkout.entity.CheckoutEntity;
import br.takejame.ecommerce.checkout.repository.CheckoutRepository;
import br.takejame.ecommerce.checkout.streaming.PaymentPaidSink;
import br.takejame.ecommerce.payment.event.PaymentCreatedEvent;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentPaidListener {

    private final CheckoutRepository checkoutRepository;

    @StreamListener(PaymentPaidSink.INPUT)
    public void handler(PaymentCreatedEvent event){
        final CheckoutEntity checkoutEntity = checkoutRepository.findByCode(event.getCheckoutCode().toString()).orElseThrow();
        checkoutEntity.setStatus(CheckoutEntity.Status.APPROVED);
        checkoutRepository.save(checkoutEntity);
    }
}
