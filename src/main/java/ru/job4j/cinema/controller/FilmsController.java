package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.sevice.FilmsService;

@ThreadSafe
@Controller
@RequestMapping("/collection")
public class FilmsController {
    private final FilmsService service;

    @Autowired
    public FilmsController(FilmsService service) {
        this.service = service;
    }

    @GetMapping()
    public String film(Model model) {
        model.addAttribute("films", service.findAll());
        return "films";
    }

    @GetMapping("/{filmId}")
    public String selectFilms(@PathVariable("filmId") int id, Model model) {
        model.addAttribute("film", service.findById(id));
        return "/film";
    }
}