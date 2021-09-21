package br.takejame.ecommerce.checkout.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.takejame.ecommerce.checkout.entity.CheckoutEntity;

public interface CheckoutRepository extends JpaRepository<CheckoutEntity, Long>{
    
    Optional<CheckoutEntity> findByCode(String code);
    
}
