package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.sevice.FilmService;
import ru.job4j.cinema.sevice.TicketService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static ru.job4j.cinema.filter.CheckHttpSession.userHttpSession;

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
                             @RequestParam(name = "fail", required = false) Boolean fail,
                             HttpSession session) {
        User userSession = (User) session.getAttribute("user");
        Ticket ticketSession = (Ticket) session.getAttribute("ticket");
        ticketSession.setRow(ticket.getRow());
        ticketSession.setCell(ticket.getCell());
        ticketSession.setUserId(userSession.getId());
        model.addAttribute("fail", fail != null);
        model.addAttribute("user", userHttpSession(session));
        model.addAttribute("film", filmService.findById(ticketSession.getFilmId()));
        return "ticketInfo";
    }

    @PostMapping("/buyTicket")
    public String buyTicket(HttpSession session) {
        Ticket sessionTicket = (Ticket) session.getAttribute("ticket");
        Optional<Ticket> buyTicket = ticketService.add(sessionTicket);
        if (buyTicket.isEmpty()) {
            return "redirect:/ticketInfo?fail=true";
        }
        return "redirect:/tickets";
    }

    @GetMapping("/tickets")
    public String userTickets(Model model, HttpSession session) {
        model.addAttribute("user", userHttpSession(session));
        model.addAttribute("tickets", ticketService.findBuyUserTicketsFilmName(userHttpSession(session).getId()));
        model.addAttribute("films", filmService.findAll());
        return "tickets";
    }
}