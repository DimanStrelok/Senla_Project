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
@Table(name = "group_invite")
public class GroupInvite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id")
    private Group group;
    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id")
    private Account account;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private InviteStatus status;
}
