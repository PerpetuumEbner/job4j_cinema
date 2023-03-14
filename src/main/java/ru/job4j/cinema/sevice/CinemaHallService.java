package ru.job4j.cinema.sevice;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.CinemaHall;
import ru.job4j.cinema.persistence.CinemaHallDBStore;

import java.util.List;

@ThreadSafe
@Service
public class CinemaHallService {
    private final CinemaHallDBStore cinemaHallDBStore;

    public CinemaHallService(CinemaHallDBStore cinemaHallDBStore) {
        this.cinemaHallDBStore = cinemaHallDBStore;
    }

    public CinemaHall findById(int id) {
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