package ru.job4j.cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class TicketDBStore {
    private static final Logger LOG = LogManager.getLogger(UserDBStore.class);

    private final BasicDataSource pool;

    private final FilmDBStore filmDBStore;

    public TicketDBStore(BasicDataSource pool, FilmDBStore filmDBStore) {
        this.pool = pool;
        this.filmDBStore = filmDBStore;
    }

    /**
     * Добавление билета.
     *
     * @param ticket Билет, который необходимо добавить.
     * @return Добавленный билет иначе Optional.
     */
    public Optional<Ticket> add(Ticket ticket) {
        Optional<Ticket> optionalTicket = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO tickets(film_id, row, cell, user_id) VALUES (?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, ticket.getFilmId());
            ps.setInt(2, ticket.getRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getUser_id());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                while (id.next()) {
                    ticket.setId(id.getInt(1));
                }
            }
            optionalTicket = Optional.of(ticket);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return optionalTicket;
    }

    /**
     * Поиск билета по Id.
     *
     * @param id Id билета.
     * @return Объект билета если найден по Id иначе null.
     */
    public Optional<Ticket> findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM tickets WHERE id =?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return Optional.of(new Ticket(
                            it.getInt("id"),
                            it.getInt("film_id"),
                            it.getInt("row"),
                            it.getInt("cell"),
                            it.getInt("user_id")
                    ));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    /**
     * Поиск всех купленных билетов.
     *
     * @return Список билетов.
     */
    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM tickets")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(
                            new Ticket(
                                    it.getInt("id"),
                                    it.getInt("film_id"),
                                    it.getInt("row"),
                                    it.getInt("cell"),
                                    it.getInt("user_id")
                            )
                    );
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return tickets;
    }

    /**
     * Поиск всех купленных билетов пользователя.
     *
     * @param id Id пользователя.
     * @return Список билетов пользователя.
     */
    public List<Ticket> findBuyUserTickets(int id) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM tickets WHERE user_id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(
                            new Ticket(
                                    it.getInt("id"),
                                    it.getInt("film_id"),
                                    it.getInt("row"),
                                    it.getInt("cell"),
                                    it.getInt("user_id")
                            )
                    );
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return tickets;
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
            ticket.setFilm(filmDBStore.findById(ticket.getFilmId()));
        }
        return tickets;
    }
}