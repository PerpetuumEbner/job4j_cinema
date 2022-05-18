package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Модель описывающая фильм.
 *
 * @author yustas
 * @version 1.0
 */
public class Films {
    private int id;

    private String name;

    private String productionYear;

    private byte[] poster;

    public Films(int id, String name, String productionYear, byte[] poster) {
        this.id = id;
        this.name = name;
        this.productionYear = productionYear;
        this.poster = poster;
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

    public String getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(String productionYear) {
        this.productionYear = productionYear;
    }

    public byte[] getPoster() {
        return poster;
    }

    public void setPoster(byte[] poster) {
        this.poster = poster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Films films = (Films) o;
        return id == films.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}