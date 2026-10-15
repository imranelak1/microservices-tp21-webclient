package com.example.car.services;
import com.example.car.entities.Car;
import com.example.car.repositories.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;
@Service
public class CarService {
    private final CarRepository cars;
    private final ClientApi clients;
    public CarService(CarRepository cars,ClientApi clients) { this.cars=cars;this.clients=clients; }
    public List<Car> findAll() { return cars.findAll().stream().map(this::enrich).toList(); }
    public Car findById(Long id) { return enrich(localById(id)); }
    public List<Car> findByClient(Long id) { return cars.findByClientId(id).stream().map(this::enrich).toList(); }
    public Car save(Car car) {
        if(car.getClientId()==null || car.getMarque()==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Client et marque requis");
        clients.findClientById(car.getClientId());
        return enrich(cars.save(car));
    }
    public Car update(Long id,Car input) {
        var car=localById(id);
        if(input.getMarque()!=null) car.setMarque(input.getMarque());
        if(input.getModele()!=null) car.setModele(input.getModele());
        if(input.getMatricule()!=null) car.setMatricule(input.getMatricule());
        return enrich(cars.save(car));
    }
    public void delete(Long id) { cars.delete(localById(id)); }
    private Car localById(Long id) { return cars.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Voiture introuvable")); }
    private Car enrich(Car car) {
        if(car.getClientId()!=null) {
            try { car.setClient(clients.findClientById(car.getClientId())); }
            catch(Exception ex) { car.setClient(null); } // Le TP RestTemplate demande une réponse partielle si Client tombe.
        }
        return car;
    }
}
