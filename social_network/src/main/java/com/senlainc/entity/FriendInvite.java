package com.senlainc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "friend_invite")
public class FriendInvite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;
    @ManyToOne(optional = false)
    @JoinColumn(name = "to_account_id")
    private Account toAccount;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private InviteStatus status;
}
