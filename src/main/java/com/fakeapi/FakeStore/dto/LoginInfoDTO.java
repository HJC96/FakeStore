package com.fakeapi.FakeStore.dto;

import lombok.Data;

@Data
public class LoginInfoDTO {
    private Long memberId;
    private String email;
    private String name;
}
