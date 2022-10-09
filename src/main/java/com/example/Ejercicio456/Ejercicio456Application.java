package com.example.Ejercicio456;

import com.example.Ejercicio456.entity.Laptop;
import com.example.Ejercicio456.repository.LaptopRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Ejercicio456Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Ejercicio456Application.class, args);
		LaptopRepository laptopRepository = context.getBean(LaptopRepository.class);

		Laptop laptop1 = new Laptop(null, "Apple", "MacBook Air (M2)", 1199.00);
		Laptop laptop2 = new Laptop(null, "HP", "Spectre x360 14", 1549.99);
		//Laptop laptop3 = new Laptop(null, "Asus", "ROG Zephyrus G15", 1799.99);

		laptopRepository.save(laptop1);
		laptopRepository.save(laptop2);
		//laptopRepository.save(laptop3);
	}

}
