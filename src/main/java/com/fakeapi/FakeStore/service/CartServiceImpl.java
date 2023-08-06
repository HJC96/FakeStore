package com.fakeapi.FakeStore.service;

import com.fakeapi.FakeStore.domain.Cart;
import com.fakeapi.FakeStore.dto.CartDTO;
import com.fakeapi.FakeStore.dto.ProductDTO;
import com.fakeapi.FakeStore.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service @Log4j2
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CartDTO> list() {
        List<Cart> carts = cartRepository.findAll();
        List<CartDTO> cartDTOS = new ArrayList<>();
        for(Cart cart:carts){
            cartDTOS.add(modelMapper.map(cart, CartDTO.class));
        }
        return cartDTOS;
    }

    @Override
    public CartDTO read(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow();
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        return cartDTO;
    }
}
