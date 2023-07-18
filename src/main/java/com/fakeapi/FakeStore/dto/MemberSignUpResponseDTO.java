package com.fakeapi.FakeStore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignUpResponseDTO {
    private Long memberId;
    private String email;
    private String name;
    private LocalDateTime regdate;
}
