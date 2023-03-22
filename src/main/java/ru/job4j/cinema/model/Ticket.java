package ru.job4j.cinema.model;

import lombok.*;

import java.util.Objects;
import java.util.PrimitiveIterator;

/**
 * Модель описывающая билет.
 *
 * @author yustas
 * @version 1.0
 */
@NoArgsConstructor
@Getter
@Setter
public class Ticket {
    private int id;

    private int filmId;

    private int row;

    private int cell;

    private int user_id;

    private Film film;

    public Ticket(int id, int filmId, int row, int cell, int user_id) {
        this.id = id;
        this.filmId = filmId;
        this.row = row;
        this.cell = cell;
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return id == ticket.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}