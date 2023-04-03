package ru.job4j.cinema.sevice;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.persistence.Sql2oFilmDBStore;

import java.util.List;
import java.util.Optional;

/**
 * Верхний слой хранилища FilmDBStore в котором находятся фильмы.
 *
 * @author yustas
 * @version 1.0
 */
@ThreadSafe
@Service
public class FilmService {
    private final Sql2oFilmDBStore store;

    @Autowired
    public FilmService(Sql2oFilmDBStore store) {
        this.store = store;
    }

    public Optional<Film> add(Film film) {
        return store.add(film);
    }

    public Optional<Film> findById(int id) {
        return store.findById(id);
    }

    public List<Film> findAll() {
        return store.findAll();
    }
}