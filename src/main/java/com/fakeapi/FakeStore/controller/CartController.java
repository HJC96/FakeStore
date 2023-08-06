package com.fakeapi.FakeStore.controller;

import com.fakeapi.FakeStore.dto.CartDTO;
import com.fakeapi.FakeStore.dto.ProductDTO;
import com.fakeapi.FakeStore.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private final CartService cartService;

    @GetMapping
    public List<CartDTO> cart_list(){
        return cartService.list();
    }

    @GetMapping("/{id}")
    public CartDTO read(@PathVariable("id") Long id){
        log.info("read id: "+ id);
        return cartService.read(id);
    }
}
