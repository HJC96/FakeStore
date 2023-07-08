package com.fakeapi.FakeStore.domain;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter @Setter
@Builder
@NoArgsConstructor
@ToString
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToMany
    @JoinColumn(name = "PRODUCT_CATEGORY")
    private List<Category> categories = new ArrayList<>();

    @Column(name = "IMAGE")
    private String imageurl;

    @Embedded
    @Column(name = "RATING")
    private Rating rating;
}
