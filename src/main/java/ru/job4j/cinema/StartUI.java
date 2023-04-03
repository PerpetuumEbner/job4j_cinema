package ru.job4j.cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class StartUI {
    public static void main(String[] args) {
        SpringApplication.run(StartUI.class, args);
        System.out.println("Go to http://localhost:8080/films");
    }
}