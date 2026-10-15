package com.example.car;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import com.example.car.entities.Car;
import com.example.car.repositories.CarRepository;

@SpringBootApplication @EnableDiscoveryClient 
public class CarApplication {
    public static void main(String[] args) { SpringApplication.run(CarApplication.class,args); }
    @Bean CommandLineRunner seed(CarRepository repository) {
        return args -> { if(repository.count()==0) {
            repository.save(new Car(null,"Toyota","Yaris","A 25 333",1L,null));
            repository.save(new Car(null,"Renault","Megane","B 6 3456",1L,null));
            repository.save(new Car(null,"Peugeot","301","A 55 4444",2L,null));
        } };
    }
}
