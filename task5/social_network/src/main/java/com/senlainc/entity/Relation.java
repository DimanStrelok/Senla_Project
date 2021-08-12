package com.senlainc.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Relation {
    private Long id;
    private Long fromAccountId;
    private Long toAccountId;
}
