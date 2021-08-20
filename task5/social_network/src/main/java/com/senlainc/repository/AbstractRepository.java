package com.senlainc.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractRepository<T> implements Repository<T> {
    @Override
    public T create(T entity) {
        String sql = "insert into " + getTableName() + "(" + combineTableAttributes(", ") +
                ") values (" + combineTableAttributes(", ", s -> "?") + ") returning id;";
        return executeQueryWithParams(sql, ps -> setCreateQueryParams(entity, ps), rs -> {
            try {
                rs.next();
                long id = rs.getLong(1);
                setEntityId(entity, id);
                return entity;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public Optional<T> findById(long id) {
        String sql = "select * from " + getTableName() + " where id = ?;";
        return executeQueryWithParams(sql, ps -> {
            try {
                ps.setLong(1, id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, rs -> {
            try {
                if (!rs.next()) {
                    return Optional.empty();
                }
                return Optional.of(parseEntity(rs));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<T> findAll() {
        String sql = "select * from " + getTableName() + ";";
        return executeQuery(sql, rs -> {
            try {
                List<T> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(parseEntity(rs));
                }
                return list;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void update(T entity) {
        String sql = "update " + getTableName() +
                " set " + combineTableAttributes(", ", s -> s + " = ?") +
                " where id = ?;";
        execute(sql, ps -> setUpdateQueryParams(entity, ps));
    }

    @Override
    public void deleteById(long id) {
        String sql = "delete from " + getTableName() + " where id = ?;";
        execute(sql, ps -> {
            try {
                ps.setLong(1, id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    protected abstract String getTableName();

    protected abstract List<String> getTableAttributes();

    protected abstract T parseEntity(ResultSet resultSet);

    protected abstract void setCreateQueryParams(T entity, PreparedStatement ps);

    protected abstract void setUpdateQueryParams(T entity, PreparedStatement ps);

    protected abstract void setEntityId(T entity, long id);

    protected Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void execute(String sql, Consumer<PreparedStatement> setup) {
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            setup.accept(ps);
            ps.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected <R> R executeQuery(String sql, Function<ResultSet, R> acquireResult) {
        return executeQueryWithParams(sql, ps -> {}, acquireResult);
    }

    protected <R> R executeQueryWithParams(String sql, Consumer<PreparedStatement> setup, Function<ResultSet, R> acquireResult) {
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            setup.accept(ps);
            try (ResultSet resultSet = ps.executeQuery()) {
                return acquireResult.apply(resultSet);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected String combineTableAttributes(String delimiter) {
        return combineTableAttributes(delimiter, s -> s);
    }

    protected String combineTableAttributes(String delimiter, Function<String, String> f) {
        StringJoiner joiner = new StringJoiner(delimiter);
        getTableAttributes().stream().map(f).forEach(joiner::add);
        return joiner.toString();
    }
}
