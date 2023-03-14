package ru.job4j.cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.CinemaHall;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CinemaHallDBStore {
    private static final Logger LOG = LogManager.getLogger(UserDBStore.class);

    private final BasicDataSource pool;

    public CinemaHallDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public CinemaHall findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM seats WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new CinemaHall(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getInt("row"),
                            it.getInt("cell"));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    public List<CinemaHall> findAll() {
        List<CinemaHall> halls = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM seats")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    halls.add(new CinemaHall(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getInt("row"),
                            it.getInt("cell")));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return halls;
    }

    public List<Integer> findAllRows(int id) {
        List<Integer> rows = new ArrayList<>();
        int count = findById(id).getRow();
        for (int index = 1; index <= count; index++) {
            rows.add(index);
        }
        return rows;
    }

    public List<Integer> findAllCell(int id) {
        List<Integer> cells = new ArrayList<>();
        int count = findById(id).getCell();
        for (int index = 1; index <= count; index++) {
            cells.add(index);
        }
        return cells;
    }
}