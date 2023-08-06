package com.fakeapi.FakeStore.dto;

import lombok.Data;

@Data
public class CartItemDTO {

    private Long productId;
    private int quantity;

}