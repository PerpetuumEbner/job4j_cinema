package ru.job4j.cinema.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Film;

import java.util.List;
import java.util.Optional;

/**
 * В классе происходит обработка фильмов в базе данных.
 *
 * @author yustas
 * @version 1.0
 */
@Repository
public class Sql2oFilmDBStore {
    private static final Logger LOG = LogManager.getLogger(Sql2oUserDBStore.class);

    private final Sql2o sql2o;

    public Sql2oFilmDBStore(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    /**
     * Добавление фильма.
     *
     * @param film Фильм, который необходимо добавить.
     * @return Добавленный фильм.
     */
    public Optional<Film> add(Film film) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("INSERT INTO films (name, productionYear, poster) "
                            + "VALUES (:name, :productionYear, :poster)", true)
                    .addParameter("name", film.getName())
                    .addParameter("productionYear", film.getProductionYear())
                    .addParameter("poster", film.getPoster());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            film.setId(generatedId);
            return Optional.of(film);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    /**
     * Поиск фильма по id.
     *
     * @param id Id фильма.
     * @return Объект фильма если найден по id, иначе Optional.
     */
    public Optional<Film> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM films WHERE id = :id");
            var film = query.addParameter("id", id).executeAndFetchFirst(Film.class);
            return Optional.ofNullable(film);
        }
    }

    /**
     * Поиск всех фильмов.
     *
     * @return Список фильмов.
     */
    public List<Film> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM films");
            return query.executeAndFetch(Film.class);
        }
    }
}