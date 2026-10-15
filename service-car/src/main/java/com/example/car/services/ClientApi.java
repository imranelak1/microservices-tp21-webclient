package com.example.car.services;
import com.example.car.models.Client;
import org.springframework.stereotype.Service;
/** Communication synchrone webclient : résolution et transport restent hors du contrôleur. */
@Service
public class ClientApi { private final org.springframework.web.reactive.function.client.WebClient http;
        private final String base;
        public ClientApi(org.springframework.web.reactive.function.client.WebClient.Builder builder,@org.springframework.beans.factory.annotation.Value("${client.base-url:http://SERVICE-CLIENT}") String base) { this.http=builder.build();this.base=base; }
        public Client findClientById(Long id) {
            return http.get().uri(base+"/api/clients/"+id).retrieve().bodyToMono(Client.class).timeout(java.time.Duration.ofSeconds(5)).block();
        } }
