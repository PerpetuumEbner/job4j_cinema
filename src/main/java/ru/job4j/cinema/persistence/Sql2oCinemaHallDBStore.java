package ru.job4j.cinema.persistence;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.CinemaHall;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * В классе происходит обработка мест в зале в базе данных.
 *
 * @author yustas
 * @version 1.0
 */
@Repository
public class Sql2oCinemaHallDBStore {
    private final Sql2o sql2o;

    public Sql2oCinemaHallDBStore(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    /**
     * Поиск зала по Id.
     *
     * @param id Id зала.
     * @return Найденный зал, иначе Optional.
     */
    public Optional<CinemaHall> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM halls WHERE id = :id");
            var cinemaHall = query.addParameter("id", id).executeAndFetchFirst(CinemaHall.class);
            return Optional.ofNullable(cinemaHall);
        }
    }

    /**
     * Поиск всех залов.
     *
     * @return Список найденных залов.
     */
    public List<CinemaHall> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM halls");
            return query.executeAndFetch(CinemaHall.class);
        }
    }

    /**
     * Список всех рядов в зале найденных по Id.
     *
     * @param id Id зала.
     * @return Список рядов в зале.
     */
    public List<Integer> findAllRows(int id) {
        List<Integer> rows = new ArrayList<>();
        Optional<CinemaHall> count = findById(id);
        if (count.isPresent()) {
            for (int index = 1; index <= count.get().getRow(); index++) {
                rows.add(index);
            }
        }
        return rows;
    }

    /**
     * Список всех мест в зале найденных по Id.
     *
     * @param id Id зала.
     * @return Список мест в зале.
     */
    public List<Integer> findAllCell(int id) {
        List<Integer> cells = new ArrayList<>();
        Optional<CinemaHall> count = findById(id);
        if (count.isPresent()) {
            for (int index = 1; index <= count.get().getCell(); index++) {
                cells.add(index);
            }
        }
        return cells;
    }
}