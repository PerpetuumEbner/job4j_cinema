package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.sevice.FilmService;
import ru.job4j.cinema.sevice.TicketService;

import javax.servlet.http.HttpSession;

@ThreadSafe
@Controller
public class TicketController {
    private final TicketService ticketService;

    private final FilmService filmService;

    @Autowired
    public TicketController(TicketService ticketService, FilmService filmService) {
        this.ticketService = ticketService;
        this.filmService = filmService;
    }

    @GetMapping("/ticketInfo")
    public String ticketInfo(Model model,
                             @ModelAttribute("ticket") Ticket ticket,
                             HttpSession httpSession) {
        Ticket ticketSession = (Ticket) httpSession.getAttribute("ticket");
        ticketSession.setRow(ticket.getRow());
        ticketSession.setCell(ticket.getCell());
        model.addAttribute("film", filmService.findById(ticketSession.getId()));
        return "ticketInfo";
    }
}