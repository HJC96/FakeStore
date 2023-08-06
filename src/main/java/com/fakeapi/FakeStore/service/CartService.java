package com.fakeapi.FakeStore.service;

import com.fakeapi.FakeStore.dto.CartDTO;
import com.fakeapi.FakeStore.dto.ProductDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface CartService {
    List<CartDTO> list();
    CartDTO read(Long id) ;
}
