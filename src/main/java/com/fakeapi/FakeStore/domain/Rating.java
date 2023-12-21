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
    private Double rate;
    private Integer count;
}
