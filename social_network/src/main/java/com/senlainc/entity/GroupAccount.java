package com.senlainc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "group_account")
public class GroupAccount {
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
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role")
    private GroupRole role;
}
