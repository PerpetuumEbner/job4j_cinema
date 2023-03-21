package ru.job4j.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Модель описывающая зал с местами.
 *
 * @author yustas
 * @version 1.0
 */
@AllArgsConstructor
@Getter
@Setter
public class CinemaHall {
    private int id;

    private String name;

    private int row;

    private int cell;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CinemaHall that = (CinemaHall) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}