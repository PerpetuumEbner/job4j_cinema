package ru.job4j.cinema.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.User;

import java.util.List;
import java.util.Optional;

/**
 * В классе происходит обработка пользователей в базе данных.
 *
 * @author yustas
 * @version 1.0
 */
@Repository
public class Sql2oUserDBStore {
    private static final Logger LOG = LogManager.getLogger(Sql2oUserDBStore.class);

    private final Sql2o sql2o;

    public Sql2oUserDBStore(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    /**
     * Добавление пользователя.
     *
     * @param user Пользователь, которого необходимо добавить
     * @return Добавленный пользователь.
     */
    public Optional<User> add(User user) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("INSERT INTO users (name, password, email, phone) "
                            + "VALUES (:name, :password, :email, :phone)", true)
                    .addParameter("name", user.getName())
                    .addParameter("password", user.getPassword())
                    .addParameter("email", user.getEmail())
                    .addParameter("phone", user.getPhone());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            user.setId(generatedId);
            return Optional.of(user);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    /**
     * Поиск пользователя по id.
     *
     * @param id Id пользователя, которого необходимо найти.
     * @return Объект пользователя если найден по id, иначе Optional.
     */
    public Optional<User> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM users WHERE id = :id");
            var user = query.addParameter("id", id).executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
        }
    }

    /**
     * Поиск всех пользователей.
     *
     * @return Список найденных пользователей.
     */
    public List<User> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM users");
            return query.executeAndFetch(User.class);
        }
    }

    /**
     * Поиск пользователя по почте и паролю.
     *
     * @param email    Почта пользователя
     * @param password Пароль пользователя.
     * @return Пользователь, найденный по почте и паролю.
     */
    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM users WHERE email = :email AND password = :password");
            var user = query
                    .addParameter("email", email)
                    .addParameter("password", password)
                    .executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
        }
    }
}