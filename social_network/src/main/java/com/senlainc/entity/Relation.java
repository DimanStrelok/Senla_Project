package com.senlainc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "relation")
public class Relation {
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
}
