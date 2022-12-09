package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.sevice.CinemaHallService;
import ru.job4j.cinema.sevice.FilmService;
import ru.job4j.cinema.sevice.TicketService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@ThreadSafe
@Controller
public class FilmController {
    private final FilmService filmService;

    private final TicketService ticketService;

    private final CinemaHallService cinemaHallService;

    @Autowired
    public FilmController(FilmService filmService, TicketService ticketService, CinemaHallService cinemaHallService) {
        this.filmService = filmService;
        this.ticketService = ticketService;
        this.cinemaHallService = cinemaHallService;
    }

    @GetMapping("/films")
    public String films(Model model) {
        model.addAttribute("films", filmService.findAll());
        return "films";
    }

    @GetMapping("/films/{filmId}")
    public String formBuyTicket(Model model, HttpServletRequest req, @PathVariable("filmId") int id) {
        HttpSession session = req.getSession();
        session.setAttribute("film", filmService.findById(id));
        model.addAttribute("film", filmService.findById(id));
        return "/film";
    }

    @GetMapping("/formAddFilm")
    public String addFilm(Model model) {
        model.addAttribute("film", new Film());
        return "addFilm";
    }

    @PostMapping("/createFilm")
    public String createFilm(@ModelAttribute Film film,
                             @RequestParam("file") MultipartFile file) throws IOException {
        film.setPoster(file.getBytes());
        filmService.add(film);
        return "redirect:/films";
    }

    @GetMapping("/posterFilm/{adId}")
    public ResponseEntity<Resource> download(@PathVariable("adId") int id) {
        Film film = filmService.findById(id);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(film.getPoster().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(film.getPoster()));
    }
}