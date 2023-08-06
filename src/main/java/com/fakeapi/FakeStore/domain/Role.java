package com.fakeapi.FakeStore.domain;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter @Setter
//@Table(name = "ROLE")
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    private Long id;

    @Column(name = "ROLENAME")
    private String name;
}
