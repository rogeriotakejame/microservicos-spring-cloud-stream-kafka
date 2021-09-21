package br.takejame.ecommerce.payment.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

import br.takejame.ecommerce.payment.streaming.CheckoutProcessor;

@Configuration
@EnableBinding(CheckoutProcessor.class)
public class StreamingConfig {
    
}
