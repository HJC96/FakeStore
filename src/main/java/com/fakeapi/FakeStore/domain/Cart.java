package com.fakeapi.FakeStore.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CART")
@Setter
@Getter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CART_ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "CART_DATE")
    private LocalDateTime date;

    @Column(name = "CART_PRODUCT")
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> products = new ArrayList<>();

}
