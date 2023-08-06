package com.fakeapi.FakeStore.domain;

import lombok.*;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long id;

    @Column(name = "CATEGORYNAME")
    private String name;

//    @ManyToMany(mappedBy = "categories", cascade = CascadeType.ALL)
//    List<Product> products = new ArrayList<Product>();
}
