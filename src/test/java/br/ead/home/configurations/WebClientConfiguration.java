package br.ead.home.configurations;

import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.rxjava3.core.Vertx;
import io.vertx.rxjava3.ext.web.client.WebClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("integration-test")
public class WebClientConfiguration {

    @Bean
    public WebClient createWebClient(Vertx vertx, WebClientOptions options){
        return WebClient.create(vertx, options);
    }

    @Bean
    public WebClientOptions createWebClientOptions(){
        return new WebClientOptions()
                .setKeepAlive(false)
                .setDefaultHost("localhost")
                .setDefaultPort(8080);
    }
}
