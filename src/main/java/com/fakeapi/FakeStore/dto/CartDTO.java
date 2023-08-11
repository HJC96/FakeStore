package com.fakeapi.FakeStore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class CartDTO {

    private Long id; // null을 처리하기 위해
    private Long userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime date;
    private List<CartItemDTO> products = new ArrayList<>();

//    public void addProduct(CartItemDTO cartItemDTO){
//        products.add(cartItemDTO);
//    }
}
