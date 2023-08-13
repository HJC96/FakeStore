package com.fakeapi.FakeStore.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name="REFRESHTOKEN")
@NoArgsConstructor
@Setter @Getter
public class RefreshToken {
    @Id @Column(name="REFRESHTOKEN_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="REFRESHTOKEN_MEMBER_ID")
    private Long memberId;
    @Column(name="REFRESHTOKEN_VALUE")
    private String value;
}
