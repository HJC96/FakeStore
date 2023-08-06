package com.fakeapi.FakeStore.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;

@Embeddable
@Getter @Setter
public class Rating {
//    private double rate;
//    private int count;
    private Double rate; // 객체로 바꾸면 null을 쓸수 있게 된다. 기본형 타입일때는 null 안됨.
    private Integer count;
}
