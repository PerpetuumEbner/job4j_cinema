package ru.job4j.cinema.sevice;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.TicketDAO;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.persistence.Sql2oTicketDBStore;

import java.util.List;
import java.util.Optional;

/**
 * Верхний слой хранилища TicketDBStore в котором находятся билеты.
 *
 * @author yustas
 * @version 1.0
 */
@ThreadSafe
@Service
public class TicketService {
    private final Sql2oTicketDBStore store;

    @Autowired
    public TicketService(Sql2oTicketDBStore store) {
        this.store = store;
    }

    public Optional<Ticket> add(Ticket ticket) {
        return store.add(ticket);
    }

    public List<Ticket> findAll() {
        return store.findAll();
    }

    public List<TicketDAO> findBuyUserTicketsFilmName(int id) {
        return store.findBuyUserTicketsFilmName(id);
    }
}