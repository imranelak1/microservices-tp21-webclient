package com.example.car.controllers;
import com.example.car.models.Client;
import com.example.car.services.ClientApi;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/test")
public class TestController {
    private final ClientApi clients;
    public TestController(ClientApi clients) { this.clients=clients; }
    @GetMapping("/client/{id}") public Client client(@PathVariable Long id) { return clients.findClientById(id); }
}
