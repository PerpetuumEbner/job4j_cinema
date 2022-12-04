package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
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
import ru.job4j.cinema.sevice.FilmService;

import java.io.IOException;

@ThreadSafe
@Controller
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public String films(Model model) {
        model.addAttribute("films", filmService.findAll());
        return "films";
    }

    @GetMapping("/formAddFilm")
    public String formAddFilm(Model model) {
        model.addAttribute("film", new Film());
        return "addFilm";
    }

//    @PostMapping("/createFilm")
//    public String createFilm(@ModelAttribute Film film,
//                             @RequestParam("file") MultipartFile file) throws IOException {
//        film.setPhoto(file.getBytes());
//        filmService.add(film);
//        return "redirect:/films";
//    }
//
//    @GetMapping("/photoFilm/{filmId}")
//    public ResponseEntity<Resource> download(@PathVariable("filmId") Integer filmId) {
//        Film film = filmService.findById(filmId);
//        return ResponseEntity.ok()
//                .headers(new HttpHeaders())
//                .contentLength(film.getPhoto().length)
//                .contentType(MediaType.parseMediaType("application/octet-stream"))
//                .body(new ByteArrayResource(film.getPhoto()));
//    }
}