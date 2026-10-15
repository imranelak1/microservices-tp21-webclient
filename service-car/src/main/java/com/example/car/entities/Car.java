package com.example.car.entities;
import jakarta.persistence.*;
import lombok.*;
import com.example.car.models.Client;
import com.fasterxml.jackson.annotation.JsonAlias;
/** Seul clientId est persisté ; l'objet distant n'appartient pas à cette base. */
@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Car {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    private String marque;
    private String modele;
    private String matricule;
    @JsonAlias({"client_id","id_client"}) private Long clientId;
    @Transient private Client client;
}
