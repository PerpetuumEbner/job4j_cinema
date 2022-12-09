package ru.job4j.cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilmDBStore {
    private static final Logger LOG = LogManager.getLogger(UserDBStore.class);

    private final BasicDataSource pool;

    public FilmDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * Добавление параметров в фильм.
     *
     * @param film Объект фильма.
     * @return Объект фильма с добавленными параметрами.
     */
    public Film add(Film film) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO films(name, productionyear, poster) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, film.getName());
            ps.setInt(2, film.getProductionYear());
            ps.setBytes(3, film.getPoster());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    film.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return film;
    }

    /**
     * Поиск объекта фильма по id.
     *
     * @param id Id фильма.
     * @return Объект фильма если найден по id иначе null.
     */
    public Film findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM films WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Film(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getInt("productionYear"),
                            it.getBytes("poster"));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Поиск всех параметров фильма в базе данных.
     *
     * @return Список параметров фильма.
     */
    public List<Film> findAll() {
        List<Film> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM films")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Film(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getInt("productionYear"),
                            it.getBytes("poster")));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return posts;
    }
}