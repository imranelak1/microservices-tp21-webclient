package com.example.client;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import com.example.client.entities.Client;
import com.example.client.repositories.ClientRepository;
@SpringBootApplication @EnableDiscoveryClient
public class ClientServiceApplication {
    public static void main(String[] args) { SpringApplication.run(ClientServiceApplication.class,args); }
    @Bean CommandLineRunner seed(ClientRepository repository) {
        return args -> { if(repository.count()==0) {
            repository.save(new Client(null,"Rabab SELIMANI",23f));
            repository.save(new Client(null,"Amal RAMI",22f));
            repository.save(new Client(null,"Samir SAFI",22f));
        }};
    }
}
