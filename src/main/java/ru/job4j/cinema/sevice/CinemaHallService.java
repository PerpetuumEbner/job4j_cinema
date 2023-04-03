package ru.job4j.cinema.sevice;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.CinemaHall;
import ru.job4j.cinema.persistence.Sql2oCinemaHallDBStore;

import java.util.List;
import java.util.Optional;

/**
 * Верхний слой хранилища CinemaHallDBStore в котором находятся места в зале.
 *
 * @author yustas
 * @version 1.0
 */
@ThreadSafe
@Service
public class CinemaHallService {
    private final Sql2oCinemaHallDBStore cinemaHallDBStore;

    @Autowired
    public CinemaHallService(Sql2oCinemaHallDBStore cinemaHallDBStore) {
        this.cinemaHallDBStore = cinemaHallDBStore;
    }

    public Optional<CinemaHall> findById(int id) {
        return cinemaHallDBStore.findById(id);
    }

    public List<CinemaHall> findAll() {
        return cinemaHallDBStore.findAll();
    }

    public List<Integer> findAllRows(int id) {
        return cinemaHallDBStore.findAllRows(id);
    }

    public List<Integer> findAllCell(int id) {
        return cinemaHallDBStore.findAllCell(id);
    }
}