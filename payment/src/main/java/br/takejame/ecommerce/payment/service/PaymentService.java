package br.takejame.ecommerce.payment.service;

import java.util.Optional;

import br.takejame.ecommerce.checkout.event.CheckoutCreatedEvent;
import br.takejame.ecommerce.payment.entity.PaymentEntity;

public interface PaymentService {
    
    Optional<PaymentEntity> create(CheckoutCreatedEvent checkoutCreatedEvent);

}
