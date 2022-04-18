package br.ead.home.configurations;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Bean
    public DataSource createDataSource() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerNames(new String[]{"localhost"});
        ds.setDatabaseName("broker");
        ds.setUser("username");
        ds.setPassword("password");
        return ds;
    }

    @Bean
    public Flyway createFlyway(DataSource dataSource){
        return  Flyway.configure()
                .dataSource(dataSource)
                .schemas("broker")
                .defaultSchema("broker")
                .load();
    }
}
