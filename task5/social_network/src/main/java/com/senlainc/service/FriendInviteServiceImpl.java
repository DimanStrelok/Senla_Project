package com.senlainc.service;

import com.senlainc.di.Component;
import com.senlainc.di.InjectConstructor;
import com.senlainc.dto.CreateFriendInviteDto;
import com.senlainc.dto.FriendInviteDto;
import com.senlainc.entity.FriendInvite;
import com.senlainc.entity.FriendInviteStatus;
import com.senlainc.mapper.FriendInviteMapper;
import com.senlainc.repository.FriendInviteRepository;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Optional;

@Component(interfaceClass = FriendInviteService.class)
public class FriendInviteServiceImpl implements FriendInviteService {
    private final FriendInviteRepository friendInviteRepository;
    private final RelationService friendRelationService;
    private final FriendInviteMapper friendInviteMapper;

    @InjectConstructor
    public FriendInviteServiceImpl(FriendInviteRepository friendInviteRepository, RelationService relationService) {
        this.friendInviteRepository = friendInviteRepository;
        this.friendRelationService = relationService;
        this.friendInviteMapper = Mappers.getMapper(FriendInviteMapper.class);
    }

    @Override
    public FriendInviteDto create(CreateFriendInviteDto createFriendInviteDto) {
        FriendInvite friendInvite = new FriendInvite();
        friendInvite.setFromAccountId(createFriendInviteDto.getFromAccountId());
        friendInvite.setToAccountId(createFriendInviteDto.getToAccountId());
        friendInvite.setCreatedAt(LocalDateTime.now());
        friendInvite.setStatus(FriendInviteStatus.Created);
        return friendInviteMapper.friendInviteToDto(friendInviteRepository.create(friendInvite));
    }

    @Override
    public void acceptFriendInvite(long id) {
        Optional<FriendInvite> friendInvite = friendInviteRepository.findById(id);
        if (friendInvite.isPresent()) {
            long fromAccountId = friendInvite.get().getFromAccountId();
            long toAccountId = friendInvite.get().getToAccountId();
            friendRelationService.createRelation(fromAccountId, toAccountId);
            FriendInvite updatedFriendInvite = friendInvite.get();
            updatedFriendInvite.setStatus(FriendInviteStatus.Accepted);
            friendInviteRepository.update(updatedFriendInvite);
        }
    }
}
