package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.sevice.FilmsService;
import ru.job4j.cinema.sevice.SessionService;

@ThreadSafe
@Controller
@RequestMapping("/films")
public class FilmsController {
    private final FilmsService filmsService;

    private final SessionService sessionService;

    @Autowired
    public FilmsController(FilmsService filmsService, SessionService sessionService) {
        this.filmsService = filmsService;
        this.sessionService = sessionService;
    }

    @GetMapping()
    public String film(Model model) {
        model.addAttribute("films", filmsService.findAll());
        return "films";
    }

    @GetMapping("/{filmId}")
    public String selectFilms(@PathVariable("filmId") int id, Model model) {
        model.addAttribute("film", filmsService.findById(id));
        model.addAttribute("rows", sessionService.rowsList());
        model.addAttribute("cells", sessionService.cellsList());
        return "/film";
    }
}