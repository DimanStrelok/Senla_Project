package com.senlainc.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepository<T> implements Repository<T> {
    @Override
    public T create(T entity) {
        Connection connection = createConnection();
        ResultSet resultSet = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into ");
            sb.append(getTableName());
            sb.append("(");
            getTableAttributes().forEach(s -> sb.append(s).append(", "));
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
            sb.append(") ");
            sb.append(" values (");
            getTableAttributes().forEach(s -> sb.append("?").append(", "));
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
            sb.append(") returning id;");
            PreparedStatement ps = connection.prepareStatement(sb.toString());
            setCreateQueryParams(entity, ps);
            resultSet = ps.executeQuery();
            resultSet.next();
            long id = resultSet.getLong(1);
            setEntityId(entity, id);
            return entity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                connection.close();
            } catch (Exception e) {
                //
            }
        }
    }

    @Override
    public Optional<T> findById(long id) {
        Connection connection = createConnection();
        ResultSet resultSet = null;
        try {
            PreparedStatement ps = connection.prepareStatement("select * from " + getTableName() + " where id = ?;");
            ps.setLong(1, id);
            resultSet = ps.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(parseEntity(resultSet));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                connection.close();
            } catch (Exception e) {
                //
            }
        }
    }

    public List<T> findAll() {
        Connection connection = createConnection();
        ResultSet resultSet = null;
        try {
            PreparedStatement ps = connection.prepareStatement("select * from " + getTableName() + ";");
            resultSet = ps.executeQuery();
            List<T> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(parseEntity(resultSet));
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                connection.close();
            } catch (Exception e) {
                //
            }
        }
    }

    @Override
    public void update(T entity) {
        try (Connection connection = createConnection()) {
            StringBuilder sb = new StringBuilder();
            sb.append("update ");
            sb.append(getTableName());
            sb.append(" set ");
            getTableAttributes().forEach(s -> sb.append(s).append(" = ?, "));
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
            sb.append(" where id = ?;");
            PreparedStatement ps = connection.prepareStatement(sb.toString());
            setUpdateQueryParams(entity, ps);
            ps.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(long id) {
        try (Connection connection = createConnection()) {
            PreparedStatement ps = connection.prepareStatement("delete from " + getTableName() + " where id = ?;");
            ps.setLong(1, id);
            ps.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract String getTableName();

    protected abstract List<String> getTableAttributes();

    protected abstract T parseEntity(ResultSet resultSet);

    protected abstract void setCreateQueryParams(T entity, PreparedStatement ps);

    protected abstract void setUpdateQueryParams(T entity, PreparedStatement ps);

    protected abstract void setEntityId(T entity, long id);

    protected Connection createConnection() {
        Connection connection;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
