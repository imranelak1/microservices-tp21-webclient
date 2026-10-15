package com.example.client.controllers;
import com.example.client.entities.Client;
import com.example.client.services.ClientService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;
@RestController @RequestMapping({"/api/clients"})
public class ClientController {
    private final ClientService service;
    public ClientController(ClientService service) { this.service=service; }
    @GetMapping public List<Client> findAll() { return service.findAll(); }
    @GetMapping("/{id}") public Client findById(@PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Client save(@RequestBody Client client) { client.setId(null);return service.save(client); }
    @PutMapping("/{id}") public Client update(@PathVariable Long id,@RequestBody Client client) { service.findById(id);client.setId(id);return service.save(client); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
}
