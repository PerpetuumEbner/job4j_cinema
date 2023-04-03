package ru.job4j.cinema.filter;

import ru.job4j.cinema.model.User;

import javax.servlet.http.HttpSession;

/**
 * В классе проходит проверка авторизованного пользователя.
 */
public class CheckHttpSession {
    /**
     * Проверка авторизованного пользователя, если не авторизован то как гость.
     *
     * @param session Http сессия, куда сохраняется текущий пользователь
     * @return Возвращается авторизированный пользователь иначе гость.
     */
    public static User userHttpSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        return user;
    }
}