package com.example.Ejercicio456.controller;

import com.example.Ejercicio456.entity.Laptop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LaptopControllerTest {

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @Test
    @DisplayName("findAll: Devolver todas las laptops")
    void findAll() {
        ResponseEntity<Laptop[]> response = testRestTemplate.getForEntity("/api/laptops", Laptop[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        // [Opcional] Mostrar cuántos elementos hay
        List<Laptop> laptops = Arrays.asList(response.getBody());
        System.out.println("Elementos en base de datos: " + laptops.size());
    }

    @Test
    @DisplayName("findOneById: Devolver sólo una laptop según su id")
    void findOneById() {
        ResponseEntity<Laptop> response = testRestTemplate.getForEntity("/api/laptops/1", Laptop.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("create: Agregar una laptop")
    void create() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                    "brand": "Asus",
                    "model": "ROG Zephyrus G15",
                    "price": 1799.99
                }""";

        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops", HttpMethod.POST, request, Laptop.class);
        Laptop result = response.getBody();

        assertEquals("ROG Zephyrus G15", result.getModel());
    }

    @Test
    @DisplayName("update: Actualizar una laptop")
    void update() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                    "id": "1",
                    "brand": "HP vía prueba unitaria",
                    "model": "ROG Zephyrus G15 Plus",
                    "price": 1799.99
                }""";

        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops", HttpMethod.PUT, request, Laptop.class);
        Laptop result = response.getBody();

        assertEquals("HP vía prueba unitaria", result.getBrand());
    }

    @Test
    @DisplayName("delete: Borrar una laptop")
    void delete() {
        HttpEntity<String> request = new HttpEntity<String>("Ejecutando prueba unitaria (delete)");
        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops/1", HttpMethod.DELETE, request, Laptop.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("deleteAll: Borrar todas las laptops")
    void deleteAll() {
        HttpEntity<String> request = new HttpEntity<String>("Ejecutando prueba unitaria (deleteAll)");
        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops", HttpMethod.DELETE, request, Laptop.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}