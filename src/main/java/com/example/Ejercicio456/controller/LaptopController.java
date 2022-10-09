package com.example.Ejercicio456.controller;

import com.example.Ejercicio456.entity.Laptop;
import com.example.Ejercicio456.repository.LaptopRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LaptopController {

    private final LaptopRepository laptopRepository;

    public LaptopController(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }

    // Devolver todas las laptops
    @GetMapping("/api/laptops")
    public List<Laptop> findAll() {
        return laptopRepository.findAll();
    }

    // Devolver sólo una laptop según su id
    @GetMapping("/api/laptops/{id}")
    public ResponseEntity<Laptop> findOneById(@PathVariable Long id) {
        Optional<Laptop> laptopOpt = laptopRepository.findById(id);

        if (laptopOpt.isPresent())
            return ResponseEntity.ok(laptopOpt.get());
        else
            return ResponseEntity.notFound().build();
    }

    // Agregar una laptop
    @PostMapping("/api/laptops")
    public Laptop create(@RequestBody Laptop laptop) {
        return laptopRepository.save(laptop);
    }

    // Actualizar una laptop
    @PutMapping("/api/laptops")
    public ResponseEntity<Laptop> update(@RequestBody Laptop laptop) {

        if (laptop.getId() == null)
            return ResponseEntity.badRequest().build();

        if (!laptopRepository.existsById(laptop.getId()))
            return ResponseEntity.notFound().build();

        Laptop result = laptopRepository.save(laptop);
        return ResponseEntity.ok(result);
    }

    // Borrar una laptop
    @DeleteMapping("/api/laptops/{id}")
    public ResponseEntity<Laptop> delete(@PathVariable Long id) {

        if (!laptopRepository.existsById(id))
            return ResponseEntity.notFound().build();

        laptopRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    // Borrar todas las laptops
    @DeleteMapping("/api/laptops")
    public ResponseEntity<Laptop> deleteAll() {
        laptopRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
