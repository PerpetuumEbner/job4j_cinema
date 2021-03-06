package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.Films;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.sevice.FilmsService;
import ru.job4j.cinema.sevice.SessionService;
import ru.job4j.cinema.sevice.TicketService;
import ru.job4j.cinema.sevice.UserService;

@ThreadSafe
@Controller
@RequestMapping("/films")
public class SessionController {
    private final SessionService sessionService;

    private final TicketService ticketService;

    private final UserService userService;

    private final FilmsService filmsService;

    @Autowired
    public SessionController(SessionService sessionService, TicketService ticketService, UserService userService, FilmsService filmsService) {
        this.sessionService = sessionService;
        this.ticketService = ticketService;
        this.userService = userService;
        this.filmsService = filmsService;
    }

    @GetMapping()
    public String films(Model model) {
        model.addAttribute("films", filmsService.findAll());
        return "films";
    }

    @PostMapping("/selectFilms")
    public String selectFilms(@ModelAttribute Films film, int id) {
        filmsService.findById(id);
        return "";
    }

    @GetMapping("/{filmId}")
    public String formBuyTicket(Model model, @PathVariable("filmId") int id) {
        model.addAttribute("film", filmsService.findById(id));
        model.addAttribute("rows", sessionService.rowsList());
        model.addAttribute("cells", sessionService.cellsList());
        model.addAttribute("ticket", new Ticket(0, 0, 0, 0, 0));
        return "/film";
    }


    @PostMapping("/buyTicket")
    public String buy(@ModelAttribute Ticket ticket) {
        if (sessionService.ticketSelection(ticket)) {
            ticketService.add(ticket);
            return "/successfully";
        }
        return "/failure";
    }
}