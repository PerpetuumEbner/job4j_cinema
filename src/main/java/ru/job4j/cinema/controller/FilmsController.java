package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.sevice.FilmsService;

@ThreadSafe
@Controller
public class FilmsController {
    private final FilmsService service;

    public FilmsController(FilmsService service) {
        this.service = service;
    }

    @GetMapping("/films")
    public String film(Model model) {
        model.addAttribute("films", service.findAll());
        return "films";
    }
}