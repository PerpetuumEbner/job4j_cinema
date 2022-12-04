package ru.job4j.cinema.sevice;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Sessions;
import ru.job4j.cinema.persistence.SessionsDBStore;

import java.util.List;

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