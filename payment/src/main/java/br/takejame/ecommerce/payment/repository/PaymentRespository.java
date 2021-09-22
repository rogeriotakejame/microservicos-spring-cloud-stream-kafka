package br.takejame.ecommerce.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.takejame.ecommerce.payment.entity.PaymentEntity;

public interface PaymentRespository extends JpaRepository<PaymentEntity, Long> {
    
}
