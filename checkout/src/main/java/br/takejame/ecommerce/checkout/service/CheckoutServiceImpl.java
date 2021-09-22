package br.takejame.ecommerce.checkout.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

import br.takejame.ecommerce.checkout.entity.CheckoutEntity;
import br.takejame.ecommerce.checkout.entity.CheckoutItemEntity;
import br.takejame.ecommerce.checkout.entity.ShippingEntity;
import br.takejame.ecommerce.checkout.event.CheckoutCreatedEvent;
import br.takejame.ecommerce.checkout.repository.CheckoutRepository;
import br.takejame.ecommerce.checkout.resource.checkout.CheckoutRequest;
import br.takejame.ecommerce.checkout.streaming.CheckoutCreatedSource;
import br.takejame.ecommerce.checkout.util.UUIDUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {
    
    private final CheckoutRepository checkoutRepository;
    private final CheckoutCreatedSource checkoutCreatedSource;
    private final UUIDUtil uuidUtil;


    @Override
    public Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest) {
        final CheckoutEntity checkoutEntity = CheckoutEntity.builder()
            .code(uuidUtil.createUUID().toString())
            .status(CheckoutEntity.Status.CREATED)
            .saveAddress(checkoutRequest.getSaveAddress())
            .saveInformation(checkoutRequest.getSaveInfo())
            .shipping(ShippingEntity.builder()
                .address(checkoutRequest.getAddress())
                .complement(checkoutRequest.getComplement())
                .country(checkoutRequest.getCountry())
                .state(checkoutRequest.getState())
                .cep(checkoutRequest.getCep())
                .build())
            .build();
        checkoutEntity.setItems(checkoutRequest.getProducts()
            .stream()
            .map(product -> CheckoutItemEntity.builder()
                .checkout(checkoutEntity)
                .product(product)
                .build())
            .collect(Collectors.toList()));
        final CheckoutEntity entity = checkoutRepository.save(checkoutEntity);
        final CheckoutCreatedEvent checkoutCreatedEvent = CheckoutCreatedEvent.newBuilder()
            .setCheckoutCode(entity.getCode())
            .setStatus(entity.getStatus().name())
            .build();
        checkoutCreatedSource.output().send(MessageBuilder.withPayload(checkoutCreatedEvent).build());
        return Optional.of(entity);
    }

    @Override
    public Optional<CheckoutEntity> updateStatus(String checkoutCode, CheckoutEntity.Status status) {
        final CheckoutEntity checkoutEntity = checkoutRepository.findByCode(checkoutCode).orElse(CheckoutEntity.builder().build());
        checkoutEntity.setStatus(CheckoutEntity.Status.APPROVED);
        return Optional.of(checkoutRepository.save(checkoutEntity));
    }
}
