package br.ead.home.configurations;

import io.vertx.core.Vertx;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;

@Configuration
public class VertxConfiguration {

    @Bean
    public Vertx createVertex(){
        return Vertx.vertx();
    }
}
