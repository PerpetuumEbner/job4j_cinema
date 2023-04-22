package ru.job4j.cinema;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;


@SpringBootApplication
public class StartUI {


    public static void main(String[] args) {
        SpringApplication.run(StartUI.class, args);
        System.out.println("Go to http://localhost:8080/films");
    }
}