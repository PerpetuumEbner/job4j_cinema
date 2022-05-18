package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.sevice.SessionService;
import ru.job4j.cinema.sevice.TicketService;
import ru.job4j.cinema.sevice.UserService;

@ThreadSafe
@Controller
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

    @GetMapping("placeSelection")
    public String films(Model model) {
        model.addAttribute("rows", sessionService.rowsList());
        model.addAttribute("cells", sessionService.cellsList());
        return "placeSelection";
    }

    @GetMapping("placeSelection/{ticketId}")
    public String placeSelection(Model model, @PathVariable("ticketId") int id) {
        model.addAttribute("ticket", ticketService.findById(id));
        return "placeSelection";
    }

    @PostMapping("ticketSelection")
    public String ticketSelection(Model model) {
        model.addAttribute("ticket",
                new Ticket(0, 0, 0, 0, 0));
        return "ticketSelection";
    }
}