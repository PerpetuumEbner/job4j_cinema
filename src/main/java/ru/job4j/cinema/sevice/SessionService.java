package ru.job4j.cinema.sevice;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Sessions;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.persistence.SessionsDBStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Верхний слой хранилища TicketDBStore в котором находятся билеты.
 *
 * @author yustas
 * @version 1.0
 */
@ThreadSafe
@Service
public class SessionService {
    private final SessionsDBStore store;

    @Autowired
    public SessionService(SessionsDBStore store) {
        this.store = store;
    }

    private final AtomicInteger ID = new AtomicInteger();

    private final int amountRows = 10;

    private final int amountCells = 15;

    private final Map<Integer, Ticket> tickets = new ConcurrentHashMap<>();

    private final Map<Integer, Ticket> purchasedTickets = new ConcurrentHashMap<>();

    private final List<Integer> sessionsList = new ArrayList<>();

    private final List<Integer> rowList = new ArrayList<>(amountRows);

    private final List<Integer> cellList = new ArrayList<>(amountCells);

    public boolean ticketSelection(Ticket ticket) {
        boolean result = false;
        if (rowList.get(ticket.getRow()) != ticket.getRow()
                && cellList.get(ticket.getCell()) != ticket.getCell()) {
            rowList.add(ticket.getRow(), 1);
            cellList.add(ticket.getCell(), 1);
            ticket.setId(ID.incrementAndGet());
            tickets.put(ticket.getId(), new Ticket(
                    ID.incrementAndGet(),
                    ticket.getSessionId(),
                    ticket.getRow(),
                    ticket.getCell(),
                    ticket.getUser_id()
            ));
            result = true;
        }
        return result;
    }

    public boolean byTicket(Ticket ticket, Sessions sessions) {
        boolean result = false;
        if (!sessionsList.contains(sessions.getId())) {
            sessionsList.add(sessions.getId());
            ticket.setId(ID.incrementAndGet());
            purchasedTickets.put(ticket.getId(), ticket);
            result = true;
        }
        return result;
    }

    public List<Integer> rowsList() {
        List<Integer> lists = new ArrayList<>();
        for (int index = 1; index <= amountRows; index++) {
            lists.add(index);
        }
        return lists;
    }

    public List<Integer> cellsList() {
        List<Integer> lists = new ArrayList<>();
        for (int index = 1; index <= amountCells; index++) {
            lists.add(index);
        }
        return lists;
    }


    public Sessions add(Sessions sessions) {
        return store.add(sessions);
    }

    public void update(Sessions sessions) {
        store.update(sessions);
    }

    public Sessions findById(int id) {
        return store.findById(id);
    }

    public List<Sessions> findAll() {
        return store.findAll();
    }
}