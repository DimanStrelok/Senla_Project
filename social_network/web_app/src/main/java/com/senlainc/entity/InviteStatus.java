package com.senlainc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.NUMBER)
public enum InviteStatus {
    Created,
    Accepted,
    Rejected
}
