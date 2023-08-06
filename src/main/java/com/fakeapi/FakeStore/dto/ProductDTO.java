package com.fakeapi.FakeStore.dto;


import com.fakeapi.FakeStore.domain.Rating;
import lombok.*;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@AllArgsConstructor
@NoArgsConstructor

//@Data
//@Getter
@Setter
@ToString
public class ProductDTO {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String imageurl;
    private Rating rating;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

//     double데이터 소수점이 0인경우 정수로 출력을 위해
//    public String getPriceAsString() {
//        if (price % 1 == 0) {
//            return String.format("%.0f", price);
//        } else {
//            return price.toString();
//        }
//    }

    public Double getPrice() {
        return price;
    }

//    public String getPrice() {
//        return price;
//    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getImageurl() {
        return imageurl;
    }

    public Rating getRating() {
        return rating;
    }
}
