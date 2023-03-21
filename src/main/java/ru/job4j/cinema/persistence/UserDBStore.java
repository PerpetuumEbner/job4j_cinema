package ru.job4j.cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * В классе происходит обработка пользователей в базе данных.
 *
 * @author yustas
 * @version 1.0
 */
@Repository
public class UserDBStore {
    private static final Logger LOG = LogManager.getLogger(UserDBStore.class);

    private final BasicDataSource pool;

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * Добавление пользователя.
     *
     * @param user Пользователь, которого необходимо добавить
     * @return Добавленный пользователь иначе Optional.
     */
    public Optional<User> add(User user) {
        Optional<User> optionalUser = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO users(name, password, email, phone) VALUES (?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                while (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
            optionalUser = Optional.of(user);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return optionalUser;
    }

    /**
     * Поиск пользователя по id.
     *
     * @param id Id пользователя, которого необходимо найти.
     * @return Объект пользователя если найден по id.
     */
    public Optional<User> findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM users WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return Optional.of(new User(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("password"),
                            it.getString("email"),
                            it.getString("phone")
                    ));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    /**
     * Поиск всех пользователей.
     *
     * @return Список найденных пользователей.
     */
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM users")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    users.add(new User(
                                    it.getInt("id"),
                                    it.getString("name"),
                                    it.getString("password"),
                                    it.getString("email"),
                                    it.getString("phone")
                            )
                    );
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return users;
    }

    /**
     * Поиск пользователя по почте и паролю.
     *
     * @param email    Почта пользователя
     * @param password Пароль пользователя.
     * @return Пользователь, найденный по почте и паролю.
     */
    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM users WHERE email = ? and password = ?")
        ) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return Optional.of(new User(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("password"),
                            it.getString("email"),
                            it.getString("phone")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}