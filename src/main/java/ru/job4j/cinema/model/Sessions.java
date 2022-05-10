package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Модель описывающая сеанс.
 *
 * @author yustas
 * @version 1.0
 */
public class Sessions {
    private int id;

    private String name;

    public Sessions(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sessions sessions = (Sessions) o;
        return id == sessions.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}