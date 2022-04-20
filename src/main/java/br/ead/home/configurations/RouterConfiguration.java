package br.ead.home.configurations;

import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.rxjava3.core.Vertx;
import io.vertx.rxjava3.ext.web.Router;
import io.vertx.rxjava3.ext.web.handler.BodyHandler;
import io.vertx.rxjava3.ext.web.handler.ErrorHandler;
import io.vertx.rxjava3.ext.web.handler.LoggerHandler;
import io.vertx.rxjava3.ext.web.handler.TimeoutHandler;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RouterConfiguration {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Router createRouter(Vertx vertx){
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.route().handler(LoggerHandler.create(LoggerFormat.DEFAULT));
        router.route().handler(TimeoutHandler.create(100));
        router.route().failureHandler(ErrorHandler.create(vertx));

        return router;
    }
}
