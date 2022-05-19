package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.sevice.SessionService;
import ru.job4j.cinema.sevice.TicketService;
import ru.job4j.cinema.sevice.UserService;

@ThreadSafe
@Controller
@RequestMapping("/order")
public class SessionController {
    private final SessionService sessionService;

    private final TicketService ticketService;

    private final UserService userService;

    @Autowired
    public SessionController(SessionService sessionService, TicketService ticketService, UserService userService) {
        this.sessionService = sessionService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    @PostMapping
    public String order(Model model) {

        return null;
    }
}