package org.myproject.bmanager4.config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Neo4jConfig {
    @Value("${NEO4J_HOST}")
    private String host;

    @Value("${NEO4J_PORT}")
    private int port;

    @Value("${NEO4J_USERNAME}")
    private String user;

    @Value("${NEO4J_PASSWORD}")
    private String password;

    @Bean
    public Driver neo4jDriver() {
        String uri = "bolt://" + host + ":" + port;

        return GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }
}
