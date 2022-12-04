package ru.job4j.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Модель описывающая фильм.
 *
 * @author yustas
 * @version 1.0
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Film {
    private int id;

    private String name;

    private String productionYear;

    private byte[] photo;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Film films = (Film) o;
        return id == films.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}