package ru.job4j.cinema.sevice;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.persistence.TicketDBStore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
    private final TicketDBStore store;

    @Autowired
    public TicketService(TicketDBStore store) {
        this.store = store;
    }

    public Optional<Ticket> add(Ticket ticket) {
        return store.add(ticket);
    }

    public Optional<Ticket> findById(int id) {
        return store.findById(id);
    }

    public List<Ticket> findAll() {
        return store.findAll();
    }

    public List<Ticket> findBuyUserTickets(int id) {
        return store.findBuyUserTickets(id);
    }
}