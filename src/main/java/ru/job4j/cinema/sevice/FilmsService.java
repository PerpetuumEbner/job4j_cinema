package ru.job4j.cinema.sevice;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Films;
import ru.job4j.cinema.persistence.FilmsDBStore;

import java.util.List;

@ThreadSafe
@Service
public class FilmsService {
    private final FilmsDBStore store;

    @Autowired
    public FilmsService(FilmsDBStore store) {
        this.store = store;
    }

    public Films add(Films film) {
        return store.add(film);
    }

    public Films findById(int id) {
        return store.findById(id);
    }

    public List<Films> findAll() {
        return store.findAll();
    }
}