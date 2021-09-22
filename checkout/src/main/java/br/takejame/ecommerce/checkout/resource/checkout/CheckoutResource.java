package br.takejame.ecommerce.checkout.resource.checkout;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.takejame.ecommerce.checkout.entity.CheckoutEntity;
import br.takejame.ecommerce.checkout.service.CheckoutService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/v1/checkout")
@RequiredArgsConstructor
public class CheckoutResource {
    
    private final CheckoutService checkoutService;

    @PostMapping(path = "/", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<CheckoutResponse> create(CheckoutRequest checkoutRequest){
        final CheckoutEntity checkoutEntity = checkoutService.create(checkoutRequest).orElseThrow();
        final CheckoutResponse checkoutResponse = CheckoutResponse.builder()
            .code(checkoutEntity.getCode())
            .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(checkoutResponse);
    }
}
