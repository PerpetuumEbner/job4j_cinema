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
@AllArgsConstructor
@Getter
@Setter
public class Ticket {
    private int id;

    private int filmId;

    private int row;

    private int cell;

    private int user_id;

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