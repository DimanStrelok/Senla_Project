package com.senlainc.repository;

import com.senlainc.entity.FriendInvite;
import com.senlainc.entity.FriendInviteStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class FriendInviteRepository extends AbstractRepository<FriendInvite> {
    @Override
    protected String getTableName() {
        return "friend_invite";
    }

    @Override
    protected List<String> getTableAttributes() {
        List<String> attributes = new ArrayList<>();
        attributes.add("from_account_id");
        attributes.add("to_account_id");
        attributes.add("created_at");
        attributes.add("status");
        return attributes;
    }

    @Override
    protected FriendInvite parseEntity(ResultSet resultSet) {
        FriendInvite friendInvite = new FriendInvite();
        try {
            friendInvite.setId(resultSet.getLong("id"));
            friendInvite.setFromAccountId(resultSet.getLong("from_account_id"));
            friendInvite.setToAccountId(resultSet.getLong("to_account_id"));
            friendInvite.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            friendInvite.setStatus(FriendInviteStatus.valueOf(resultSet.getString("status")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return friendInvite;
    }

    @Override
    protected void setCreateQueryParams(FriendInvite entity, PreparedStatement ps) {
        try {
            ps.setLong(1, entity.getFromAccountId());
            ps.setLong(2, entity.getToAccountId());
            ps.setTimestamp(3, Timestamp.valueOf(entity.getCreatedAt()));
            ps.setString(4, entity.getStatus().name());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void setUpdateQueryParams(FriendInvite entity, PreparedStatement ps) {
        setCreateQueryParams(entity, ps);
        try {
            ps.setLong(5, entity.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void setEntityId(FriendInvite entity, long id) {
        entity.setId(id);
    }
}
