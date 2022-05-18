//package ru.job4j.cinema.store;
//
//import net.jcip.annotations.ThreadSafe;
//import org.springframework.stereotype.Repository;
//import ru.job4j.cinema.model.Films;
//
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * Хранилище в котором находятся фильмы.
// *
// * @author yustas
// * @version 1.0
// */
//
//@ThreadSafe
//@Repository
//public class FilmsStore {
//    private final Map<Integer, Films> films = new HashMap<>();
//
//    private final AtomicInteger ID = new AtomicInteger(4);
//
//    public FilmsStore() {
//        films.put(1, new Films(1, "Зеленая миля", "1999", new byte[0]));
//        films.put(2, new Films(2, "Список Шиндлера", "1999", new byte[0]));
//        films.put(3, new Films(3, "Побег из Шоушенка", "1999", new byte[0]));
//        films.put(4, new Films(4, "Властелин колец: Возвращение короля", "1999", new byte[0]));
//    }
//
//    public Collection<Films> findAll() {
//        return films.values();
//    }
//
//    public void add(Films film) {
//        film.setId(ID.incrementAndGet());
//        films.put(film.getId(), film);
//    }
//
//    public void update(Films film) {
//        films.replace(film.getId(), film);
//    }
//
//    public Films findById(int id) {
//        return films.get(id);
//    }
//}