package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.sevice.FilmsService;
import ru.job4j.cinema.sevice.SessionService;
import ru.job4j.cinema.sevice.TicketService;

@ThreadSafe
@Controller
@RequestMapping("/films")
public class FilmsController {
    private final FilmsService filmsService;

    private final SessionService sessionService;

    private final TicketService ticketService;

    @Autowired
    public FilmsController(FilmsService filmsService, SessionService sessionService, TicketService ticketService) {
        this.filmsService = filmsService;
        this.sessionService = sessionService;
        this.ticketService = ticketService;
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

    @PostMapping("/order")
    public String order(@ModelAttribute Ticket ticket) {
        if (sessionService.ticketSelection(ticket)) {
            ticketService.add(ticket);
            return "/successfully";
        }
        return "/failure";
    }

    @GetMapping("/formOrder")
    public String formOrder(Model model) {
        model.addAttribute("ticket", new Ticket(0, 0, 0, 0, 0));
        return "/order";
    }
}