package com.fakeapi.FakeStore.dto;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;

@Data
public class RefreshTokenDTO {
    @NotEmpty
    String refreshToken;

}
