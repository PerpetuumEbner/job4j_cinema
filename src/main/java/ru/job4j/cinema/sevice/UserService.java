package ru.job4j.cinema.sevice;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.persistence.Sql2oUserDBStore;

import java.util.List;
import java.util.Optional;

/**
 * Верхний слой хранилища UserDBStore в котором находятся пользователи.
 *
 * @author yustas
 * @version 1.0
 */
@ThreadSafe
@Service
public class UserService {
    private final Sql2oUserDBStore store;

    @Autowired
    public UserService(Sql2oUserDBStore store) {
        this.store = store;
    }

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public Optional<User> findById(int id) {
        return store.findById(id);
    }

    public List<User> findAll() {
        return store.findAll();
    }

    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        return store.findUserByEmailAndPwd(email, password);
    }
}