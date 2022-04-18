package br.ead.home.configurations;

import io.vertx.pgclient.PgConnectOptions;
import io.vertx.rxjava3.pgclient.PgPool;
import io.vertx.rxjava3.sqlclient.SqlClient;
import io.vertx.sqlclient.PoolOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReactiveDatabaseConfiguration {

    @Bean
    public PgConnectOptions createPgConnectOptions() {
        return new PgConnectOptions()
                .setPort(5432)
                .setHost("localhost")
                .setDatabase("broker")
                .setUser("username")
                .setPassword("password");
    }

    @Bean
    public PoolOptions createPoolOptions() {
        return new PoolOptions()
                .setMaxSize(5);
    }

    @Bean
    public SqlClient createSqlClient(PgConnectOptions connectOptions, PoolOptions poolOptions) {
        return PgPool.client(connectOptions, poolOptions);
    }
}
