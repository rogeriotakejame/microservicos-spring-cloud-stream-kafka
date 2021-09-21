package br.takejame.ecommerce.checkout.resource.checkout;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutResponse implements Serializable{
    
    private String code;
    
}
