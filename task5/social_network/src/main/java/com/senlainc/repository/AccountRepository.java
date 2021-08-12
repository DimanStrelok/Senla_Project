package com.senlainc.repository;

import com.senlainc.entity.Account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository extends AbstractRepository<Account> {
    @Override
    protected String getTableName() {
        return "account";
    }

    @Override
    protected List<String> getTableAttributes() {
        List<String> attributes = new ArrayList<>();
        attributes.add("first_name");
        attributes.add("last_name");
        attributes.add("middle_name");
        attributes.add("email");
        attributes.add("email_confirmed");
        attributes.add("password");
        attributes.add("city");
        attributes.add("phone_number");
        attributes.add("text_status");
        return attributes;
    }

    @Override
    protected Account parseEntity(ResultSet resultSet) {
        Account account = new Account();
        try {
            account.setId(resultSet.getLong("id"));
            account.setFirstName(resultSet.getString("first_name"));
            account.setLastName(resultSet.getString("last_name"));
            account.setLastName(resultSet.getString("middle_name"));
            account.setEmail(resultSet.getString("email"));
            account.setEmailConfirmed(resultSet.getBoolean("email_confirmed"));
            account.setPassword(resultSet.getString("password"));
            account.setCity(resultSet.getString("city"));
            account.setPhoneNumber(resultSet.getString("phone_number"));
            account.setTextStatus(resultSet.getString("text_status"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return account;
    }

    @Override
    protected void setCreateQueryParams(Account entity, PreparedStatement ps) {
        try {
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getMiddleName());
            ps.setString(4, entity.getEmail());
            ps.setBoolean(5, entity.isEmailConfirmed());
            ps.setString(6, entity.getPassword());
            ps.setString(7, entity.getCity());
            ps.setString(8, entity.getPhoneNumber());
            ps.setString(9, entity.getTextStatus());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void setUpdateQueryParams(Account entity, PreparedStatement ps) {
        setCreateQueryParams(entity, ps);
        try {
            ps.setLong(10, entity.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void setEntityId(Account entity, long id) {
        entity.setId(id);
    }
}
