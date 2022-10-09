package com.example.Ejercicio456.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/saludo")
    public String imprimirSaludo() {
        return " :: Open Bootcamp ::<br>Curso de Spring - Ejercicio 11";
    }
}
