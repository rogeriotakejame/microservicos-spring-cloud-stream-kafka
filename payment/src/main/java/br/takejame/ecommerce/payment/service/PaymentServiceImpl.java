package br.takejame.ecommerce.payment.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.takejame.ecommerce.checkout.event.CheckoutCreatedEvent;
import br.takejame.ecommerce.payment.entity.PaymentEntity;
import br.takejame.ecommerce.payment.repository.PaymentRespository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
    
    private final PaymentRespository paymentRepository;
    
    @Override
    public Optional<PaymentEntity> create(CheckoutCreatedEvent checkoutCreatedEvent) {
        final PaymentEntity paymentEntity = PaymentEntity.builder()
                .checkoutCode(checkoutCreatedEvent.getCheckoutCode())
                .code(UUID.randomUUID().toString())
                .build();
        paymentRepository.save(paymentEntity);
        return Optional.of(paymentEntity);
    }

}
