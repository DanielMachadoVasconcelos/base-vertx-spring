package br.ead.home.configurations;

import io.vertx.rxjava3.core.Vertx;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VertxConfiguration {

    @Bean
    public Vertx createVertex(){
        return Vertx.vertx();
    }
}
