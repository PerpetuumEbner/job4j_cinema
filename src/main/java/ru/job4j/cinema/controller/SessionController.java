package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.sevice.SessionService;

@ThreadSafe
@Controller
public class SessionController {
    private final SessionService service;

    public SessionController(SessionService service) {
        this.service = service;
    }

    @GetMapping("/films")
    public String films(Model model) {
        model.addAttribute("rows", service.rowsList());
        model.addAttribute("sells", service.rowsList());
        return "films";
    }
}