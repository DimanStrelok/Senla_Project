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
@Table(name = "group_chat_message")
public class GroupChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "chat_id")
    private GroupChat chat;
    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id")
    private Account account;
    @Column(name = "text")
    private String text;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
