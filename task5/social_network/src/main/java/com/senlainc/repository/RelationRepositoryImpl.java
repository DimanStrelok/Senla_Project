package com.senlainc.repository;

import com.senlainc.di.Component;
import com.senlainc.entity.Relation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component(interfaceClass = RelationRepository.class)
public class RelationRepositoryImpl extends AbstractRepository<Relation> implements RelationRepository {
    @Override
    protected String getTableName() {
        return "relation";
    }

    @Override
    protected List<String> getTableAttributes() {
        List<String> attributes = new ArrayList<>();
        attributes.add("from_account_id");
        attributes.add("to_account_id");
        return attributes;
    }

    @Override
    protected Relation parseEntity(ResultSet resultSet) {
        Relation relation = new Relation();
        try {
            relation.setId(resultSet.getLong("id"));
            relation.setFromAccountId(resultSet.getLong("from_account_id"));
            relation.setToAccountId(resultSet.getLong("to_account_id"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return relation;
    }

    @Override
    protected void setCreateQueryParams(Relation entity, PreparedStatement ps) {
        try {
            ps.setLong(1, entity.getFromAccountId());
            ps.setLong(2, entity.getToAccountId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void setUpdateQueryParams(Relation entity, PreparedStatement ps) {
        setCreateQueryParams(entity, ps);
        try {
            ps.setLong(3, entity.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void setEntityId(Relation entity, long id) {
        entity.setId(id);
    }
}
