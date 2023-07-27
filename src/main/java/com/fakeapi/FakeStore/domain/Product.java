package com.fakeapi.FakeStore.domain;


import lombok.*;

import javax.persistence.*;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CATEGORY_ID", nullable=false)
    private Category category;
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "PRODUCT_CATEGORY",
//            joinColumns = @JoinColumn(name = "PRODUCT_ID"),
//            inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID"))
//    private List<Category> categories = new ArrayList<>();

    @Column(name = "IMAGE")
    private String imageurl;

    @Embedded
    @Column(name = "RATING")
    private Rating rating;

}
