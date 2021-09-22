package br.takejame.ecommerce.checkout.util;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UUIDUtil {
    
    public UUID createUUID(){
        return UUID.randomUUID();
    }
}
