package br.takejame.ecommerce.checkout.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

import br.takejame.ecommerce.checkout.streaming.CheckoutCreatedSource;
import br.takejame.ecommerce.checkout.streaming.PaymentPaidSink;

@Configuration
@EnableBinding(value = {
        CheckoutCreatedSource.class,
        PaymentPaidSink.class
})
public class StreamingConfig {
    
}
