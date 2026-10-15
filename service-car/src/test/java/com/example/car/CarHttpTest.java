package com.example.car;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.*;
import org.springframework.http.*;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import com.example.car.entities.Car;
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT,properties={"spring.datasource.url=jdbc:h2:mem:testcar;DB_CLOSE_DELAY=-1","spring.datasource.driver-class-name=org.h2.Driver","spring.datasource.username=sa","spring.datasource.password=","spring.cloud.discovery.enabled=false","eureka.client.enabled=false","client.fixed-url=true"})
class CarHttpTest {
    static HttpServer upstream;
    static String base=startUpstream();
    static String startUpstream() {
        try {
            upstream=HttpServer.create(new InetSocketAddress("localhost",0),0);
            upstream.createContext("/api/clients",exchange->{
                byte[] data="{\"id\":1,\"nom\":\"Salma\",\"age\":22}".getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().add("Content-Type","application/json");exchange.sendResponseHeaders(200,data.length);exchange.getResponseBody().write(data);exchange.close();
            });upstream.start();return "http://localhost:"+upstream.getAddress().getPort();
        } catch(Exception e) { throw new IllegalStateException(e); }
    }
    @DynamicPropertySource static void properties(DynamicPropertyRegistry properties) { properties.add("client.base-url",()->base); }
    @AfterAll static void close() {upstream.stop(0);}
    @Autowired TestRestTemplate http;
    @Test void enrichesActualHttpResponse() { var body=http.getForObject("/api/cars/1",String.class);assertTrue(body.contains("Salma"),body);assertTrue(body.contains("Toyota")); }
    @Test void listsOnlyRequestedClientCars() { var cars=http.getForObject("/api/cars/byClient/1",Car[].class);assertEquals(2,cars.length);assertEquals(1,cars[0].getClientId()); }
    @Test void createAndUpdateKeepLocalData() {
        var response=http.postForEntity("/api/cars",new Car(null,"Dacia","Sandero","TP-2026",1L,null),Car.class);
        assertEquals(HttpStatus.CREATED,response.getStatusCode());var car=response.getBody();assertEquals("Salma",car.getClient().nom());
        car.setMatricule("TP-UPDATED");http.put("/api/cars/"+car.getId(),car);
        assertTrue(http.getForObject("/api/cars/"+car.getId(),String.class).contains("TP-UPDATED"));
        http.delete("/api/cars/"+car.getId());
    }
    @Test void missingCarReturns404() {assertEquals(HttpStatus.NOT_FOUND,http.getForEntity("/api/cars/999999",String.class).getStatusCode());}
}
