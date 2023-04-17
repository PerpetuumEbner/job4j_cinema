package ru.job4j.cinema.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * В классе происходит обработка билетов в базе данных.
 *
 * @author yustas
 * @version 1.0
 */
@Repository
public class Sql2oTicketDBStore {
    private static final Logger LOG = LogManager.getLogger(Sql2oUserDBStore.class);

    private final Sql2o sql2o;

    private final Sql2oFilmDBStore sql2oFilmDBStore;

    public Sql2oTicketDBStore(Sql2o sql2o, Sql2oFilmDBStore sql2oFilmDBStore) {
        this.sql2o = sql2o;
        this.sql2oFilmDBStore = sql2oFilmDBStore;
    }

    /**
     * Добавление билета.
     *
     * @param ticket Билет, который необходимо добавить.
     * @return Добавленный билет.
     */
    public Optional<Ticket> add(Ticket ticket) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("INSERT INTO tickets(film_id, row, cell, user_id) "
                            + "VALUES (:filmId, :row, :cell, :userId)", true)
                    .addParameter("filmId", ticket.getFilmId())
                    .addParameter("row", ticket.getRow())
                    .addParameter("cell", ticket.getCell())
                    .addParameter("userId", ticket.getUserId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            ticket.setId(generatedId);
            return Optional.of(ticket);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    /**
     * Поиск билета по Id.
     *
     * @param id Id билета.
     * @return Объект билета если найден по Id, иначе Optional.
     */
    public Optional<Ticket> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM tickets WHERE id = :id");
            query.addParameter("id", id);
            var ticket = query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetchFirst(Ticket.class);
            return Optional.ofNullable(ticket);
        }
    }

    /**
     * Поиск всех купленных билетов.
     *
     * @return Список билетов.
     */
    public List<Ticket> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM tickets");
            return query.executeAndFetch(Ticket.class);
        }
    }

    /**
     * Поиск всех купленных билетов пользователя.
     *
     * @param id Id пользователя.
     * @return Список билетов пользователя.
     */
    public List<Ticket> findBuyUserTickets(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM tickets WHERE user_id = :userId");
            query.addParameter("userId", id);
            return query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetch(Ticket.class);
        }
    }

    /**
     * Поиск всех купленных билетов пользователя с добавлением названия фильма.
     *
     * @param id Id пользователя.
     * @return Список билетов пользователя.
     */
    public List<Ticket> findBuyUserTicketsFilmName(int id) {
        List<Ticket> tickets = new ArrayList<>(findBuyUserTickets(id));
        for (Ticket ticket : tickets) {
            Optional<Film> optionalFilm = sql2oFilmDBStore.findById(ticket.getFilmId());
            optionalFilm.ifPresent(ticket::setFilm);

        }
        return tickets;
    }
}