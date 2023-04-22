package ru.job4j.cinema.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.Objects;

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
    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "film_id", "filmId",
            "row", "row",
            "cell", "cell",
            "user_id", "userId"
    );

    private int id;

    private int filmId;

    private int row;

    private int cell;

    private int userId;

    public Ticket(int id, int filmId, int row, int cell, int userId) {
        this.id = id;
        this.filmId = filmId;
        this.row = row;
        this.cell = cell;
        this.userId = userId;
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