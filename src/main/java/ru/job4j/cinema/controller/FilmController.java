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
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.sevice.CinemaHallService;
import ru.job4j.cinema.sevice.FilmService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static ru.job4j.cinema.filter.CheckHttpSession.userHttpSession;

@ThreadSafe
@Controller
public class FilmController {
    private final FilmService filmService;

    private final CinemaHallService cinemaHallService;

    @Autowired
    public FilmController(FilmService filmService,
                          CinemaHallService cinemaHallService) {
        this.filmService = filmService;
        this.cinemaHallService = cinemaHallService;
    }

    @GetMapping("/films")
    public String films(Model model, HttpSession session) {
        model.addAttribute("user", userHttpSession(session));
        model.addAttribute("films", filmService.findAll());
        return "films";
    }

    @GetMapping("/films/{filmId}")
    public String formSelectionHall(Model model,
                                    @PathVariable("filmId") int filmId,
                                    HttpSession session) {
        model.addAttribute("user", userHttpSession(session));
        model.addAttribute("film", filmService.findById(filmId));
        return "/film";
    }

    @GetMapping("/place/{filmId}")
    public String formSelectionSeat(Model model,
                                    @PathVariable("filmId") int filmId,
                                    HttpSession session) {
        Ticket ticketSession = new Ticket();
        ticketSession.setFilmId(filmId);
        session.setAttribute("ticket", ticketSession);
        model.addAttribute("user", userHttpSession(session));
        model.addAttribute("film", filmService.findById(filmId));
        model.addAttribute("rows", cinemaHallService.findAllRows(1));
        model.addAttribute("cells", cinemaHallService.findAllCell(1));
        return "place";
    }

    @GetMapping("/formAddFilm")
    public String addFilm(Model model, HttpSession session) {
        model.addAttribute("user", userHttpSession(session));
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
        Optional<Film> film = filmService.findById(id);
        return film.<ResponseEntity<Resource>>map(value -> ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(value.getPoster().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(value.getPoster()))).orElse(null);
    }
}