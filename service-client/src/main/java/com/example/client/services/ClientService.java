package com.example.client.services;
import com.example.client.entities.Client;
import com.example.client.repositories.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;
/** Chaque service conserve ses propres données ; aucun lien JPA vers Voiture. */
@Service
public class ClientService {
    private final ClientRepository repository;
    public ClientService(ClientRepository repository) { this.repository=repository; }
    public List<Client> findAll() { return repository.findAll(); }
    public Client findById(Long id) { return repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Client non trouvé")); }
    public Client save(Client client) {
        if(client.getNom()==null || client.getNom().isBlank() || client.getAge()==null || client.getAge()<0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Nom et âge valides requis");
        return repository.save(client);
    }
    public void delete(Long id) { findById(id);repository.deleteById(id); }
}
