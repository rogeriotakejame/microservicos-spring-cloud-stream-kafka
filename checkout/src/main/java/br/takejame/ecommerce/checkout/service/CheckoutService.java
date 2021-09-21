package br.takejame.ecommerce.checkout.service;

import java.util.Optional;

import br.takejame.ecommerce.checkout.entity.CheckoutEntity;
import br.takejame.ecommerce.checkout.resource.checkout.CheckoutRequest;

public interface CheckoutService {
    
    Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest);

}
