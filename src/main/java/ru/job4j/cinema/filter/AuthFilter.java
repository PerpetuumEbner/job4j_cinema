package ru.job4j.cinema.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * В классе происходит проверка, чтобы только авторизованный пользователь мог покупать билеты.
 */
@Component
public class AuthFilter implements Filter {
    private final List<String> ACCESS_LIST = Arrays.asList("loginPage", "login", "registration", "Fail", "films", "posterFilm");

    /**
     * Метод проверки доступа.
     *
     * @param request  Определяет объект для предоставления сервлету информации о клиентском запросе.
     * @param response Определяет объект, помогающий сервлету отправить ответ клиенту.
     * @param chain    Объект, предоставляемый контейнером сервлета разработчику,
     *                 дающий представление о цепочке вызовов отфильтрованного запроса на ресурс.
     */
    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        boolean access = ACCESS_LIST
                .stream()
                .anyMatch(uri::contains);
        if (access) {
            chain.doFilter(req, res);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/loginPage");
            return;
        }
        chain.doFilter(req, res);
    }
}
