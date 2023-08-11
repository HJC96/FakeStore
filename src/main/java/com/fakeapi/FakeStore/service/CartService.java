package com.fakeapi.FakeStore.service;

import com.fakeapi.FakeStore.domain.Cart;
import com.fakeapi.FakeStore.dto.CartDTO;
import com.fakeapi.FakeStore.dto.PageRequestDTO;
import com.fakeapi.FakeStore.dto.PageResponseDTO;
import com.fakeapi.FakeStore.dto.ProductDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface CartService {
//    List<CartDTO> list();
    PageResponseDTO<CartDTO> list(PageRequestDTO pageRequestDTO);

    PageResponseDTO<CartDTO> listWithLimitCart(PageRequestDTO pageRequestDTO, int limit);
    CartDTO read(Long id) ;

    Cart register(CartDTO cartDTO);

    CartDTO update(Long id, CartDTO cartDTO) ;

    public void delete(Long id);

}


