package ru.job4j.cinema.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * В классе происходит проверка, чтобы только авторизованный пользователь мог покупать билеты.
 */
@Component
public class AuthFilter implements Filter {

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
        if (uri.endsWith("loginPage")
                || uri.endsWith("login")
                || uri.endsWith("registration")
                || uri.endsWith("Fail")
                || uri.endsWith("formAddUser")
                || uri.endsWith("films")
                || uri.contains("posterFilm")) {
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
